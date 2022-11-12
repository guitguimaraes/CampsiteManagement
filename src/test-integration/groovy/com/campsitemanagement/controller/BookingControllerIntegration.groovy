package com.campsitemanagement.controller

import com.campsitemanagement.dto.BookingRequestDto
import com.campsitemanagement.dto.BookingResponseDto
import com.campsitemanagement.dto.ErrorResponseDto
import com.campsitemanagement.repository.BookingRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import java.time.LocalDate

@Stepwise
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerIntegration extends Specification{

    @LocalServerPort
    int port

    @Shared
    RequestSpecification requestSpecification

    @Shared
    String bookingId

    def setup() {
        requestSpecification = RestAssured.given()
                .baseUri('http://localhost')
                .port(port)
                .basePath('/booking/v1')
                .contentType(ContentType.JSON)
    }

    @Autowired
    BookingRepository bookingRepository

    def "get dates available without add"() {
        given: 'booking dto '

        String startDate = LocalDate.now().plusDays(1).toString()
        String endDate = LocalDate.now().plusDays(15).toString()

        when: 'POST to create booking'
        Response response = requestSpecification
                .get("/${startDate}/${endDate}")

        response.then().log().all()

        then: 'Should return Ok status'
        response.statusCode == 200

        and: 'Should return message error'
        response.body.as(List).size() == 15
    }

    def "Booking not create when start date is today'"() {
        given: 'booking dto '
        BookingRequestDto bookingRequestDto = new BookingRequestDto()
        bookingRequestDto.email = 'email'
        bookingRequestDto.fullName = 'name'
        bookingRequestDto.startDate = LocalDate.now()
        bookingRequestDto.endDate = LocalDate.now().plusDays(3)

        when: 'POST to create booking'
        Response response = requestSpecification
                .body(bookingRequestDto)
                .post()

        response.then().log().all()

        then: 'Should return Bad request'
        response.statusCode == 400

        and: 'Should return message error'
        response.body.as(ErrorResponseDto).message == "Start date need to be reserved minimum 1 day(s) ahead of arrival"
    }

    def "Booking create when call the correct endpoint'"() {
        given: 'booking dto '
        BookingRequestDto bookingRequestDto = new BookingRequestDto()
        bookingRequestDto.email = 'email'
        bookingRequestDto.fullName = 'name'
        bookingRequestDto.startDate = LocalDate.now().plusDays(1)
        bookingRequestDto.endDate = LocalDate.now().plusDays(3)

        when: 'POST to create booking'
        Response response = requestSpecification
                .body(bookingRequestDto)
                .post()

        response.then().log().all()

        bookingId = response.path('id')

        then: 'Should return CREATED status'
        response.statusCode == 201

        and: 'BookingResponseDto should be returned'
        response.body().as(BookingResponseDto) != null
    }

    def "Booking Get after create a booking"() {
        given: 'booking id '

        when: 'POST to create booking'
        Response response = requestSpecification
                .get("/${bookingId}")

        response.then().log().all()

        then: 'Should return OK status'
        response.statusCode == 200

        and: 'BookingResponseDto should be returned'
        response.body().as(BookingResponseDto) != null
    }

    def "Booking Get with invalid Id"() {
        given: 'booking invalid id '
        String id = UUID.randomUUID()

        when: 'POST to create booking'
        Response response = requestSpecification
                .get("/${id}")

        response.then().log().all()

        then: 'Should return Bad request'
        response.statusCode == 404

        and: 'Should return message error'
        response.body.as(ErrorResponseDto).message == "Booking not found"
    }

    def "Booking create when call the correct endpoint to test dates"() {
        given: 'booking dto '
        BookingRequestDto bookingRequestDto = new BookingRequestDto()
        bookingRequestDto.email = 'email'
        bookingRequestDto.fullName = 'name'
        bookingRequestDto.startDate = LocalDate.now().plusDays(5)
        bookingRequestDto.endDate = LocalDate.now().plusDays(8)

        when: 'POST to create booking'
        Response response = requestSpecification
                .body(bookingRequestDto)
                .post()

        response.then().log().all()

        then: 'Should return CREATED status'
        response.statusCode == 201

        and: 'BookingResponseDto should be returned'
        response.body().as(BookingResponseDto) != null
    }

    def "Booking update when call the correct endpoint'"() {
        given: 'booking dto '
        BookingRequestDto bookingRequestDto = new BookingRequestDto()
        bookingRequestDto.email = 'email'
        bookingRequestDto.fullName = 'name'
        bookingRequestDto.startDate = LocalDate.now().plusDays(2)
        bookingRequestDto.endDate = LocalDate.now().plusDays(4)

        when: 'POST to create booking'
        Response response = requestSpecification
                .body(bookingRequestDto)
                .put("/${bookingId}")

        response.then().log().all()

        bookingId = response.path('id')

        then: 'Should return OK status'
        response.statusCode == 200

        and: 'BookingResponseDto should be returned'
        response.body().as(BookingResponseDto) != null
    }

    def "update fail when already have booking in the dates"() {
        given: 'booking dto '
        BookingRequestDto bookingRequestDto = new BookingRequestDto()
        bookingRequestDto.email = 'email'
        bookingRequestDto.fullName = 'name'
        bookingRequestDto.startDate = LocalDate.now().plusDays(6)
        bookingRequestDto.endDate = LocalDate.now().plusDays(8)

        when: 'POST to create booking'
        Response response = requestSpecification
                .body(bookingRequestDto)
                .put("/${bookingId}")

        response.then().log().all()

        then: 'Should return Bad request'
        response.statusCode == 400

        and: 'Should return message error'
        response.body.as(ErrorResponseDto).message == "Booking Not available to this date."
    }

    def "get dates available without the dates already created"() {
        given: 'booking dto '

        String startDate = LocalDate.now().plusDays(1).toString()
        String endDate = LocalDate.now().plusDays(15).toString()

        when: 'POST to create booking'
        Response response = requestSpecification
                .get("/${startDate}/${endDate}")

        response.then().log().all()

        then: 'Should return Ok status'
        response.statusCode == 200

        and: 'Should return message error'
        response.body.as(List).size() == 9
    }

    def "Delete booking with success"() {
        given: 'booking id '

        when: 'POST to create booking'
        Response response = requestSpecification
                .delete("/${bookingId}")

        response.then().log().all()

        then: 'Should return Bad request'
        response.statusCode == 200
    }

    def "Delete booking with invalid id"() {
        given: 'booking id '

        String id = UUID.randomUUID()

        when: 'POST to create booking'
        Response response = requestSpecification
                .delete("/${id}")

        response.then().log().all()

        then: 'Should return Bad request'
        response.statusCode == 404

        and: 'Should return message error'
        response.body.as(ErrorResponseDto).message == "Booking not found"
    }
}
