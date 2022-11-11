package com.campsiteManagement.service;

import com.campsiteManagement.entity.Booking;
import com.campsiteManagement.exception.BookingNotAvailableException;
import com.campsiteManagement.exception.DateException;
import com.campsiteManagement.exception.NotFoundException;
import com.campsiteManagement.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EntityQueryService entityQueryService;

    @Override
    @Transactional
    public List<Booking> getAvailableBooks() {
        return null;
    }

    @Override
    @Transactional
    public Booking addBooking(Booking booking) {
        validateDates(booking);
        if (isFreeBooking(booking.getStartDate(), booking.getEndDate())) {
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotAvailableException("Booking Not available to this date.");
        }
    }


    @Override
    @Transactional
    public Booking updateBooking(String bookingId, Booking booking) {
        Optional<Booking> bookingRep = bookingRepository.findById(bookingId);
        if (bookingRep.isPresent()) {
            Booking booking1 = bookingRep.get();
            booking1.setEmail(booking.getEmail());
            booking1.setFullName(booking.getFullName());
            booking1.setStartDate(booking.getStartDate());
            booking1.setEndDate(booking1.getEndDate());
            bookingRepository.save(booking1);
            return booking1;
        } else {
            throw new NotFoundException("Booking not found");
        }
    }

    @Override
    @Transactional
    public void deleteBooking(String bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
        } else {
            throw new NotFoundException("Booking not found");
        }
    }

    private void validateDates(Booking booking) {
        if (booking.getStartDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new DateException("Start date need to be reserved minimum 1 day(s) ahead of arrival");
        }
        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new DateException("End date Before Start date");
        }
        long spaceBetweenDays = Duration.between(booking.getStartDate().atTime(0, 0), booking.getEndDate().atTime(0, 0)).toDays();
        if (spaceBetweenDays > 3) {
            throw new DateException("Space between dates higher than 3");
        }
        if(spaceBetweenDays == 0){
            throw new DateException("Space between dates needs to be higher than 0");
        }
        if (booking.getStartDate().isAfter(LocalDate.now().plus(1, ChronoUnit.MONTHS))) {
            throw new DateException("The campsite can be reserved up to 1 month in advance.");
        }
    }

    private boolean isFreeBooking(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = entityQueryService.findByStartDateEndDate(startDate, endDate);
        return bookings.isEmpty();
    }

}
