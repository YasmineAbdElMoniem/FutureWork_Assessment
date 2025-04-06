package com.futurework.partner.apis;

import com.futurework.partner.helper.Enums;
import com.futurework.partner.models.PartnerRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.futurework.partner.apis.BaseApi.requestSpec;
import static com.futurework.partner.apis.BaseApi.setUpRequestSpecification;

public class PartnerApi {

    private static final Logger logger = LogManager.getLogger(PartnerApi.class);

    private PartnerApi() {
    }

    public static Response createPartner(PartnerRequest partner) {
        logger.info("Creating partner with name: {}", partner.getName());
        setUpRequestSpecification();

        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(partner)
                .when()
                .post(Enums.apiPath.PARTNER.getValue())
                .then()
                .extract().response();

        logger.info("Request completed with status code: {}", response.statusCode());

        if (response.statusCode() != 200) {
            logger.warn("Unexpected status code received: {}", response.statusCode());
            logger.debug("Response body: {}", response.getBody().asString());
        }

        return response;
    }
}