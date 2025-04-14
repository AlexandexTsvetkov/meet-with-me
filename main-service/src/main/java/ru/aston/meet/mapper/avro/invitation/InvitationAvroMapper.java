package ru.aston.meet.mapper.avro.invitation;

import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.model.invitation.Invitation;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

import java.time.Instant;
import java.time.ZoneId;

@Component
public class InvitationAvroMapper {

    public InvitationAvro mapToInvitationAvro(Invitation invitation) {

        Meeting meeting = invitation.getMeeting();
        User invited = invitation.getInvited();
        User initiator = meeting.getInitiator();

        return InvitationAvro.newBuilder()
                .setId(invitation.getId().toString())
                .setTimestamp(Instant.now())
                .setDescription(meeting.getDescription())
                .setEventDate(meeting.getEventDate().atZone(ZoneId.systemDefault()).toInstant())
                .setInvitedEmail(invited.getEmail())
                .setInvitedName(invited.getName())
                .setInitiatorName(initiator.getName())
                .setLocation(meeting.getLocation())
                .setTitle(meeting.getTitle())
                .build();
    }
}
