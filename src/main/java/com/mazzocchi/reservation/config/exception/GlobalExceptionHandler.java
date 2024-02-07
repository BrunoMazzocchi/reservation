package com.mazzocchi.reservation.config.exception;

import com.fasterxml.jackson.core.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // Log the error details
        System.err.println(ex.getMessage());

        // Return a response entity with a generic error message
        return new ResponseEntity<>("A runtime error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        // Log the error details
        System.err.println(ex.getMessage());

        // Return a response entity with a generic error message
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Get all validation errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        // Create the error response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Validation error");
        errorResponse.put("errors", errors);
        errorResponse.put("timestamp", LocalDateTime.now());

        // Return the error response with a 400 Bad Request status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex) {
        // Create the error response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid JSON format");
        errorResponse.put("details", ex.getOriginalMessage());
        errorResponse.put("timestamp", LocalDateTime.now());

        // Return the error response with a 400 Bad Request status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}