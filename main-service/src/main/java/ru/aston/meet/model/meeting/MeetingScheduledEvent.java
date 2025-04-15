package ru.aston.meet.model.meeting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс, представляющий событие запланированной встречи.
 * Используется для передачи данных о встрече в других частях системы,
 * например, при обработке событий или уведомлений.
 */
@RequiredArgsConstructor
@Getter
public class MeetingScheduledEvent {
    /**
     * Объект встречи, для которой создано событие
     * Не может быть null
     */
    private final Meeting meeting;
}