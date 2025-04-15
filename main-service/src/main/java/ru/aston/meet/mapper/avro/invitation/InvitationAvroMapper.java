package ru.aston.meet.mapper.avro.invitation;

import org.springframework.stereotype.Component;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.model.invitation.Invitation;
import ru.aston.meet.model.meeting.Meeting;
import ru.aston.meet.model.user.User;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Класс-маппер, который преобразует объекты {@link Invitation} в объекты {@link InvitationAvro}.
 */
@Component
public class InvitationAvroMapper {

    /**
     * Преобразует {@link Invitation} в {@link InvitationAvro}.
     *
     * @param invitation приглашение, которое нужно преобразовать
     * @return преобразованный объект {@link InvitationAvro}
     */
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