package ru.aston.meet.mapper.user;

import org.apache.avro.specific.SpecificRecordBase;
import ru.aston.meet.kafka.notifications.partipant.AddPartipantAvro;
import ru.aston.meet.kafka.notifications.partipant.DeletePartipantAvro;
import ru.aston.meet.model.user.User;

public enum PartipantEventType {
    ADD {
        @Override
        public SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user) {

            return AddPartipantAvro.newBuilder()
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .build();
        }
    },
    DELETE {
        @Override
        public SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user) {
            return DeletePartipantAvro.newBuilder().build();
        }
    };

    public abstract SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user);
}
