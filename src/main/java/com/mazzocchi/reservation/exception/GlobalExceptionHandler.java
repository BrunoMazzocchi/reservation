package com.mazzocchi.reservation.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // Log the error details
        System.err.println(ex.getMessage());

        // Return a response entity with a generic error message
        return new ResponseEntity<>("A runtime error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}