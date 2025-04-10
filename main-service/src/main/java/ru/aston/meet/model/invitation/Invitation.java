package ru.aston.meet.model.invitation;

import jakarta.persistence.*;
import lombok.*;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

/**
 * Класс, представляющий приглашение в системе.
 *
 * <p>{@link #id} - Идентификатор приглашения.</p>
 * <p>{@link #meeting} - Встреча.</p>
 * <p>{@link #invited} - Приглашенный пользователь.</p>
 * <p>{@link #status} - Статус приглашения.</p>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @OneToOne
    @JoinColumn(name = "invited_id")
    private User invited;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
}
