package ru.aston.meet.dto.meeting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 * Выходной DTO (Data Transfer Object) для представления информации о встрече.
 */
@Getter
@Setter
@NoArgsConstructor
public class MeetingDto {

    /*
     * Идентификатор встречи.
     */
    private Long id;

    /*
     * Название встречи.
     */
    @NotBlank
    private String title;

    /*
     * Описание встерчи.
     */
    @NotBlank
    private String description;

    /*
     * Дата прведения встречи.
     */
    @NotNull
    private LocalDateTime eventDate;

    /*
     * Место проведения встерчи.
     */
    @NotBlank
    private String location;
}
