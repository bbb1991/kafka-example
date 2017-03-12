package me.bbb1991.config;


import org.apache.commons.configuration.Configuration;

import java.util.Properties;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface PropertyReader {

    Configuration getPropsAsConfiguration();

    Properties getPropsAsProperties();
}
