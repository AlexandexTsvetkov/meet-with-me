package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.partipant.ParticipantAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

/**
 * Специализированный десериализатор для объектов ParticipantAvro.
 * Преобразует бинарные данные Kafka в объекты, содержащие информацию об участниках встреч.
 * Наследует базовую функциональность от {@link BaseAvroDeserializer}.
 */
public class ParticipantAvroDeserializer extends BaseAvroDeserializer<ParticipantAvro> {

    /**
     * Конструктор инициализирует десериализатор со схемой ParticipantAvro.
     * Использует статическую схему, определённую в классе ParticipantAvro.
     */
    public ParticipantAvroDeserializer() {
        super(ParticipantAvro.getClassSchema());
    }
}