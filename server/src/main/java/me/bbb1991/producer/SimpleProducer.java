package me.bbb1991.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class SimpleProducer<K, V> {

    /**
     * Отправитель сообщения
     */
    private KafkaProducer<K, V> producer;

    /**
     * Тема, куда будет отпавляться сообщения
     */
    private String topic;

    /**
     * Конструктор
     *
     * @param properties
     */
    public SimpleProducer(Properties properties) {
        producer = new KafkaProducer<>(properties);
        topic = properties.getProperty("topic", "test");
    }

    /**
     * Отправка сообщения
     *
     * @param message
     */
    public void produce(V message) {
        ProducerRecord<K, V> data = new ProducerRecord<>(topic, message);
        producer.send(data);
    }
}