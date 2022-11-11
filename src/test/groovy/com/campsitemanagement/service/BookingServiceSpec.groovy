package com.campsitemanagement.service

import com.campsitemanagement.entity.Booking
import com.campsitemanagement.exception.BookingNotAvailableException
import com.campsitemanagement.exception.NotFoundException
import com.campsitemanagement.repository.BookingRepository
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

    def 'Booking insert with Date not available'() {
        given: 'correct booking'

        Booking bookingInsert = createBooking()


        def DatesList = [Mock(Booking), Mock(Booking)]
        1 * bookingRepository.findByEndDateBetweenOrStartDateBetween(_, _, _, _) >> DatesList

        when:

        bookingService.addBooking(bookingInsert)

        then:

        thrown(BookingNotAvailableException)
    }

    def 'Booking delete with success'() {
        given: 'correct bookingId to delete'

        Booking bookingDeleted = createBooking()

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


   /** @Execution(value = ExecutionMode.CONCURRENT)
    defTestparalel(){
        'Booking insert with success'()
    }
*/
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
