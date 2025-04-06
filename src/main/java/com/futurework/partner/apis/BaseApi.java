package com.futurework.partner.apis;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseApi {

    protected static RequestSpecification requestSpec;
    private static final Logger logger = LogManager.getLogger(BaseApi.class);

    private BaseApi() {
    }

    public static void setUpRequestSpecification() {
        logger.info("Setting up request specification with content-type header.");
        requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
}