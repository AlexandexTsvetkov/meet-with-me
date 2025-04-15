package ru.aston.meet.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс для работы с настройками Kafka.
 * Содержит конфигурационные параметры для подключения и работы с Kafka.
 * Значения свойств загружаются из конфигурационного файла с префиксом "kafka.config".
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.config")
public class KafkaProperties {
    /**
     * Список серверов Kafka для начального подключения в формате "host:port"
     */
    private String bootstrapServers;

    /**
     * Идентификатор клиента для подключения к Kafka
     */
    private String clientIdConfig;

    /**
     * Класс сериализатора для ключей сообщений
     */
    private String producerKeySerializer;

    /**
     * Класс сериализатора для значений сообщений
     */
    private String producerValueSerializer;

    /**
     * Название топика для событий встреч
     */
    private String meetingTopic;

    /**
     * Название топика для приглашений на встречи
     */
    private String invitationTopic;

    /**
     * Название топика для событий участников встреч
     */
    private String partipantTopic;
}