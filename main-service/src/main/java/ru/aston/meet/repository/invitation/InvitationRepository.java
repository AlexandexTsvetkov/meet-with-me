package ru.aston.meet.repository.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.invitation.Invitation;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    /**
     * Возвращает приглашение по идентификатору пользователя и встречи
     */
    Optional<Invitation> findByInvitedIdAndMeetingId(Long userId, Long meetingId);

    /**
     * Возвращает список приглашений по идентификатору встречи
     */
    List<Invitation> findByMeetingId(Long meetingId);

    /**
     * Возвращает список приглашений по идентификатору пользователя
     */
    List<Invitation> findByInvitedId(Long userId);
}
