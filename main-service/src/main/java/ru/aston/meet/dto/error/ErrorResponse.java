package ru.aston.meet.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 * Класс для представления ответа об ошибке.
 */
@Builder
@Getter
@Setter
public class ErrorResponse {

    /*
     * Статус HTTP ответа.
     */
    private String status;

    /*
     * Причина, по которой произошла ошибка.
     */
    private String reason;

    /*
     * Сообщение об ошибке, описывающее проблему.
     */
    private String message;

    /*
     * Время, когда произошла ошибка.
     * Формат: "yyyy-MM-dd HH:mm:ss".
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
