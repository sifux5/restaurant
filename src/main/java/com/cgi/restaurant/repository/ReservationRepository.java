package com.cgi.restaurant.repository;

import com.cgi.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTableIdAndEndTimeAfterAndStartTimeBefore(
            Long tableId,
            LocalDateTime start,
            LocalDateTime end
    );
}
