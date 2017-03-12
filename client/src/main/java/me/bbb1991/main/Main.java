package me.bbb1991.main;

import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.consumer.SimpleConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        PropertyReader reader = new PropertyReaderImpl("application.properties");
        Properties properties = reader.getPropsAsProperties();
        int consumersNumber = Integer.parseInt(properties.getProperty("consumers.count", "3"));


        ExecutorService executorService = Executors.newFixedThreadPool(consumersNumber);
        List<SimpleConsumer> consumers = new ArrayList<>();


        for (int i = 0; i < consumersNumber; i++) {
            SimpleConsumer consumer = new SimpleConsumer(properties);
            consumers.add(consumer);
            executorService.submit(consumer);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (SimpleConsumer consumer : consumers) {
                consumer.shutdown();
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}

