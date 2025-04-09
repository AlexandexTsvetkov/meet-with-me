package ru.aston.meet.service.meeting;

import ru.aston.meet.dto.meeting.MeetingDto;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

public interface MeetingService {

    MeetingDto create(MeetingDto meetingDto, User user);

    Meeting findById(long meetingId);

    MeetingDto update(long meetingId, MeetingDto meetingDto, long userId);

    void delete(long meetingId, Long userId);
}
