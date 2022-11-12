package com.campsitemanagement.mapper;

import com.campsitemanagement.dto.BookingRequestDto;
import com.campsitemanagement.dto.BookingResponseDto;
import com.campsitemanagement.entity.Booking;
import org.mapstruct.Mapper;

/**
 * Booking mapper.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper {

    /**
     * maping the values of {@link BookingRequestDto} to {@link Booking}.
     *
     * @param bookingRequestDto {@link BookingRequestDto}
     * @return {@link Booking}
     */
    Booking bookingRequestDtoToBooking(BookingRequestDto bookingRequestDto);

    /**
     * maping the values of {@link Booking} to {@link BookingResponseDto}.
     *
     * @param booking {@link Booking}
     * @return {@link BookingResponseDto}
     */
    BookingResponseDto bookingToBookingResponseDto(Booking booking);
}
