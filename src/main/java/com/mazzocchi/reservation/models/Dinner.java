package com.mazzocchi.reservation.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "dinner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dinner")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "menu_dinner",
            joinColumns = @JoinColumn(name = "id_dinner"),
            inverseJoinColumns = @JoinColumn(name = "id_menu")
    )
    private List<Menu> menus;
}
