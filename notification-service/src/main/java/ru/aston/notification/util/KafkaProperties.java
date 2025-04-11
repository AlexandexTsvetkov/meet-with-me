package ru.aston.notification.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.config")
public class KafkaProperties {
    private String bootstrapServers;
    private String groupId;
    private String clientId;
    private String keyDeserializer;
    private String valueDeserializer;
    private String consumeAttemptTimeout;
    private String meetingTopic;
    private String invitationTopic;
    private String partipantTopic;
}

