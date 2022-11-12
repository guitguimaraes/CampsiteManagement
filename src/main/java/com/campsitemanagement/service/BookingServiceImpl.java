package com.campsitemanagement.service;

import com.campsitemanagement.configuration.CampsiteConfiguration;
import com.campsitemanagement.entity.Booking;
import com.campsitemanagement.exception.BookingNotAvailableException;
import com.campsitemanagement.exception.DateException;
import com.campsitemanagement.exception.NotFoundException;
import com.campsitemanagement.repository.BookingRepository;
import com.campsitemanagement.util.DateRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
     * Not Found message.
     */
    public static final String BOOKING_NOT_FOUND = "Booking not found";

    private final BookingRepository bookingRepository;

    private final CampsiteConfiguration campsiteConfiguration;

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(BOOKING_NOT_FOUND));

    }

    @Override
    @Transactional
    public List<LocalDate> getAvailableBooks(String startDate, String endDate) {
        try {
            LocalDate startDateLocal = LocalDate.parse(startDate);
            LocalDate endDateLocal = LocalDate.parse(endDate);
            return getFreeLocalDate(startDateLocal, endDateLocal);
        } catch (DateTimeParseException dtException) {
            throw new DateException("Date invalid");
        }
    }

    @Override
    @Transactional
    public Booking addBooking(Booking booking) {
        validateDates(booking);
        if (isFreeBooking(booking.getStartDate(), booking.getEndDate(), null)) {
            return bookingRepository.insert(booking);
        } else {
            throw new BookingNotAvailableException("Booking Not available to this date.");
        }
    }

    @Override
    @Transactional
    public Booking updateBooking(String bookingId, Booking booking) {
        validateDates(booking);
        Optional<Booking> bookingRep = bookingRepository.findById(bookingId);
        if (bookingRep.isPresent()) {
            Booking booking1 = bookingRep.get();
            if ((booking1.getStartDate().equals(booking.getStartDate())
                    && (booking1.getEndDate().equals(booking.getEndDate())))
                    || isFreeBooking(booking.getStartDate(), booking.getEndDate(), booking1)) {
                booking1.setEmail(booking.getEmail());
                booking1.setFullName(booking.getFullName());
                booking1.setStartDate(booking.getStartDate());
                booking1.setEndDate(booking1.getEndDate());
                bookingRepository.save(booking1);
                return booking1;
            } else {
                throw new BookingNotAvailableException("Booking Not available to this date.");
            }
        } else {
            throw new NotFoundException(BOOKING_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteBooking(String bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            bookingRepository.delete(booking.get());
        } else {
            throw new NotFoundException(BOOKING_NOT_FOUND);
        }
    }

    private void validateDates(Booking booking) {
        if (booking.getStartDate().isBefore(LocalDate.now().plusDays(campsiteConfiguration.getMinDaysAhead()))) {
            throw new DateException("Start date need to be reserved minimum " + campsiteConfiguration.getMinDaysAhead() + " day(s) ahead of arrival");
        }
        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new DateException("End date Before Start date");
        }
        long spaceBetweenDays = Duration.between(booking.getStartDate().atTime(0, 0), booking.getEndDate().atTime(0, 0)).toDays();
        if (spaceBetweenDays > campsiteConfiguration.getMaxDaysBooking()) {
            throw new DateException("Space between dates higher than " + campsiteConfiguration.getMaxDaysBooking());
        }
        if (spaceBetweenDays == 0) {
            throw new DateException("Space between dates needs to be higher than 0");
        }
        if (booking.getStartDate().isAfter(LocalDate.now().plusDays(campsiteConfiguration.getMaxDaysAhead()))) {
            throw new DateException("The campsite can not be reserved up to " + campsiteConfiguration.getMaxDaysAhead() + "days in advance.");
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

    private boolean isFreeBooking(LocalDate startDate, LocalDate endDate, Booking booking) {
        List<Booking> bookings = bookingRepository.findByEndDateBetweenOrStartDateBetween(startDate, endDate.plusDays(1), startDate, endDate);
        if (booking != null && !StringUtils.isEmpty(booking.getId())) {
            bookings.remove(booking);
        }
        return bookings.isEmpty();
    }

}
