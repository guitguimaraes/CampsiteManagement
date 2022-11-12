package com.campsitemanagement.mapper;

import com.campsitemanagement.dto.BookingRequestDto;
import com.campsitemanagement.dto.BookingResponseDto;
import com.campsitemanagement.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking bookingRequestDtoToBooking(BookingRequestDto bookingRequestDto);

    BookingResponseDto bookingToBookingResponseDto(Booking booking);
}
