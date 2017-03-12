package me.bbb1991.config;


import org.apache.commons.configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class PropertyReaderImpl implements PropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderImpl.class);

    private PropertiesConfiguration configuration;

    public PropertyReaderImpl(String fileName) throws IOException {

        logger.info("Trying to read property file: {}", fileName);

        try {

            configuration = new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            logger.error("Error occurred while trying to parse property file {}!", fileName);
            throw new IOException(e);
        }
        logger.info("Config file successfully parsed!");
    }

    public Configuration getPropsAsConfiguration() {
        return configuration;
    }

    public Properties getPropsAsProperties() {
        Properties properties = new Properties();
        Iterator<String> iterator = configuration.getKeys();

        while (iterator.hasNext()) {
            String key = iterator.next();
            properties.put(key, configuration.getString(key));
        }

        return properties;
    }
}
