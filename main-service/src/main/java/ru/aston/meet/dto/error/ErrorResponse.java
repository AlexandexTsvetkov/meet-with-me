package ru.aston.meet.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс для представления ответа об ошибке.
 *
 * <p>{@link #status} - Статус HTTP ответа.</p>
 * <p>{@link #reason} - Причина, по которой произошла ошибка.</p>
 * <p>{@link #message} - Сообщение об ошибке, описывающее проблему.</p>
 * <p>{@link #timestamp} - Время, когда произошла ошибка. Формат: "yyyy-MM-dd HH:mm:ss".</p>
 */
@Builder
@Getter
@Setter
public class ErrorResponse {

    private String status;

    private String reason;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
