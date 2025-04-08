package ru.aston.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.Invitation;
import ru.aston.meet.model.User;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByInvitedIdAndMeetingId(Long userId, Long meetingId);

    List<Invitation> findByMeetingId(Long meetingId);

    List<Invitation> findByInvitedId(Long userId);
}
