package ru.aston.meet.service;

import ru.aston.meet.dto.MeetingDto;
import ru.aston.meet.model.Meeting;
import ru.aston.meet.model.User;

public interface MeetingService {

    MeetingDto create(MeetingDto meetingDto, User user);

    Meeting findById(long meetingId);

    MeetingDto update(long meetingId, MeetingDto meetingDto, long userId);

    void delete(long meetingId, Long userId);
}
