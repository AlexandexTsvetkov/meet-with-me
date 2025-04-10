package ru.aston.meet.service.notification;

import ru.aston.meet.mapper.user.PartipantEventType;
import ru.aston.meet.model.invitation.Invitation;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;
import ru.aston.meet.model.user.User;

public interface NotificationService {
    void sendMeetingEvent(Meeting meeting, MeetingEventType meetingEventType);
    void sendInvitation(Invitation invitation);
    void sendPartipant(Meeting meeting, User user, PartipantEventType partipantEventType);
}
