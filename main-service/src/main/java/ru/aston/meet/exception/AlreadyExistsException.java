package ru.aston.meet.exception;

/**
 * Исключение, выбрасываемое при попытке создать сущность, которая уже существует.
 * <p>
 * Используется для сигнализации о конфликте уникальности, например, при создании пользователя
 * с уже существующим email.
 * </p>
 */
public class AlreadyExistsException extends RuntimeException {

    /**
     * Создаёт новое исключение с указанным сообщением.
     *
     * @param message подробное сообщение об ошибке
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}