package com.mazzocchi.reservation.dto;

import java.sql.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String customerName;
    private String customerNumber;
    private Date dateReserve;
    private String state;
}
