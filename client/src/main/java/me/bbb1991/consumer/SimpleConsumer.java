package me.bbb1991.consumer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import me.bbb1991.helper.KeyReader;
import me.bbb1991.model.Employee;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class SimpleConsumer<K, V> implements Runnable {

    private String topicName;

    private KafkaConsumer<K, V> consumer;


    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public SimpleConsumer(Properties properties) {
        consumer = new KafkaConsumer<>(properties);
        this.topicName = properties.getProperty("topic");
        logger.info("Creating new consumer");
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList(topicName));
        ConsumerRecords<K, V> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<K, V> record : records) {
            V s = record.value();

            String str;

            if (s instanceof String) {
                str = (String) s;
            } else {
                str = s.toString();
            }

            try {
                RSAPrivateKey privateKey = (RSAPrivateKey) KeyReader.getPrivateKey("private.der");

                EncryptedJWT jwt = EncryptedJWT.parse(str);
                RSADecrypter decrypter = new RSADecrypter(privateKey);

                jwt.decrypt(decrypter);

                System.out.println(jwt.getJWTClaimsSet().getClaim("employee"));


            } catch (Exception e) {
                logger.error("Error: ", e);
            }
        }
        consumer.close();
    }

    public void shutdown() {
        logger.info("Shutting down consumer...");
        consumer.wakeup();
    }
}
