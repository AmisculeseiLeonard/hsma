package com.healthcaresocialmedia.relationshipservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException e) {
        return createErrorInfo(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({MessagingException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleMessagingException(MessagingException e) {
        return createErrorInfo(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ErrorInfo createErrorInfo(Exception exception, HttpStatus status, String message) {
        log.error("Error occurred", exception);
        return new ErrorInfo(message, status.value(), ZonedDateTime.now());
    }
}
