package ru.aston.meet.repository.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aston.meet.model.meeting.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m " +
            "WHERE (:start IS NULL OR m.eventDate >= :start) " +
            "AND (:end IS NULL OR m.eventDate <= :end) " +
            "AND (:participants IS NULL OR EXISTS (" +
            "   SELECT p FROM m.participants p WHERE p IN :participants" +
            "))")
    List<Meeting> findByDateRangeAndParticipants(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end,
                                                 @Param("participants") List<Long> participants);
}
