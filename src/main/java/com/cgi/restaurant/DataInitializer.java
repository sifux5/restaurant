package com.cgi.restaurant;

import com.cgi.restaurant.model.Reservation;
import com.cgi.restaurant.model.Table;
import com.cgi.restaurant.repository.ReservationRepository;
import com.cgi.restaurant.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) {
        List<Table> tables = tableRepository.saveAll(List.of(
                createTable("A1", 2, "INDOOR", false, true, false, false, 1, 1),
                createTable("A2", 2, "INDOOR", true, false, false, false, 1, 2),
                createTable("A3", 4, "INDOOR", true, false, true, true, 1, 3),
                createTable("A4", 4, "INDOOR", false, false, false, false, 1, 4),
                createTable("B1", 4, "INDOOR", false, true, true, false, 2, 1),
                createTable("B2", 6, "INDOOR", true, false, false, true, 2, 2),
                createTable("B3", 6, "INDOOR", false, false, false, false, 2, 3),
                createTable("B4", 8, "INDOOR", false, true, true, false, 2, 4),
                createTable("T1", 2, "TERRACE", true, false, false, false, 3, 1),
                createTable("T2", 4, "TERRACE", true, false, true, true, 3, 2),
                createTable("T3", 4, "TERRACE", false, false, false, false, 3, 3),
                createTable("T4", 6, "TERRACE", true, true, false, false, 3, 4),
                createTable("P1", 8, "PRIVATE", false, true, true, false, 4, 1),
                createTable("P2", 10, "PRIVATE", false, true, false, false, 4, 2)
        ));

        Random random = new Random();
        LocalDateTime baseTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        for (Table table : tables) {
            int reservationCount = random.nextInt(3);
            for (int i = 0; i < reservationCount; i++) {
                LocalDateTime startTime = baseTime.plusHours(random.nextInt(12));
                Reservation reservation = new Reservation();
                reservation.setTable(table);
                reservation.setCustomerName("Klient " + random.nextInt(100));
                reservation.setPartySize(random.nextInt(table.getCapacity()) + 1);
                reservation.setStartTime(startTime);
                reservation.setEndTime(startTime.plusHours(2));
                reservationRepository.save(reservation);
            }
        }
    }

    private Table createTable(String name, int capacity, String zone,
                              boolean windowSeat, boolean quietCorner,
                              boolean accessible, boolean nearPlayground,
                              int positionX, int positionY) {
        Table table = new Table();
        table.setName(name);
        table.setCapacity(capacity);
        table.setZone(zone);
        table.setWindowSeat(windowSeat);
        table.setQuietCorner(quietCorner);
        table.setAccessible(accessible);
        table.setNearPlayground(nearPlayground);
        table.setPositionX(positionX);
        table.setPositionY(positionY);
        return table;
    }
}