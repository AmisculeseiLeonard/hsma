package com.healthcaresocialmedia.UserManagementService.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleConstraintViolationException(ConstraintViolationException e) {
        String fieldErrors = e.getConstraintViolations().stream()
                .map(constraintViolation -> String.format("'%s': %s;",
                        ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName(),
                        constraintViolation.getMessage()))
                .collect(Collectors.joining());
        return createErrorInfo(e, HttpStatus.BAD_REQUEST, String.format("Invalid parameters: [%s]", fieldErrors));
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException e) {
        return createErrorInfo(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({EmailAlreadyRegisteredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {
        return createErrorInfo(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({UserAuthenticationFailedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo handleUserAuthenticationFailedException(UserAuthenticationFailedException e) {
        return createErrorInfo(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler({UsernameAlreadyRegisteredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleUsernameAlreadyRegisteredException(UsernameAlreadyRegisteredException e) {
        return createErrorInfo(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ErrorInfo createErrorInfo(Exception exception, HttpStatus status, String message) {
        log.error("Error occurred", exception);
        return new ErrorInfo(message, status.value(), ZonedDateTime.now());
    }
}
