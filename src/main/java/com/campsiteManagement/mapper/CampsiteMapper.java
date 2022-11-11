package com.campsiteManagement.mapper;

import com.campsiteManagement.dto.BookingRequestDto;
import com.campsiteManagement.dto.BookingResponseDto;
import com.campsiteManagement.entity.Booking;

public class CampsiteMapper {

    public static Booking bookingRequestDtoToBooking(BookingRequestDto bookingRequestDto){
        return Booking.builder()
                .fullName(bookingRequestDto.getName())
                .email(bookingRequestDto.getEmail())
                .startDate(bookingRequestDto.getStartDate())
                .endDate(bookingRequestDto.getEndDate())
                .build();
    }

    public static BookingResponseDto bookingTobookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .build();
    }
}
