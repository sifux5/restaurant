package com.cgi.restaurant.service;

import com.cgi.restaurant.model.Table;
import com.cgi.restaurant.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableMergeService {

    private final TableRepository tableRepository;
    private final TableService tableService;

    public List<List<Table>> findMergeableTables(int partySize, LocalDateTime startTime, LocalDateTime endTime) {
        List<Table> allTables = tableRepository.findAll();
        List<List<Table>> result = new ArrayList<>();

        for (int i = 0; i < allTables.size(); i++) {
            for (int j = i + 1; j < allTables.size(); j++) {
                Table t1 = allTables.get(i);
                Table t2 = allTables.get(j);

                if (!t1.getZone().equals(t2.getZone())) continue;
                if (!areAdjacent(t1, t2)) continue;

                if (!tableService.isTableAvailable(t1.getId(), startTime, endTime)) continue;
                if (!tableService.isTableAvailable(t2.getId(), startTime, endTime)) continue;

                if (t1.getCapacity() + t2.getCapacity() < partySize) continue;

                result.add(List.of(t1, t2));
            }
        }

        return result;
    }

    private boolean areAdjacent(Table t1, Table t2) {
        int dx = Math.abs(t1.getPositionX() - t2.getPositionX());
        int dy = Math.abs(t1.getPositionY() - t2.getPositionY());
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
}