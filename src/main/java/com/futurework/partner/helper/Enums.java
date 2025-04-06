package com.futurework.partner.helper;

import com.futurework.partner.utilities.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enums {

    private static final Logger logger = LogManager.getLogger(Enums.class);

    public enum apiPath {
        PARTNER(ConfigLoader.BASE_URL + "/premium/our-partners");

        private final String value;

        apiPath(String value) {
            this.value = value;
            logger.debug("API Path initialized: {}", value);
        }

        public String getValue() {
            return value;
        }
    }
}