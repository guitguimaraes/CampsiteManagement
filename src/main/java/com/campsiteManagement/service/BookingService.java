package com.campsiteManagement.service;

import com.campsiteManagement.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<LocalDate> getAvailableBooks(String startDate, String endDate);

    Booking addBooking(Booking booking);

    Booking updateBooking(String bookingId, Booking booking);

    void deleteBooking(String bookingId);
}
