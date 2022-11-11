package com.campsitemanagement.service;

import com.campsitemanagement.entity.Booking;

import java.time.LocalDate;
import java.util.List;

/**
 * Booking Service Interface.
 */
public interface BookingService {

    /**
     * method responsible to get all available dates to book.
     * @param startDate - start date of the search
     * @param endDate - end date of the search
     * @return List LocalDate
     */
    List<LocalDate> getAvailableBooks(String startDate, String endDate);

    /**
     * method responsible to add a book.
     * @param booking booking information to be added
     * @return Booking id added
     */
    Booking addBooking(Booking booking);

    /**
     * method responsible to update a book.
     * @param bookingId - booking id that will be updated
     * @param booking - booking information to be updated
     * @return booking id.
     */
    Booking updateBooking(String bookingId, Booking booking);

    /**
     * method responsible to delete a book.
     * @param bookingId - booking id that will be deleted
     */
    void deleteBooking(String bookingId);
}
