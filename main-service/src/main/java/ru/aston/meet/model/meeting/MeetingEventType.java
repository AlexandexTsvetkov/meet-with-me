package ru.aston.meet.model.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import ru.aston.meet.kafka.notifications.meeting.CreateMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.DeleteMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.EditMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.InvitedUser;
import ru.aston.meet.model.user.User;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public enum MeetingEventType {
    CREATE {
        @Override
        public SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users) {

            User user = meeting.getInitiator();

            return CreateMeetingAvro.newBuilder()
                    .setDescription(meeting.getDescription())
                    .setEventDate(meeting.getEventDate().atZone(ZoneId.systemDefault())
                            .toInstant())
                    .setTitle(meeting.getTitle())
                    .setLocation(meeting.getLocation())
                    .setInitiatorName(user.getName())
                    .setInitiatorEmail(user.getEmail())
                    .build();
        }
    },
    DELETE {
        @Override
        public SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users) {
            return DeleteMeetingAvro.newBuilder()
                    .setInvited(users.stream().map(user -> InvitedUser.newBuilder().setEmail(user.getEmail()).setName(user.getName()).build()).collect(Collectors.toList()))
                    .build();
        }
    },
    EDIT {
        @Override
        public SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users) {

            return EditMeetingAvro.newBuilder()
                    .setDescription(meeting.getDescription())
                    .setEventDate(meeting.getEventDate().atZone(ZoneId.systemDefault())
                            .toInstant())
                    .setTitle(meeting.getTitle())
                    .setInvited(users.stream().map(user -> InvitedUser.newBuilder().setEmail(user.getEmail()).setName(user.getName()).build()).collect(Collectors.toList()))
                    .setLocation(meeting.getLocation())
                    .build();
        }
    };

    public abstract SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users);
}
