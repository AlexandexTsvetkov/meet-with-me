package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

public class ParticipantAvroDeserializer extends BaseAvroDeserializer<ParticipantAvro> {
    public ParticipantAvroDeserializer() {
        super(ParticipantAvro.getClassSchema());
    }
}
