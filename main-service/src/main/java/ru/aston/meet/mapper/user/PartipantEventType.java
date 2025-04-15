package ru.aston.meet.mapper.user;

import org.apache.avro.specific.SpecificRecordBase;
import ru.aston.meet.kafka.notifications.partipant.AddPartipantAvro;
import ru.aston.meet.kafka.notifications.partipant.DeletePartipantAvro;
import ru.aston.meet.model.user.User;

/**
 * Перечисление, представляющее типы событий для участников.
 * Содержит методы для преобразования событий добавления и удаления участника.
 */
public enum PartipantEventType {
    /**
     * Тип события добавления участника.
     */
    ADD {
        @Override
        public SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user) {
            return AddPartipantAvro.newBuilder()
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .build();
        }
    },
    /**
     * Тип события удаления участника.
     */
    DELETE {
        @Override
        public SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user) {
            return DeletePartipantAvro.newBuilder().build();
        }
    };

    /**
     * Абстрактный метод для преобразования события участника в конкретный Avro-объект.
     *
     * @param user пользователь, связанный с событием
     * @return объект {@link SpecificRecordBase}, представляющий событие участника
     */
    public abstract SpecificRecordBase mapToPartipantEventSpecificRecordBase(User user);
}