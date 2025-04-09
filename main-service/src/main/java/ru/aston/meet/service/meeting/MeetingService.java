package ru.aston.meet.service.meeting;

import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {

    MeetingDto create(MeetingDto meetingDto, User user);

    Meeting findById(long meetingId);

    MeetingDto update(long meetingId, MeetingDto meetingDto, long userId);

    void delete(long meetingId, Long userId);

    List<Meeting> getMeetingsByDateForParticipants(LocalDate eventDate, List<Long> participantsId);

    void addConfirmedParticipants(Meeting meeting, Long serId);

    void deleteConfirmedParticipants(Meeting meeting, Long serId);
}
