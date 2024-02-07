package com.mazzocchi.reservation.config.exception;

public class InactiveException extends RuntimeException {
    public InactiveException(String message) {
        super(message);
    }
}