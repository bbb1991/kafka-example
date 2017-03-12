package me.bbb1991.main;

import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.consumer.SimpleConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Главный класс, который запускает клиента
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Main {

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Main метод
     *
     * @param args не используется
     * @throws Exception в разных случаях
     */
    public static void main(String[] args) throws Exception {
        logger.info("Loading properties");
        PropertyReader reader = new PropertyReaderImpl("application.properties");
        Properties properties = reader.getPropsAsProperties();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        logger.info("Creating new consumer");
        SimpleConsumer<Integer, String> consumer = new SimpleConsumer<>(properties);

        logger.info("Staring consumer");
        executorService.submit(consumer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumer.shutdown();
            executorService.shutdown();
            try {
                executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}

