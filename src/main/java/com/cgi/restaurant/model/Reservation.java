package com.cgi.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    private String customerName;
    private int partySize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
