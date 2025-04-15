package ru.aston.notification.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс конфигурационных свойств для настройки подключения к Kafka.
 * Свойства загружаются из конфигурационного файла с префиксом "kafka.config".
 * Аннотации Lombok {@link Getter} и {@link Setter} генерируют геттеры и сеттеры для всех полей.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.config")
public class KafkaProperties {
    /**
     * Список bootstrap-серверов Kafka в формате "host:port[,host2:port2...]"
     */
    private String bootstrapServers;

    /**
     * Идентификатор группы потребителя (consumer group)
     */
    private String groupId;

    /**
     * Идентификатор клиента для подключения к Kafka
     */
    private String clientId;

    /**
     * Класс десериализатора для ключей сообщений
     */
    private String keyDeserializer;

    /**
     * Класс десериализатора для значений сообщений
     */
    private String valueDeserializer;

    /**
     * Таймаут между попытками потребления сообщений (в миллисекундах)
     */
    private String consumeAttemptTimeout;

    /**
     * Название топика для событий встреч
     */
    private String meetingTopic;

    /**
     * Название топика для событий приглашений
     */
    private String invitationTopic;

    /**
     * Название топика для событий участников встреч
     * (Опечатка в названии поля: должно быть 'participantTopic')
     */
    private String partipantTopic;
}