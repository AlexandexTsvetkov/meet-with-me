package ru.aston.meet.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aston.meet.dto.error.ErrorResponse;
import ru.aston.meet.exception.AlreadyExistsException;
import ru.aston.meet.exception.AuthenticationException;
import ru.aston.meet.exception.InvitationException;
import ru.aston.meet.exception.NotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponse handleThrowable(Exception e) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason("Unexpected reason")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponse handleAlreadyExistsException(AlreadyExistsException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason("User exists")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handleNotFoundException(NotFoundException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .reason("The required object was not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse handleInvitationException(InvitationException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Invalid invitation operation")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ErrorResponse handleAuthenticationException(AuthenticationException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.toString())
                .reason("Authentication failed")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
