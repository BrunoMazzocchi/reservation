package com.mazzocchi.reservation.models;

import jakarta.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
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

    @PrePersist
    @PreUpdate
    // Updates the price of the menu based on the sum of the prices of the dinners
    public void updatePrice() {
        this.price = dinners.stream().mapToDouble(Dinner::getPrice).sum();
    }

}
