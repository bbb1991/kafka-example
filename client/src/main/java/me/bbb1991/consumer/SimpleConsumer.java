package me.bbb1991.consumer;

import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class SimpleConsumer implements Runnable {

    private String topicName;

    private ConsumerConnector connector;

    private KafkaConsumer<Integer, String> consumer;

    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public SimpleConsumer(Properties properties) {
        consumer = new KafkaConsumer<>(properties);
        this.topicName = properties.getProperty("topic");
        logger.info("Creating new consumer");
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList(topicName));
        ConsumerRecords<Integer, String> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.printf("Offset: %d, key: %d and value is: %s%n", record.offset(), record.key(), record.value());
        }
        consumer.close();
    }

    public void shutdown() {
        logger.info("Shutting down consumer...");
        consumer.wakeup();
    }
}
