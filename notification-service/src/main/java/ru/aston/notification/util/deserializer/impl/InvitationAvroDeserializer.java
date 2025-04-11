package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

public class InvitationAvroDeserializer extends BaseAvroDeserializer<InvitationAvro> {
    public InvitationAvroDeserializer() {
        super(InvitationAvro.getClassSchema());
    }
}
