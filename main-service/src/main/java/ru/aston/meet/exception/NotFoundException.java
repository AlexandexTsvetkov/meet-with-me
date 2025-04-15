package ru.aston.meet.exception;

/**
 * Исключение, выбрасываемое, когда искомый объект не найден.
 * <p>
 * Обычно используется для сигнализации о том, что запрошенный ресурс (например, пользователь,
 * встреча или приглашение) отсутствует в системе.
 * </p>
 */
public class NotFoundException extends RuntimeException {

    /**
     * Создаёт исключение с указанным сообщением, когда объект не найден.
     *
     * @param message подробное сообщение об ошибке
     */
    public NotFoundException(String message) {
        super(message);
    }
}