package ru.aston.notification.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.CreateMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.DeleteMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.EditMeetingAvro;
import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final NotificationService notificationService;

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

    }

    @KafkaListener(
            topics = "${kafka.config.partipant-topic}",
            groupId = "${kafka.config.group-id}",
            properties = {
                    "value.deserializer=ru.aston.notification.util.deserializer.impl.ParticipantAvroDeserializer"
            }
    )
    public void listenParticipantEvents(ParticipantAvro participant) {
    }

    private void sendMeetingMessage(MeetingAvro meeting) {
        Object payload = meeting.getPayload();
        if (payload instanceof CreateMeetingAvro) {
            CreateMeetingAvro createPayload = (CreateMeetingAvro) payload;
            String htmlMessage = createMeetingEmailHtml(createPayload);
            notificationService.sendEmail(createPayload.getInitiatorEmail(), "Создана новая встреча: " + createPayload.getTitle(), htmlMessage);
        } else if (payload instanceof EditMeetingAvro) {
            // Обработка EditMeetingAvro
            EditMeetingAvro editPayload = (EditMeetingAvro) payload;
        } else if (payload instanceof DeleteMeetingAvro) {
            // Обработка DeleteMeetingAvro
            DeleteMeetingAvro deletePayload = (DeleteMeetingAvro) payload;
        }
    }

    private String formatDateTime(Instant instant) {
        return DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

    public String createMeetingEmailHtml(CreateMeetingAvro meeting) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 10px; }
                        .details { margin: 20px 0; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #7f8c8d; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1 class="header">Новая встреча: %s</h1>
                
                        <div class="details">
                            <p><strong>Описание:</strong> %s</p>
                            <p><strong>Дата и время:</strong> %s</p>
                            <p><strong>Место проведения:</strong> %s</p>
                            <p><strong>Организатор:</strong> %s (%s)</p>
                        </div>
                
                        <div class="footer">
                            <p>Это письмо сгенерировано автоматически, пожалуйста, не отвечайте на него.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(
                meeting.getTitle(),
                meeting.getDescription(),
                formatDateTime(meeting.getEventDate()),
                meeting.getLocation(),
                meeting.getInitiatorName(),
                meeting.getInitiatorEmail()
        );
    }
}
