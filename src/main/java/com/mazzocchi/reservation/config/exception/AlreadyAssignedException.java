package com.mazzocchi.reservation.config.exception;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(String message) {
        super(message);
    }
}