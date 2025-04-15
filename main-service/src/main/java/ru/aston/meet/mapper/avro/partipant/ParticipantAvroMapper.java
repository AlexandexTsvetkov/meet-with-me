package ru.aston.meet.mapper.avro.partipant;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.meet.mapper.user.PartipantEventType;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

/**
 * Класс-маппер, который преобразует объекты {@link User} в объекты {@link ParticipantAvro}.
 */
@Component
public class ParticipantAvroMapper {

    /**
     * Преобразует {@link User} в {@link ParticipantAvro} для определенной встречи.
     *
     * @param meeting    встреча, к которой относится участник
     * @param participant участник, которого нужно преобразовать
     * @param eventType  тип события участника
     * @return преобразованный объект {@link ParticipantAvro}
     */
    public ParticipantAvro mapToParticipantAvro(Meeting meeting, User participant, PartipantEventType eventType) {
        return ParticipantAvro.newBuilder()
                .setId(participant.getId().toString())
                .setMeetingId(meeting.getId().toString())
                .build();
    }

    /**
     * Преобразует {@link User} в {@link SpecificRecordBase} на основе типа события участника.
     *
     * @param participant участник, которого нужно преобразовать
     * @param strategy    стратегия преобразования на основе типа события
     * @return преобразованный объект {@link SpecificRecordBase}
     */
    public SpecificRecordBase mapToPartipantSpecificRecordBase(User participant, PartipantEventType strategy) {
        return strategy.mapToPartipantEventSpecificRecordBase(participant);
    }
}