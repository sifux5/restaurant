package com.cgi.restaurant.service;

import com.cgi.restaurant.model.Reservation;
import com.cgi.restaurant.repository.ReservationRepository;
import com.cgi.restaurant.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final TableService tableService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Long tableId, String customerName, int partySize, LocalDateTime startTime) {
        LocalDateTime endTime = startTime.plusHours(2);

        if (!tableService.isTableAvailable(tableId, startTime, endTime)) {
            throw new RuntimeException("Laud on sellel ajal juba broneeritud!");
        }

        var table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Lauda ei leitud!"));

        if (table.getCapacity() < partySize) {
            throw new RuntimeException("Seltskond on liiga suur sellele lauale!");
        }

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setCustomerName(customerName);
        reservation.setPartySize(partySize);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}