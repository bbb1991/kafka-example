package me.bbb1991.consumer;

import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import me.bbb1991.helper.KeyReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.Properties;

/**
 * Класс, который получает и обрабатывает сообщения
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class SimpleConsumer<K, V> implements Runnable {

    /**
     * С какой темой будем работать
     */
    private String topicName;

    /**
     * Получатель сообщения
     */
    private KafkaConsumer<K, V> consumer;

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    /**
     * Приватный ключ, с помощью которой и будем расшифровывать
     */
    private RSAPrivateKey privateKey;

    /**
     * Конструктор
     *
     * @param properties настройки, считанные с файла
     */
    public SimpleConsumer(Properties properties) throws Exception {
        consumer = new KafkaConsumer<>(properties);
        this.topicName = properties.getProperty("topic");
        logger.info("Creating new consumer...");

        String keyName = properties.getProperty("private_key");
        privateKey = (RSAPrivateKey) KeyReader.getPrivateKey(keyName);

    }

    @Override
    public void run() {
        logger.info("Staring consumer");
        consumer.subscribe(Collections.singletonList(topicName));
        ConsumerRecords<K, V> records = consumer.poll(Long.MAX_VALUE);
        for (ConsumerRecord<K, V> record : records) {
            V value = record.value();

            logger.info("Got new encrypted message: {}", value);

            String json = encrypt(value);
            doStuff(json);
        }
        consumer.close();
    }

    /**
     * Прерывание работы клиента
     */
    public void shutdown() {
        logger.info("Shutting down consumer...");
        consumer.wakeup();
    }

    /**
     * Расшифровка сообщения
     *
     * @param message пришедшее сообщения
     * @return расшифрованное сообщение в виде JSON
     */
    private String encrypt(V message) {

        logger.info("Encrypting message");

        String result = null;

        String str;

        if (message instanceof String) {
            str = (String) message;
        } else {
            str = message.toString();
        }

        try {

            EncryptedJWT jwt = EncryptedJWT.parse(str);
            RSADecrypter decrypter = new RSADecrypter(privateKey);

            jwt.decrypt(decrypter);

            result = jwt.getJWTClaimsSet().getClaim("employee").toString();


        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e);
        }

        logger.info("Encryption completed!");
        return result;
    }

    /**
     * Метод, который что то будет делать с полученным обектом. Не знаю, писать в БД или еще какое-то действие
     *
     * @param json
     */
    private void doStuff(String json) {
        logger.info("Got message is: {}", json);
    }
}
