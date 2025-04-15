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

/**
 * Глобальный обработчик исключений для REST контроллеров.
 *
 * <p>Перехватывает исключения, возникающие в процессе работы контроллеров,
 * и преобразует их в стандартизированные ответы с соответствующими HTTP статусами.
 *
 * <p>Поддерживает обработку следующих типов исключений:
 * <ul>
 *   <li>{@link AlreadyExistsException} - конфликт существующих данных (HTTP 500)</li>
 *   <li>{@link NotFoundException} - объект не найден (HTTP 404)</li>
 *   <li>{@link InvitationException} - ошибка операции с приглашением (HTTP 400)</li>
 *   <li>{@link AuthenticationException} - ошибка аутентификации (HTTP 401)</li>
 *   <li>Общие исключения {@link Exception} (HTTP 500)</li>
 * </ul>
 *
 * <p>Для каждого типа исключения формируется ответ {@link ErrorResponse},
 * содержащий:
 * <ul>
 *   <li>HTTP статус</li>
 *   <li>Причину ошибки</li>
 *   <li>Сообщение об ошибке</li>
 *   <li>Временную метку возникновения</li>
 * </ul>
 *
 * @see ErrorResponse
 */
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
