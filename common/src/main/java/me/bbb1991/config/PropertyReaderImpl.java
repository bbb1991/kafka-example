package me.bbb1991.config;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * Класс, который считывает настройки
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class PropertyReaderImpl implements PropertyReader {

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderImpl.class);

    private PropertiesConfiguration configuration;

    /**
     * Конструктор
     *
     * @param fileName файл с настройками
     * @throws IOException
     */
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

    /**
     * Получение настроек в виде {@link Configuration}. Имеет богатый функционал
     *
     * @return
     */
    public Configuration getPropsAsConfiguration() {
        return configuration;
    }

    /**
     * Получение настроек в виде стандартной {@link Properties}. Плюс в том что его потом можно скормить конструктору Кафки
     *
     * @return
     */
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
