package com.mazzocchi.reservation.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.*;


@Entity
@Table(name = "reserve")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserve")
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_number", nullable = false, length = 12)
    private String customerNumber;

    @Column(name = "date_reserve", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
    private Date dateReserve;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "id_menu")
    private Menu menu;

}
