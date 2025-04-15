package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.invitation.InvitationAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

/**
 * Десериализатор для объектов InvitationAvro.
 * Реализует преобразование бинарных данных Kafka в объекты приглашений на встречи.
 * Наследует базовую функциональность от {@link BaseAvroDeserializer}.
 */
public class InvitationAvroDeserializer extends BaseAvroDeserializer<InvitationAvro> {

    /**
     * Конструктор по умолчанию.
     * Инициализирует десериализатор со схемой InvitationAvro.
     */
    public InvitationAvroDeserializer() {
        super(InvitationAvro.getClassSchema());
    }
}