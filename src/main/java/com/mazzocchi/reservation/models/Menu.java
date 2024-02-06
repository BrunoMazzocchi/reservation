package com.mazzocchi.reservation.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany
    @JoinTable(
            name = "menu_dinner",
            joinColumns = @JoinColumn(name = "id_menu"),
            inverseJoinColumns = @JoinColumn(name = "id_dinner")
    )
    private List<Dinner> dinners;

}
