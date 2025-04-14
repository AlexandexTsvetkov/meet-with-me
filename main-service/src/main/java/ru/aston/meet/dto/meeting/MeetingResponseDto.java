package ru.aston.meet.dto.meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Выходной DTO (Data Transfer Object) для представления информации о встрече.
 *
 * <p>{@link #id} - Идентификатор.</p>
 * <p>{@link #title} - Название.</p>
 * <p>{@link #description} - Описание.</p>
 * <p>{@link #createOn} - Дата создания.</p>
 * <p>{@link #eventDate} - Дата прведения.</p>
 * <p>{@link #initiatorId} - Идентификатор создателя.</p>
 * <p>{@link #location} - Место проведения.</p>
 * <p>{@link #participants} - Список участников.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class MeetingResponseDto {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime createOn;

    private LocalDateTime eventDate;

    private Long initiatorId;

    private String location;

    private Set<Long> participants;
}
