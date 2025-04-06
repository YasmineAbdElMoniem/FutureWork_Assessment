package com.futurework.partner.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private static final Logger logger = LogManager.getLogger(ErrorResponse.class);
    private String message;

    public ErrorResponse() {
        logger.debug("ErrorResponse object instantiated");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        logger.debug("Setting error message: {}", message);
        this.message = message;
    }
}