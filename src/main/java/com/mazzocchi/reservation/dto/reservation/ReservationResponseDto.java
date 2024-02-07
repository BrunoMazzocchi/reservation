package com.mazzocchi.reservation.dto.reservation;

import com.fasterxml.jackson.annotation.*;
import com.mazzocchi.reservation.dto.menu.*;
import lombok.*;

import java.sql.*;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponseDto {

    private Long id;

    private String customerName;

    private String customerNumber;

    private Date dateReserve;

    private String state;

    private MenuDto menu;
}