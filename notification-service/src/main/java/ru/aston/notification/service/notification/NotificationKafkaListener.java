package ru.aston.notification.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.kafka.notifications.meeting.CreateMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.DeleteMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.EditMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.InvitedUser;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.RemindMeetingAvro;
import ru.aston.notification.util.TemplateProcessor;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final NotificationService notificationService;
    private final TemplateProcessor templateProcessor;

    @KafkaListener(
            topics = "${kafka.config.meeting-topic}",
            groupId = "${kafka.config.group-id}",
            properties = {
                    "value.deserializer=ru.aston.notification.util.deserializer.impl.MeetingAvroDeserializer"
            }
    )
    public void listenMeetingEvents(MeetingAvro meeting) {
        sendMeetingMessage(meeting);
    }

    @KafkaListener(
            topics = "${kafka.config.invitation-topic}",
            groupId = "${kafka.config.group-id}",
            properties = {
                    "value.deserializer=ru.aston.notification.util.deserializer.impl.InvitationAvroDeserializer"
            }
    )
    public void listenInvitationEvents(InvitationAvro invitation) {
        sendInvitationMessage(invitation);
    }

    private void sendMeetingMessage(MeetingAvro meeting) {
        Object payload = meeting.getPayload();
        if (payload instanceof CreateMeetingAvro) {
            CreateMeetingAvro createPayload = (CreateMeetingAvro) payload;
            String htmlMessage = createMeetingEmailHtml(createPayload);
            notificationService.sendEmail(createPayload.getInitiatorEmail(), "Создана новая встреча: " + createPayload.getTitle(), htmlMessage);
        } else if (payload instanceof EditMeetingAvro) {
            EditMeetingAvro editPayload = (EditMeetingAvro) payload;
            String htmlMessage = editMeetingEmailHtml(editPayload);
            notifyInvitedUsers(editPayload.getInvited(), "Изменение встречи: " + editPayload.getTitle(), htmlMessage);
        } else if (payload instanceof DeleteMeetingAvro) {
            DeleteMeetingAvro deletePayload = (DeleteMeetingAvro) payload;
            String htmlMessage = deleteMeetingEmailHtml(deletePayload);
            notifyInvitedUsers(deletePayload.getInvited(), "Отмена встречи: " + deletePayload.getTitle(), htmlMessage);
        } else if (payload instanceof RemindMeetingAvro) {
            RemindMeetingAvro remindPayload = (RemindMeetingAvro) payload;
            String htmlMessage = remindMeetingEmailHtml(remindPayload);
            notifyInvitedUsers(remindPayload.getInvited(), "Напоминание о встречи: " + remindPayload.getTitle() + " Встреча состоится Через 15 минут", htmlMessage);
        }
    }

    private void sendInvitationMessage(InvitationAvro invitation) {
        String htmlMessage = loadTemplate("invitation", Map.of(
                "title", invitation.getTitle(),
                "description", invitation.getDescription(),
                "eventDate", formatDateTime(invitation.getEventDate()),
                "location", invitation.getLocation(),
                "initiatorName", invitation.getInitiatorName()
        ));

        notificationService.sendEmail(
                invitation.getInvitedEmail(),
                "Вас пригласили на встречу: " + invitation.getTitle(),
                htmlMessage
        );
    }

    private String formatDateTime(Instant instant) {
        return DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

    private String remindMeetingEmailHtml(RemindMeetingAvro meeting) {
        return loadTemplate("remind-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription(),
                "eventDate", formatDateTime(meeting.getEventDate()),
                "location", meeting.getLocation(),
                "initiatorName", meeting.getInitiatorName(),
                "initiatorEmail", meeting.getInitiatorEmail()
        ));
    }

    public String createMeetingEmailHtml(CreateMeetingAvro meeting) {
        return loadTemplate("create-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription(),
                "eventDate", formatDateTime(meeting.getEventDate()),
                "location", meeting.getLocation(),
                "initiatorName", meeting.getInitiatorName(),
                "initiatorEmail", meeting.getInitiatorEmail()
        ));
    }

    public String editMeetingEmailHtml(EditMeetingAvro meeting) {
        return loadTemplate("edit-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription(),
                "eventDate", formatDateTime(meeting.getEventDate()),
                "location", meeting.getLocation()
        ));
    }

    public String deleteMeetingEmailHtml(DeleteMeetingAvro meeting) {
        return loadTemplate("delete-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription()
        ));
    }

    private String loadTemplate(String templateName, Map<String, String> placeholders) {
        try {
            return templateProcessor.loadAndFillTemplate("templates/" + templateName + ".html", placeholders);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке шаблона " + templateName, e);
        }
    }

    private void notifyInvitedUsers(Iterable<InvitedUser> users, String subject, String html) {
        for (InvitedUser user : users) {
            notificationService.sendEmail(user.getEmail(), subject, html);
        }
    }
}
