package com.mazzocchi.reservation.dto;

import lombok.*;

import java.time.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorHandlerResponse {
    private String message;
    private int status;

    private List<String> errors;

    private LocalDateTime timestamp;



}
