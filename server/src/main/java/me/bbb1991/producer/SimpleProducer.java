package me.bbb1991.producer;

import me.bbb1991.model.Employee;
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

    private KafkaProducer<K, V> producer;
    private String topic;

    public SimpleProducer(Properties properties) {
        producer = new KafkaProducer<>(properties);
        topic = properties.getProperty("topic", "test");
    }

    public void produce(V message) {
        ProducerRecord<K, V> data = new ProducerRecord<>(topic, message);
        producer.send(data);
    }
}