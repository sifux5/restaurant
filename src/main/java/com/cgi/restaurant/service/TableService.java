package com.cgi.restaurant.service;

import com.cgi.restaurant.model.Table;
import com.cgi.restaurant.repository.TableRepository;
import com.cgi.restaurant.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public List<Table> getAvailableTables(LocalDateTime startTime, LocalDateTime endTime, int partySize, String zone) {
        List<Table> allTables = tableRepository.findAll();

        return allTables.stream()
                .filter(table -> table.getCapacity() >= partySize)
                .filter(table -> zone == null || table.getZone().equalsIgnoreCase(zone))
                .filter(table -> isTableAvailable(table.getId(), startTime, endTime))
                .toList();
    }

    public boolean isTableAvailable(Long tableId, LocalDateTime startTime, LocalDateTime endTime) {
        List<?> conflicts = reservationRepository
                .findByTableIdAndEndTimeAfterAndStartTimeBefore(tableId, startTime, endTime);
        return conflicts.isEmpty();
    }
}
