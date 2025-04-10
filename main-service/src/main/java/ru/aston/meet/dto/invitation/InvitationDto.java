package ru.aston.meet.dto.invitation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.invitation.InvitationStatus;

/**
 * Выходной DTO (Data Transfer Object) для представления информации о приглашении.
 *
 * <p>{@link #id} - Идентификатор приглашения.</p>
 * <p>{@link #meetingId} - Идентификатор встречи.</p>
 * <p>{@link #userId} - Идентификатор приглашенного пользователя.</p>
 * <p>{@link #status} - Статус приглашения.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class InvitationDto {

    private Long id;

    private Long meetingId;

    private Long userId;

    private InvitationStatus status;
}
