package ru.aston.meet.mapper.avro.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;

import java.time.Instant;

@Component
public class MeetingAvroMapper {

    public MeetingAvro mapToMeetingAvro(Meeting meeting, MeetingEventType meetingEventType) {
        return MeetingAvro.newBuilder()
                .setId(meeting.getId().toString())
                .setTimestamp(Instant.now())
                .setPayload(mapToMeetingSpecificRecordBase(meeting, meetingEventType))
                .build();
    }

    public SpecificRecordBase mapToMeetingSpecificRecordBase(Meeting meeting, MeetingEventType strategy) {
        return strategy.mapToMeetEventSpecificRecordBase(meeting);
    }
}
