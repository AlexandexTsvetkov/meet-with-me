package ru.aston.meet.mapper.avro.partipant;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.meet.mapper.user.PartipantEventType;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

@Component
public class ParticipantAvroMapper {

    public ParticipantAvro mapToParticipantAvro(Meeting meeting, User participant, PartipantEventType eventType) {
        return ParticipantAvro.newBuilder()
                .setId(participant.getId().toString())
                .setMeetingId(meeting.getId().toString())

                .build();
    }

    public SpecificRecordBase mapToPartipantSpecificRecordBase(User participant, PartipantEventType strategy) {
        return strategy.mapToPartipantEventSpecificRecordBase(participant);
    }
}
