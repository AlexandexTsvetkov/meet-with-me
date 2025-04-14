package ru.aston.meet.dto.invitation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.invitation.InvitationStatus;

/**
 * Входной DTO (Data Transfer Object) для редактирования приглашения.
 *
 * <p>{@link #id} - Идентификатор.</p>
 * <p>{@link #status} - Статус.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateInvitationStatusDto {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private InvitationStatus status;
}
