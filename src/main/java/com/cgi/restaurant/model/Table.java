package com.cgi.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@jakarta.persistence.Table(name = "restaurant_table")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;
    private String zone;
    private boolean windowSeat;
    private boolean quietCorner;
    private boolean accessible;
    private int positionX;
    private int positionY;
    private boolean nearPlayground;
}
