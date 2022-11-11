package com.campsitemanagement.controller;

import com.campsitemanagement.dto.BookingRequestDto;
import com.campsitemanagement.dto.BookingResponseDto;
import com.campsitemanagement.entity.Booking;
import com.campsitemanagement.mapper.CampsiteMapper;
import com.campsitemanagement.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * Method responsible to get all available dates to book.
     * @param startDate - start date of the search
     * @param endDate - end date of the search
     * @return list of available dates
     */
    @ApiOperation(value = "Get Available dates")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{startDate}/{endDate}")
    public List<LocalDate> getAvailableDates(@PathVariable String startDate, @PathVariable String endDate) {
        log.info("Received request to get Available Dates: {}, {}", kv("startDate", startDate), kv("endDate", endDate));

        List<LocalDate> localDates = bookingService.getAvailableBooks(startDate, endDate);

        log.info("Available dates returned with success");

        return localDates;
    }

    /**
     * method responsible to add a book.
     * @param bookingRequestDto booking information to be added
     * @return Booking id added
     */
    @ApiOperation(value = "Add new Booking")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponseDto addBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("Received request to add booking: {}", kv("addBooking", bookingRequestDto));

        final Booking booking = bookingService.addBooking(CampsiteMapper.bookingRequestDtoToBooking(bookingRequestDto));

        log.info("Booking {} added with success", kv("addedBooking", booking));

        return CampsiteMapper.bookingTobookingResponseDto(booking);
    }

    /**
     * method responsible to update a book.
     * @param bookingId - booking id that will be updated
     * @param bookingUpdateRequestDto - booking information to be updated
     * @return booking id.
     */
    @ApiOperation(value = "Update Booking")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{bookingId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponseDto updateBooking(@PathVariable String bookingId, @Valid @RequestBody BookingRequestDto bookingUpdateRequestDto) {
        log.info("Received request to update booking: {}", kv("updateBooking", bookingId));

        final Booking booking = bookingService.updateBooking(bookingId, CampsiteMapper.bookingRequestDtoToBooking(bookingUpdateRequestDto));

        log.info("Booking {} updated with success", kv("updateBooking", booking));

        return CampsiteMapper.bookingTobookingResponseDto(booking);
    }

    /**
     * method responsible to delete a book.
     * @param bookingId - booking id that will be deleted
     */
    @ApiOperation(value = "Delete Booking")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        log.info("Received request to delete booking: {}", kv("bookingId", bookingId));

        bookingService.deleteBooking(bookingId);

        log.info("Booking {} deleted with success", kv("deleted", bookingId));
    }
}
