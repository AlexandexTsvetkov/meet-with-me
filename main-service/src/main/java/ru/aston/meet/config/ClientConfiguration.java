package ru.aston.meet.config;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aston.meet.util.KafkaProperties;

import java.util.Properties;

/**
 * Конфигурационный класс для настройки Kafka Producer в приложении.
 * <p>
 * Использует параметры из {@link KafkaProperties} для создания и конфигурирования
 * экземпляра {@link Producer} c ключом типа {@code String} и значением типа {@code SpecificRecordBase}.
 * Конфигурирует сериализацию ключей и значений, а также параметры клиента Kafka.
 * </p>
 *
 * <ul>
 *     <li>Аннотирован {@link Configuration}, чтобы быть компонентом Spring.</li>
 *     <li>Использует {@link EnableConfigurationProperties} для включения поддержки конфигурируемых свойств.</li>
 *     <li>Аннотация {@link RequiredArgsConstructor} позволяет внедрять зависимости через final-поля.</li>
 * </ul>
 *
 * @author (Ваше Имя)
 * @see ru.aston.meet.util.KafkaProperties
 */
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
@RequiredArgsConstructor
public class ClientConfiguration {

    /**
     * Свойства Kafka, определённые в файле конфигурации Spring.
     */
    private final KafkaProperties kafkaProperties;

    /**
     * Создаёт бин {@link Producer} для отправки сообщений в Kafka.
     *
     * <p>
     * Для настройки используется набор свойств из {@link KafkaProperties}.
     * </p>
     *
     * @return экземпляр {@link Producer}, готовый для отправки сообщений с ключом типа {@link String}
     *         и значением типа {@link SpecificRecordBase}.
     */
    @Bean
    Producer<String, SpecificRecordBase> producer() {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientIdConfig());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducerKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducerValueSerializer());

        return new KafkaProducer<>(config);
    }
}
