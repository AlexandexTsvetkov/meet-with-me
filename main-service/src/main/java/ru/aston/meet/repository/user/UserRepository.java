package ru.aston.meet.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aston.meet.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Возвращает пользователя по email
     */
    Optional<User> findByEmail(String email);

    /**
     * Возвращает пользователя по email
     */
    @Query("SELECT u FROM User u JOIN MeetingParticipant mp ON u.id = mp.userId WHERE mp.meetingId = :meetingId")
    List<User> findUsersByMeetingId(@Param("meetingId") Long meetingId);
}
