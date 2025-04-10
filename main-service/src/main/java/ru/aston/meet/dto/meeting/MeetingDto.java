package ru.aston.meet.dto.meeting;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Входной DTO (Data Transfer Object) для представления информации о встрече.
 *
 * <p>{@link #id} - Идентификатор.</p>
 * <p>{@link #title} - Название.</p>
 * <p>{@link #description} - Описание.</p>
 * <p>{@link #eventDate} - Дата прведения.</p>
 * <p>{@link #location} - Место проведения.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class MeetingDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Future(message = "Дата встречи должна быть в будущем")
    private LocalDateTime eventDate;

    @NotBlank
    private String location;
}
