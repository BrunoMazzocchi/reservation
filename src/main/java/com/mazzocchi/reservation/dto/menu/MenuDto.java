package com.mazzocchi.reservation.dto.menu;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotEmpty(message = "Dinners list cannot be empty")
    @Valid // Validates each DinnerDto in the list
    private List<DinnerDto> dinners;
}