package com.campsitemanagement.mapper;

import com.campsitemanagement.dto.BookingRequestDto;
import com.campsitemanagement.dto.BookingResponseDto;
import com.campsitemanagement.entity.Booking;

/**
 * Mapper to handle the request and response.
 */
public class CampsiteMapper {

    /**
     * Responsible to map request to booking class.
     *
     * @param bookingRequestDto request to add/update booking
     * @return Booking
     */
    public static Booking bookingRequestDtoToBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .fullName(bookingRequestDto.getName())
                .email(bookingRequestDto.getEmail())
                .startDate(bookingRequestDto.getStartDate())
                .endDate(bookingRequestDto.getEndDate())
                .build();
    }

    /**
     * Response to map booking to response.
     *
     * @param booking - booking entity
     * @return BookingResponseDto
     */
    public static BookingResponseDto bookingTobookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .build();
    }
}
