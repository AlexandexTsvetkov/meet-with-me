package ru.aston.meet.exception;

/**
 * Исключение, выбрасываемое при ошибках аутентификации пользователя.
 * <p>
 * Используется для сигнализации о неудачных попытках входа или отсутствии доступа
 * из-за некорректных учетных данных.
 * </p>
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Создает исключение аутентификации с указанным сообщением.
     *
     * @param message подробное сообщение об ошибке аутентификации
     */
    public AuthenticationException(String message) {
        super(message);
    }
}