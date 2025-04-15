package ru.aston.meet.mapper.avro.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.meeting.MeetingEventType;
import ru.aston.meet.model.user.User;

import java.time.Instant;
import java.util.List;

/**
 * Класс-маппер, который преобразует объекты {@link Meeting} в объекты {@link MeetingAvro}.
 */
@Component
public class MeetingAvroMapper {

    /**
     * Преобразует {@link Meeting} в {@link MeetingAvro}.
     *
     * @param meeting          встреча, которую нужно преобразовать
     * @param meetingEventType тип события встречи
     * @param users            список пользователей, связанных с встречей
     * @return преобразованный объект {@link MeetingAvro}
     */
    public MeetingAvro mapToMeetingAvro(Meeting meeting, MeetingEventType meetingEventType, List<User> users) {
        return MeetingAvro.newBuilder()
                .setId(meeting.getId().toString())
                .setTimestamp(Instant.now())
                .setPayload(mapToMeetingSpecificRecordBase(meeting, meetingEventType, users))
                .build();
    }

    /**
     * Преобразует {@link Meeting} в {@link SpecificRecordBase} на основе типа события встречи.
     *
     * @param meeting          встреча, которую нужно преобразовать
     * @param strategy         стратегия преобразования на основе типа события
     * @param users            список пользователей, связанных с встречей
     * @return преобразованный объект {@link SpecificRecordBase}
     */
    public SpecificRecordBase mapToMeetingSpecificRecordBase(Meeting meeting, MeetingEventType strategy, List<User> users) {
        return strategy.mapToMeetEventSpecificRecordBase(meeting, users);
    }
}