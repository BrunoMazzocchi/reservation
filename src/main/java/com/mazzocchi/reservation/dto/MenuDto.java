package com.mazzocchi.reservation.dto;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class MenuDto {
    private Long id;
    private String name;
    private double price;
    private String state;
}
