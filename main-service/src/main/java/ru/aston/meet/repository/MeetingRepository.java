package ru.aston.meet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.meet.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
