package com.campsitemanagement.service

import com.campsitemanagement.entity.Booking
import com.campsitemanagement.repository.BookingRepository
import com.campsitemanagement.service.BookingService
import com.campsitemanagement.service.BookingServiceImpl
import spock.lang.Specification

import java.time.LocalDate

class BookingServiceSpec extends Specification {

    BookingRepository bookingRepository = Mock(BookingRepository)
    BookingService bookingService = new BookingServiceImpl(bookingRepository)

    def 'Booking insert with success'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking()

        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> new ArrayList<Booking>()
        1 * bookingRepository.insert(bookingInsert) >> bookingInsert

        when:

        Booking booking = bookingService.addBooking(bookingInsert)

        then:

        booking == bookingInsert

        and:

        noExceptionThrown()
    }

    def createBooking(){
        Booking booking = new Booking(
                'id',
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3),
                0,
                'email',
                'fullName'
        )
        booking
    }
}
