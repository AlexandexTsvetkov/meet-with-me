package ru.aston.meet.model.meeting;

import org.apache.avro.specific.SpecificRecordBase;
import ru.aston.meet.kafka.notifications.meeting.CreateMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.DeleteMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.InvitedUser;
import ru.aston.meet.kafka.notifications.meeting.RemindMeetingAvro;
import ru.aston.meet.kafka.notifications.meeting.EditMeetingAvro;
import ru.aston.meet.model.user.User;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Перечисление типов событий встречи.
 * Предоставляет методы для преобразования данных встречи в Avro-объекты
 * для отправки уведомлений через Kafka.
 */
public enum MeetingEventType {
    /**
     * Событие создания встречи.
     * Преобразует данные встречи в Avro-объект для создания встречи.
     */
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

    /**
     * Событие удаления встречи.
     * Преобразует данные встречи в Avro-объект для удаления встречи.
     */
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

    /**
     * Событие редактирования встречи.
     * Преобразует данные встречи в Avro-объект для редактирования встречи.
     */
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

    /**
     * Событие напоминания о встрече.
     * Преобразует данные встречи в Avro-объект для напоминания о встрече.
     */
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

    /**
     * Абстрактный метод для преобразования данных встречи в соответствующий Avro-объект.
     *
     * @param meeting объект встречи, содержащий основные данные
     * @param users список пользователей, приглашенных на встречу
     * @return Avro-объект, соответствующий типу события
     */
    public abstract SpecificRecordBase mapToMeetEventSpecificRecordBase(Meeting meeting, List<User> users);
}