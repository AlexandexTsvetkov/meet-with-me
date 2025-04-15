package ru.aston.notification.util.deserializer;

import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

/**
 * Базовый десериализатор Avro-объектов для Kafka.
 * Преобразует бинарные данные в объекты Avro (SpecificRecordBase).
 *
 * @param <T> тип десериализуемого объекта, должен наследоваться от SpecificRecordBase
 */
public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private final DecoderFactory decoderFactory;
    private final DatumReader<T> datumReader;

    /**
     * Конструктор с указанием схемы Avro.
     * Использует стандартную фабрику декодеров.
     *
     * @param schema схема Avro для десериализации
     */
    public BaseAvroDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    /**
     * Основной конструктор с возможностью указания фабрики декодеров.
     *
     * @param decoderFactory фабрика для создания декодеров
     * @param schema схема Avro для десериализации
     */
    public BaseAvroDeserializer(DecoderFactory decoderFactory, Schema schema) {
        this.decoderFactory = decoderFactory;
        this.datumReader = new SpecificDatumReader<>(schema);
    }

    /**
     * Десериализует бинарные данные в объект Avro.
     *
     * @param topic название топика Kafka (используется в сообщениях об ошибках)
     * @param data бинарные данные для десериализации (может быть null)
     * @return десериализованный объект или null, если передан null
     * @throws SerializationException если произошла ошибка в процессе десериализации
     */
    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data != null) {
                BinaryDecoder decoder = decoderFactory.binaryDecoder(data, null);
                return datumReader.read(null, decoder);
            }
            return null;
        } catch (IOException e) {
            throw new SerializationException("Ошибка десереализации данных топика [" + topic + "]", e);
        }
    }
}