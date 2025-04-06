package com.futurework.partner.webtestcases;

import com.fasterxml.jackson.databind.JsonNode;
import com.futurework.partner.pages.LoginPage;
import com.futurework.partner.pages.OtpModel;
import com.futurework.partner.utilities.JsonUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

@Epic("Authentication Module")
@Feature("Login Functionality")
public class LoginTest extends TestBase {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test(description = "Verify error message when incorrect password is used"
            , retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Login with Invalid Credentials")
    @Description("Should show error message for incorrect email or password")
    public void checkValidations() {
        logger.info("Starting test: checkValidations");

        JsonNode data = JsonUtils.getTestData().get("checkAccountCreation");
        String email = data.get("email").asText();

        getDriver().get("https://maas-uat.futurework.com.sa/login");

        logger.info("Attempting login with incorrect password for email: {}", email);
        boolean isErrorDisplayed = new LoginPage(getDriver())
                .login(email, "Test@12345")
                .isErrorMessageDisplayed("Incorrect email or password");

        assertEqual(isErrorDisplayed, true, "Error message should be displayed for incorrect credentials");

        getSoftAssert().assertAll();
        logger.info("Test finished: checkValidations");
    }

    @Test(description = "Verify OTP modal stays open when incorrect OTP is entered"
            , retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("OTP Validation")
    @Description("OTP modal should remain visible after entering an invalid OTP")
    public void enterInvalidOtp() {
        logger.info("Starting test: enterInvalidOtp");

        JsonNode data = JsonUtils.getTestData().get("checkAccountCreation");
        String email = data.get("email").asText();
        String password = data.get("password").asText();

        getDriver().get("https://maas-uat.futurework.com.sa/login");

        logger.info("Logging in with valid credentials to trigger OTP modal");
        boolean isOtpModalDisplayed = new LoginPage(getDriver())
                .login(email, password)
                .isOtpModalDisplayed("OTP Code");

        assertEqual(isOtpModalDisplayed, true, "OTP modal should be displayed after login");

        logger.info("Entering invalid OTP to check modal persistence");
        new OtpModel(getDriver()).enterInvalidOtp();

        boolean isModalStillDisplayed = new LoginPage(getDriver())
                .isOtpModalDisplayed("OTP Code");

        assertEqual(isModalStillDisplayed, true, "OTP modal should still be visible after entering invalid OTP");

        getSoftAssert().assertAll();
        logger.info("Test finished: enterInvalidOtp");
    }

    @Test(description = "Verify successful login with valid OTP",
            retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Successful Login")
    @Description("User should be logged in and see the dashboard after entering valid credentials and OTP")
    public void loginWithValidOtp() {
        logger.info("Starting test: login");

        JsonNode data = JsonUtils.getTestData().get("checkAccountCreation");
        String email = data.get("email").asText();
        String password = data.get("password").asText();

        getDriver().get("https://maas-uat.futurework.com.sa/login");

        logger.info("Logging in with valid credentials");
        new LoginPage(getDriver()).login(email, password);

        logger.info("Entering valid OTP");
        boolean isDashboardVisible = new OtpModel(getDriver())
                .enterValidOtp()
                .isDashboardVisible("Subscriptions");

        assertEqual(isDashboardVisible, true, "Dashboard should be visible after successful login");

        getSoftAssert().assertAll();
        logger.info("Test finished: login");
    }
}