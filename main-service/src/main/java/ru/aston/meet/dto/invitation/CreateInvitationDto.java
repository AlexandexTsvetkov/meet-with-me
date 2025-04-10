package ru.aston.meet.dto.invitation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Входной DTO (Data Transfer Object) для нового приглашения.
 *
 * <p>{@link #meetingId} - Идентификатор встречи.</p>
 * <p>{@link #userId} - Идентификатор приглашенного пользователя.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateInvitationDto {

    @NotNull
    @Positive
    private Long meetingId;

    @NotNull
    @Positive
    private Long userId;
}
