package com.campsitemanagement.service;

import com.campsitemanagement.entity.Booking;
import com.campsitemanagement.exception.BookingNotAvailableException;
import com.campsitemanagement.exception.DateException;
import com.campsitemanagement.exception.NotFoundException;
import com.campsitemanagement.repository.BookingRepository;
import com.campsitemanagement.util.DateRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Booking Service Implementation.
 */
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    /**
     * Max days of a book can be done.
     */
    public static final int MAX_DAYS_BOOK = 3;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public List<LocalDate> getAvailableBooks(String startDate, String endDate) {
        try{
            LocalDate startDateLocal = LocalDate.parse(startDate);
            LocalDate endDateLocal = LocalDate.parse(endDate);
            return getFreeLocalDate(startDateLocal, endDateLocal);
        }catch (DateTimeParseException dt){
            throw new DateException("Date invalid");
        }
    }

    @Override
    @Transactional
    public Booking addBooking(Booking booking) {
        validateDates(booking);
        if (isFreeBooking(booking.getStartDate(), booking.getEndDate())) {
            return bookingRepository.insert(booking);
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
            if ((booking1.getStartDate().equals(booking.getStartDate()) &&
                    (booking1.getEndDate().equals(booking.getEndDate()))) ||
                    isFreeBooking(booking.getStartDate(), booking.getEndDate())) {
                booking1.setEmail(booking.getEmail());
                booking1.setFullName(booking.getFullName());
                booking1.setStartDate(booking.getStartDate());
                booking1.setEndDate(booking1.getEndDate());
                bookingRepository.save(booking1);
                return booking1;
            }else{
                throw new BookingNotAvailableException("Booking Not available to this date.");
            }
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
        if (spaceBetweenDays > MAX_DAYS_BOOK) {
            throw new DateException("Space between dates higher than 3");
        }
        if (spaceBetweenDays == 0) {
            throw new DateException("Space between dates needs to be higher than 0");
        }
        if (booking.getStartDate().isAfter(LocalDate.now().plus(1, ChronoUnit.MONTHS))) {
            throw new DateException("The campsite can be reserved up to 1 month in advance.");
        }
    }

    private List<LocalDate> getFreeLocalDate(LocalDate startDate, LocalDate endDate) {
        DateRange dateRange = new DateRange(startDate, endDate);
        List<LocalDate> localDates = dateRange.toList();
        localDates.removeAll(getBookedListBetweenDays(startDate, endDate));
        return localDates;
    }

    private List<LocalDate> getBookedListBetweenDays(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByEndDateBetweenOrStartDateBetween(startDate, endDate, startDate, endDate);
        List<LocalDate> localDates = new ArrayList<>();
        bookings.forEach(
            booking -> localDates.addAll(new DateRange(booking.getStartDate(), booking.getEndDate()).toList())
        );
        return localDates;
    }

    private boolean isFreeBooking(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByEndDateBetweenOrStartDateBetween(startDate, endDate, startDate, endDate);
        return bookings.isEmpty();
    }

}
