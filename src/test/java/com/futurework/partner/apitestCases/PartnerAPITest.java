package com.futurework.partner.apitestCases;

import com.futurework.partner.apis.PartnerApi;
import com.futurework.partner.models.ErrorResponse;
import com.futurework.partner.models.PartnerRequest;
import com.futurework.partner.testdata.TestDataProvider;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Partner API")
@Feature("Create Partner Endpoint")
public class PartnerAPITest {

    private static final Logger logger = LogManager.getLogger(PartnerAPITest.class);
    private static final int STATUS_OK = 200;
    private static final int STATUS_BAD_REQUEST = 400;
    private static final long MAX_RESPONSE_TIME_MS = 2000L;

    @Test(dataProvider = "validPartnerData", dataProviderClass = TestDataProvider.class
            , retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Successful Partner Creation")
    @Description("Test to ensure partner is created successfully with valid data")
    public void createPartner_withValidRequest_shouldReturnSuccess(PartnerRequest validPartner) {
        logger.info("Running test for valid partner creation.");
        Response response = PartnerApi.createPartner(validPartner);
        validateSuccessResponse(response);
    }

    @Test(dataProvider = "invalidPartnerData", dataProviderClass = TestDataProvider.class,
            retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Failed Partner Creation")
    @Description("Test to verify proper error is returned when partner request has invalid data")
    public void createPartner_withInvalidRequest_shouldReturnExpectedError(
            PartnerRequest invalidPartner, String expectedErrorMessage) {
        logger.info("Running test for invalid partner creation.");
        Response response = PartnerApi.createPartner(invalidPartner);
        validateErrorResponse(response, expectedErrorMessage);
    }

    @Step("Validate 200 OK response and response time")
    private void validateSuccessResponse(Response response) {
        logger.info("Validating success response with status {}", response.statusCode());
        assertThat("Expected status code 200", response.statusCode(), equalTo(STATUS_OK));
        assertThat("Response time should be under 2000ms", response.time(), lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Step("Validate 400 Bad Request with expected error message")
    private void validateErrorResponse(Response response, String expectedMessage) {
        logger.info("Validating error response with status {}", response.statusCode());
        assertThat("Expected status code 400", response.statusCode(), equalTo(STATUS_BAD_REQUEST));
        ErrorResponse error = response.body().as(ErrorResponse.class);
        assertThat("Expected error message to match", error.getMessage(), containsString(expectedMessage));
    }
}