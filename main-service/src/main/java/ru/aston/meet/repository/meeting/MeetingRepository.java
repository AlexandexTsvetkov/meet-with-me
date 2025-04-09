package ru.aston.meet.repository.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.meeting.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
