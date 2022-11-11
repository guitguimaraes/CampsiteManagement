package com.campsitemanagement.exception;

/**
 * Exception to handle all not found errors.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
