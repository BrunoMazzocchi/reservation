package com.mazzocchi.reservation.dto;

import java.util.*;

import lombok.*;

@Getter
@Setter
public class DinnerDto {
    private Long id;
    private String name;
    private String description;
    private double price;
}
