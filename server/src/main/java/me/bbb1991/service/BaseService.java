package me.bbb1991.service;

import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.producer.SimpleProducer;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BaseService {

    private SimpleProducer producer;

    public BaseService() throws IOException {
        PropertyReader reader = new PropertyReaderImpl("application.properties");
        producer = new SimpleProducer(reader.getPropsAsProperties());
    }

    public void sendObjectToCunsumers(Object o) {
        producer.produce(o.toString());
    }

}
