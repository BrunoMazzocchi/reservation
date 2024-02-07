package com.mazzocchi.reservation.dto.menu;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class DinnerDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;
}
