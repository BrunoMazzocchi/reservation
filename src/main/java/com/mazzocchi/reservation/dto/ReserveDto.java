package com.mazzocchi.reservation.dto;

import java.sql.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReserveDto {
    private Long id;
    private String customerName;
    private String customerNumber;
    private Date dateReserve;
    private String state;
}
