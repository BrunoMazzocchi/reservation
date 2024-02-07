package com.mazzocchi.reservation.config.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mazzocchi.reservation.dto.ErrorHandlerResponse;
import org.springframework.web.method.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorHandlerResponse> handleNotFoundException(NotFoundException ex) {
        // Create the error response
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage())), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // Log the error details
        System.err.println(ex.getMessage());

        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        // Log the error details
        System.err.println(ex.getMessage());

        return new ResponseEntity<>(buildErrorResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
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
        return new ResponseEntity<>(buildErrorResponse("Validation error", HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorHandlerResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid value '%s' for parameter '%s'. Please provide a valid value.",
                ex.getValue(),
                ex.getName());
        return new ResponseEntity<>(buildErrorResponse(message, HttpStatus.BAD_REQUEST, Collections.singletonList(message)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex) {
        // Create the error response
        return new ResponseEntity<>(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, Collections.singletonList(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorHandlerResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid request body. Please check the syntax and types of your request.";
        return new ResponseEntity<>(buildErrorResponse(message, HttpStatus.BAD_REQUEST, Collections.singletonList(message)), HttpStatus.BAD_REQUEST);
    }

    private ErrorHandlerResponse buildErrorResponse(String message, HttpStatus status, List<String> errors) {
        ErrorHandlerResponse errorResponse = new ErrorHandlerResponse();
        errorResponse.setErrors(errors);
        errorResponse.setMessage(message);
        errorResponse.setStatus(status.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }
}