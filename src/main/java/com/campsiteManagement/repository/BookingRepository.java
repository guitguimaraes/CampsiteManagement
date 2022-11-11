package com.campsiteManagement.repository;

import com.campsiteManagement.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByEndDateBetweenOrStartDateBetween(LocalDate startDate, LocalDate endDate, LocalDate startDate2, LocalDate endDate2);
}
