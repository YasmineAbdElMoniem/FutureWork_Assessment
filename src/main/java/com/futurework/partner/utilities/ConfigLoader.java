package com.futurework.partner.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);
    private static final Properties props = new Properties();

    public static final String BASE_URL;
    public static final String RETRY_COUNT;

    private ConfigLoader() {
    }

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("environment.properties")) {

            if (input == null) {
                throw new RuntimeException("environment.properties file not found.");
            }

            props.load(input);
            logger.info("Loaded environment.properties successfully.");

        } catch (Exception e) {
            logger.error("Failed to load environment.properties file", e);
            throw new RuntimeException("Failed to load environment.properties file: " + e.getMessage(), e);
        }

        BASE_URL = getProperty("base.url");
        RETRY_COUNT = getProperty("retry.count");
    }

    public static String getProperty(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.error("Missing property key: {}", key);
            throw new RuntimeException("Missing property key: " + key);
        }
        logger.debug("Loaded config [{} = {}]", key, value);
        return value;
    }
}