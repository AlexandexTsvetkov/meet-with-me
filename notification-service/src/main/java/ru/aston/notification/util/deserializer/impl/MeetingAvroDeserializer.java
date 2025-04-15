package ru.aston.notification.util.deserializer.impl;

import ru.aston.meet.kafka.notifications.meeting.MeetingAvro;
import ru.aston.notification.util.deserializer.BaseAvroDeserializer;

/**
 * Десериализатор для объектов MeetingAvro, содержащих данные о встречах.
 * Преобразует бинарные данные из Kafka в объекты MeetingAvro, используя схему Avro.
 * Наследует базовую функциональность десериализации от {@link BaseAvroDeserializer}.
 */
public class MeetingAvroDeserializer extends BaseAvroDeserializer<MeetingAvro> {

    /**
     * Создает новый экземпляр десериализатора, инициализированного
     * схемой MeetingAvro из Avro.
     * Использует статическую схему, определенную в классе MeetingAvro.
     */
    public MeetingAvroDeserializer() {
        super(MeetingAvro.getClassSchema());
    }
}