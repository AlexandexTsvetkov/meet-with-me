package ru.aston.notification.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.CreateMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.EditMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.DeleteMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.RemindMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.InvitedUser;
import ru.aston.notification.util.TemplateProcessor;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для обработки Kafka сообщений и отправки соответствующих уведомлений.
 * Слушает события встреч и приглашений, генерирует и отправляет email-уведомления.
 */
@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final NotificationService notificationService;
    private final TemplateProcessor templateProcessor;

    /**
     * Обрабатывает события встреч из Kafka.
     *
     * @param meeting событие встречи, содержащее payload с конкретным типом действия
     *               (создание, редактирование, удаление, напоминание)
     */
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

    /**
     * Обрабатывает события приглашений из Kafka.
     *
     * @param invitation событие приглашения на встречу
     */
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

    /**
     * Отправляет уведомления в зависимости от типа события встречи.
     *
     * @param meeting событие встречи с payload
     */
    private void sendMeetingMessage(MeetingAvro meeting) {
        Optional<?> optionalPayload = Optional.ofNullable(meeting.getPayload());

        optionalPayload.ifPresent(payload -> {
            optionalPayload
                    .filter(CreateMeetingAvro.class::isInstance)
                    .map(CreateMeetingAvro.class::cast)
                    .ifPresent(createPayload -> {
                        String htmlMessage = createMeetingEmailHtml(createPayload);
                        notificationService.sendEmail(createPayload.getInitiatorEmail(),
                                "Создана новая встреча: " + createPayload.getTitle(), htmlMessage);
                    });

            optionalPayload
                    .filter(EditMeetingAvro.class::isInstance)
                    .map(EditMeetingAvro.class::cast)
                    .ifPresent(editPayload -> {
                        String htmlMessage = editMeetingEmailHtml(editPayload);
                        notifyInvitedUsers(editPayload.getInvited(), editPayload.getInitiatorEmail(),
                                "Изменение встречи: " + editPayload.getTitle(), htmlMessage);
                    });

            optionalPayload
                    .filter(DeleteMeetingAvro.class::isInstance)
                    .map(DeleteMeetingAvro.class::cast)
                    .ifPresent(deletePayload -> {
                        String htmlMessage = deleteMeetingEmailHtml(deletePayload);
                        notifyInvitedUsers(deletePayload.getInvited(), deletePayload.getInitiatorEmail(),
                                "Отмена встречи: " + deletePayload.getTitle(), htmlMessage);
                    });

            optionalPayload
                    .filter(RemindMeetingAvro.class::isInstance)
                    .map(RemindMeetingAvro.class::cast)
                    .ifPresent(remindPayload -> {
                        String htmlMessage = remindMeetingEmailHtml(remindPayload);
                        notifyInvitedUsers(remindPayload.getInvited(), remindPayload.getInitiatorEmail(),
                                "Напоминание о встрече: " + remindPayload.getTitle() + " Встреча состоится через 15 минут",
                                htmlMessage);
                    });
        });
    }

    /**
     * Отправляет уведомление о приглашении на встречу.
     *
     * @param invitation событие приглашения
     */
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

    /**
     * Форматирует Instant в строку с датой и временем.
     *
     * @param instant момент времени для форматирования
     * @return отформатированная строка даты и времени
     */
    private String formatDateTime(Instant instant) {
        return DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }

    /**
     * Генерирует HTML для уведомления о напоминании встречи.
     */
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

    /**
     * Генерирует HTML для уведомления о создании встречи.
     */
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

    /**
     * Генерирует HTML для уведомления об изменении встречи.
     */
    public String editMeetingEmailHtml(EditMeetingAvro meeting) {
        return loadTemplate("edit-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription(),
                "eventDate", formatDateTime(meeting.getEventDate()),
                "location", meeting.getLocation()
        ));
    }

    /**
     * Генерирует HTML для уведомления об отмене встречи.
     */
    public String deleteMeetingEmailHtml(DeleteMeetingAvro meeting) {
        return loadTemplate("delete-meeting", Map.of(
                "title", meeting.getTitle(),
                "description", meeting.getDescription()
        ));
    }

    /**
     * Загружает и заполняет шаблон уведомления.
     *
     * @param templateName имя шаблона (без расширения)
     * @param placeholders значения для подстановки в шаблон
     * @return заполненный HTML шаблон
     * @throws RuntimeException если произошла ошибка при загрузке шаблона
     */
    private String loadTemplate(String templateName, Map<String, String> placeholders) {
        try {
            return templateProcessor.loadAndFillTemplate("templates/" + templateName + ".html", placeholders);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке шаблона " + templateName, e);
        }
    }

    /**
     * Отправляет уведомления организатору и всем приглашенным пользователям.
     *
     * @param users список приглашенных пользователей
     * @param email email организатора
     * @param subject тема письма
     * @param html HTML содержимое письма
     */
    private void notifyInvitedUsers(Iterable<InvitedUser> users, String email, String subject, String html) {
        notificationService.sendEmail(email, subject, html);
        for (InvitedUser user : users) {
            notificationService.sendEmail(user.getEmail(), subject, html);
        }
    }
}