package ru.aston.meet.dto.meeting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/*
 * Выходной DTO (Data Transfer Object) для представления информации о встрече.
 */

@Getter
@Setter
@NoArgsConstructor
public class MeetingResponseDto {

    /*
     * Идентификатор встречи.
     */
    private Long id;

    /*
     * Название встречи.
     */
    private String title;

    /*
     * Описание встерчи.
     */
    private String description;

    /*
     * Дата создания встречи.
     */
    private LocalDateTime createOn;

    /*
     * Дата прведения встречи.
     */
    private LocalDateTime eventDate;

    /*
     * Идентификатор создателя встречи.
     */
    private Long initiatorId;

    /*
     * Место проведения встерчи.
     */
    private String location;

    /*
     * Список участников встречи.
     */
    private Set<Long> participants;
}
