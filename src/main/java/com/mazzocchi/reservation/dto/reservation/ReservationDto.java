package com.mazzocchi.reservation.dto.reservation;

import java.sql.*;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private Long id;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @NotBlank(message = "Customer number cannot be blank")
    @Pattern(regexp = "\\d{12}", message = "Invalid phone number format. Must be 12 digits.")
    private String customerNumber;

    @NotNull(message = "Reservation date cannot be null")
    private Date dateReserve;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @Valid
    @NotNull(message = "Menu cannot be null")
    private Long menuId;
}