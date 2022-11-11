package com.campsiteManagement.service;

import com.campsiteManagement.entity.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getAvailableBooks();

    Booking addBooking(Booking booking);

    Booking updateBooking(String bookingId, Booking booking);

    void deleteBooking(String bookingId);
}
