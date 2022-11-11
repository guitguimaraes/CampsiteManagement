package com.campsitemanagement.exception;

/**
 * Booking not Available Exception.
 */
public class BookingNotAvailableException extends RuntimeException {
    public BookingNotAvailableException(String message) {
        super(message);
    }
}
