package ru.aston.meet.mapper.avro.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;
import ru.aston.meet.model.user.User;

import java.time.Instant;
import java.util.List;

@Component
public class MeetingAvroMapper {

    public MeetingAvro mapToMeetingAvro(Meeting meeting, MeetingEventType meetingEventType, List<User> users) {
        return MeetingAvro.newBuilder()
                .setId(meeting.getId().toString())
                .setTimestamp(Instant.now())
                .setPayload(mapToMeetingSpecificRecordBase(meeting, meetingEventType, users))
                .build();
    }

    public SpecificRecordBase mapToMeetingSpecificRecordBase(Meeting meeting, MeetingEventType strategy, List<User> users) {
        return strategy.mapToMeetEventSpecificRecordBase(meeting, users);
    }
}
