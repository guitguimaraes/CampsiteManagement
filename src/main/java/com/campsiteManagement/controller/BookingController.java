package com.campsiteManagement.controller;

import com.campsiteManagement.dto.BookingRequestDto;
import com.campsiteManagement.dto.BookingResponseDto;
import com.campsiteManagement.entity.Booking;
import com.campsiteManagement.mapper.CampsiteMapper;
import com.campsiteManagement.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Rest Controller to manage booking.
 */
@RestController
@Slf4j
@Api(tags = "Booking Controller")
@RequestMapping(path = "/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @ApiOperation(value = "Add new Booking")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponseDto addBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("Received request to add booking: {}", kv("addBooking", bookingRequestDto));

        final Booking booking = bookingService.addBooking(CampsiteMapper.bookingRequestDtoToBooking(bookingRequestDto));

        log.info("Booking {} added with success", kv("addedBooking", booking));

        return CampsiteMapper.bookingTobookingResponseDto(booking);
    }

    @ApiOperation(value = "Update Booking")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponseDto updateBooking(@PathVariable String bookingId, @Valid @RequestBody BookingRequestDto bookingUpdateRequestDto) {
        log.info("Received request to update booking: {}", kv("updateBooking",  bookingId));

        final Booking booking = bookingService.updateBooking(bookingId, CampsiteMapper.bookingRequestDtoToBooking(bookingUpdateRequestDto));

        log.info("Booking {} updated with success", kv("updateBooking", booking));

        return CampsiteMapper.bookingTobookingResponseDto(booking);
    }

    @ApiOperation(value = "Delete Booking")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        log.info("Received request to delete booking: {}", kv("bookingId", bookingId));

        bookingService.deleteBooking(bookingId);

        log.info("Booking {} deleted with success", kv("deleted", bookingId));
    }
}
