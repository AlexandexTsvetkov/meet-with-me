package ru.aston.meet.model.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import ru.aston.meet.kafka.notifications.meeting.*;
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

            User initiator = meeting.getInitiator();

            return DeleteMeetingAvro.newBuilder()
                    .setInitiatorName(initiator.getName())
                    .setInitiatorEmail(initiator.getEmail())
                    .setInvited(users.stream().map(user -> InvitedUser.newBuilder().setEmail(user.getEmail()).setName(user.getName()).build()).collect(Collectors.toList()))
                    .build();
        }
    },
    EDIT {
        @Override
        public SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users) {

            User initiator = meeting.getInitiator();

            return RemindMeetingAvro.newBuilder()
                    .setDescription(meeting.getDescription())
                    .setEventDate(meeting.getEventDate().atZone(ZoneId.systemDefault())
                            .toInstant())
                    .setTitle(meeting.getTitle())
                    .setLocation(meeting.getLocation())
                    .setInitiatorName(initiator.getName())
                    .setInitiatorEmail(initiator.getEmail())
                    .setInvited(users.stream().map(user -> InvitedUser.newBuilder().setEmail(user.getEmail()).setName(user.getName()).build()).collect(Collectors.toList()))
                    .build();
        }
    },
    REMIND {
        @Override
        public SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users) {

            User initiator = meeting.getInitiator();

            return EditMeetingAvro.newBuilder()
                    .setDescription(meeting.getDescription())
                    .setEventDate(meeting.getEventDate().atZone(ZoneId.systemDefault())
                            .toInstant())
                    .setTitle(meeting.getTitle())
                    .setInitiatorName(initiator.getName())
                    .setInitiatorEmail(initiator.getEmail())
                    .setInvited(users.stream().map(user -> InvitedUser.newBuilder().setEmail(user.getEmail()).setName(user.getName()).build()).collect(Collectors.toList()))
                    .setLocation(meeting.getLocation())
                    .build();
        }
    };

    public abstract SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users);
}
