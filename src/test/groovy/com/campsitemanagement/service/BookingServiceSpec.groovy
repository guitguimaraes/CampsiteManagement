package com.campsitemanagement.service

import com.campsitemanagement.configuration.CampsiteConfiguration
import com.campsitemanagement.entity.Booking
import com.campsitemanagement.exception.BookingNotAvailableException
import com.campsitemanagement.exception.DateException
import com.campsitemanagement.exception.NotFoundException
import com.campsitemanagement.repository.BookingRepository
import spock.lang.Specification

import java.time.LocalDate

class BookingServiceSpec extends Specification {

    BookingRepository bookingRepository = Mock(BookingRepository)
    CampsiteConfiguration campsiteConfiguration = createCampsiteConfiguration()
    BookingService bookingService = new BookingServiceImpl(bookingRepository, campsiteConfiguration)

    def 'Booking get with success'() {
        given: 'correct bookingId to delete'

        Booking bookingDeleted = createBooking(3)

        1 * bookingRepository.findById('bookingId') >> Optional.of(bookingDeleted)

        when:

        bookingService.getBookingById('bookingId')

        then:

        noExceptionThrown()
    }

    def 'Booking not found to get'() {
        given: 'incorrect bookingId to delete'


        1 * bookingRepository.findById('bookingId') >> Optional.empty()

        when:

        bookingService.getBookingById('bookingId')

        then:

        thrown(NotFoundException)
    }

    def 'Booking insert with success'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)

        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> new ArrayList<Booking>()
        1 * bookingRepository.insert(bookingInsert) >> bookingInsert

        when:

        Booking booking = bookingService.addBooking(bookingInsert)

        then:

        booking == bookingInsert

        and:

        noExceptionThrown()
    }

    def 'Booking insert with Date not available'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)


        def DatesList = [Mock(Booking), Mock(Booking)]
        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> DatesList

        when:

        bookingService.addBooking(bookingInsert)

        then:

        thrown(BookingNotAvailableException)
    }

    def 'Booking insert with startDate equals today'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)
        bookingInsert.startDate = LocalDate.now()

        when:

        bookingService.addBooking(bookingInsert)

        then:

        thrown(DateException)
    }

    def 'Booking insert with date range higher than 3 '() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)
        bookingInsert.startDate = LocalDate.now()

        when:

        bookingService.addBooking(bookingInsert)

        then:

        thrown(DateException)
    }

    def 'Booking update with success'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)
        Booking bookingUpdate = createBooking(2)

        1 * bookingRepository.findById('id') >> Optional.of(bookingInsert)
        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> new ArrayList<Booking>()
        1 * bookingRepository.save(bookingInsert) >> bookingInsert

        when:

        Booking booking = bookingService.updateBooking('id', bookingUpdate)

        then:

        booking == bookingInsert

        and:

        noExceptionThrown()
    }

    def 'Booking update fail date not available'() {
        given: 'correct booking'

        Booking booking = createBooking(3)
        Booking bookingUpdate = createBooking(2)
        def DatesList = [Mock(Booking), Mock(Booking)]

        1 * bookingRepository.findById('id') >> Optional.of(booking)
        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> DatesList

        when:

        bookingService.updateBooking('id', bookingUpdate)

        then:

        thrown(BookingNotAvailableException)
    }

    def 'Booking update with Date not available'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking(3)


        def DatesList = [Mock(Booking), Mock(Booking)]
        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> DatesList

        when:

        bookingService.addBooking(bookingInsert)

        then:

        thrown(BookingNotAvailableException)
    }

    def 'Booking update with startDate equals today'() {
        given: 'correct booking'

        Booking booking = createBooking(3)
        booking.startDate = LocalDate.now()

        when:

        bookingService.updateBooking('id', booking)

        then:

        thrown(DateException)
    }

    def 'Booking update with date range higher than 3 '() {
        given: 'correct booking'

        Booking booking = createBooking(3)
        booking.startDate = LocalDate.now()

        when:

        bookingService.updateBooking('id', booking)

        then:

        thrown(DateException)
    }

    def 'Booking delete with success'() {
        given: 'correct bookingId to delete'

        Booking bookingDeleted = createBooking(3)

        1 * bookingRepository.findById('bookingId') >> Optional.of(bookingDeleted)
        1 * bookingRepository.delete(bookingDeleted)

        when:

        bookingService.deleteBooking('bookingId')

        then:

        noExceptionThrown()
    }

    def 'Booking not found to delete'() {
        given: 'incorrect bookingId to delete'


        1 * bookingRepository.findById('bookingId') >> Optional.empty()

        when:

        bookingService.deleteBooking('bookingId')

        then:

        thrown(NotFoundException)
    }


    def createBooking(date) {
        Booking booking = new Booking()
        booking.id = 'id'
        booking.startDate = LocalDate.now().plusDays(1)
        booking.endDate = LocalDate.now().plusDays(date)
        booking.email = 'email'
        booking.fullName = 'fullName'
        booking
    }

    def createCampsiteConfiguration() {
        CampsiteConfiguration campsiteConfiguration = new CampsiteConfiguration();
        campsiteConfiguration.maxDaysAhead = 30
        campsiteConfiguration.maxDaysBooking = 3
        campsiteConfiguration.minDaysAhead = 1
        campsiteConfiguration
    }
}
