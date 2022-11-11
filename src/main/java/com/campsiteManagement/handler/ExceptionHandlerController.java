package com.campsiteManagement.handler;

import com.campsiteManagement.dto.ErrorResponseDto;
import com.campsiteManagement.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controle para tratar todas as exceptions lançadas pela aplicação.
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleInternalServerError(Exception exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(Exception exception) {
        return handleException(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponseDto> handleException(Exception exception, HttpStatus httpStatus) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

}
