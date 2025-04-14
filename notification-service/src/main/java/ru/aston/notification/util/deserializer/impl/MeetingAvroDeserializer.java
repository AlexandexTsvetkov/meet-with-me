package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

public class MeetingAvroDeserializer extends BaseAvroDeserializer<MeetingAvro> {
    public MeetingAvroDeserializer() {
        super(MeetingAvro.getClassSchema());
    }
}
