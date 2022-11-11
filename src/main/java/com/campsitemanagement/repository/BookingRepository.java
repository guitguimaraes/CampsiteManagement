package com.campsitemanagement.repository;

import com.campsitemanagement.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Booking repository.
 */
public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByEndDateBetweenOrStartDateBetween(LocalDate startDate, LocalDate endDate, LocalDate startDate2, LocalDate endDate2);
}
