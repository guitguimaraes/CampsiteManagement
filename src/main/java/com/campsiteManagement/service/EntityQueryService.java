package com.campsiteManagement.service;

import com.campsiteManagement.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface EntityQueryService {

    List<Booking> findByStartDateEndDate(LocalDate startDate, LocalDate endDate);
}
