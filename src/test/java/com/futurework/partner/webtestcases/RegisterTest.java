package com.futurework.partner.webtestcases;

import com.fasterxml.jackson.databind.JsonNode;
import com.futurework.partner.pages.RegisterPage;
import com.futurework.partner.utilities.JsonUtils;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

@Epic("Authentication Module")
@Feature("User Registration")
public class RegisterTest extends TestBase {

    private static final Logger logger = LogManager.getLogger(RegisterTest.class);

    @Test(description = "Validate required fields and error messages in registration form"
            , retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Registration Form Validation")
    @Description("Verify all validation messages are shown when submitting empty or invalid data")
    public void checkValidations() {
        logger.info("Starting test: checkValidations");

        JsonNode data = JsonUtils.getTestData().get("checkValidations");

        getDriver().get("https://maas-uat.futurework.com.sa/sme-register");
        new RegisterPage(getDriver())
                .fillRegistrationForm(
                        data.get("name").asText(),
                        data.get("phone").asText(),
                        data.get("city").asText(),
                        data.get("email").asText(),
                        data.get("password").asText(),
                        data.get("confirmPassword").asText())
                .clickOnClearIcon();

        logger.info("Verifying field validation messages");
        assertEqual(new RegisterPage(getDriver()).checkNameFieldValidation(data.get("validationMessages").get("name").asText()), true, "Name field validation message is incorrect");
        assertEqual(new RegisterPage(getDriver()).checkPhoneFieldValidation(data.get("validationMessages").get("phone").asText()), true, "Phone field validation message is incorrect");
        assertEqual(new RegisterPage(getDriver()).checkCityFieldValidation(data.get("validationMessages").get("city").asText()), true, "City field validation message is incorrect");
        assertEqual(new RegisterPage(getDriver()).checkEmailFieldValidation(data.get("validationMessages").get("email").asText()), true, "Email field validation message is incorrect");
        assertEqual(new RegisterPage(getDriver()).checkPasswordFieldValidation(data.get("validationMessages").get("password").asText()), true, "Password field validation message is incorrect");
        assertEqual(new RegisterPage(getDriver()).checkConfirmPasswordFieldValidation(data.get("validationMessages").get("confirmPassword").asText()), true, "Confirm Password field validation message is incorrect");

        getSoftAssert().assertAll();
        logger.info("Test finished: checkValidations");
    }

    @Test(description = "Check if Sign In page appears when email already exists",
            retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("Existing User Registration")
    @Description("Should display appropriate error when trying to register with an existing email")
    public void checkErrorForExistingEmail() {
        logger.info("Starting test: checkSignInPageIsOpened");

        JsonNode data = JsonUtils.getTestData().get("checkSignInPageIsOpened");

        getDriver().get("https://maas-uat.futurework.com.sa/sme-register");
        boolean isEmailErrorDisplayed = new RegisterPage(getDriver())
                .fillRegistrationForm(
                        data.get("name").asText(),
                        data.get("phone").asText(),
                        data.get("city").asText(),
                        data.get("email").asText(),
                        data.get("password").asText(),
                        data.get("confirmPassword").asText())
                .clickOnSubmitButton()
                .checkValidationForExistentEmail(data.get("expectedEmailError").asText());

        assertEqual(isEmailErrorDisplayed, true, "Error message for existing email was not displayed");

        getSoftAssert().assertAll();
        logger.info("Test finished: checkSignInPageIsOpened");
    }

    @Test(description = "Create a new account with valid registration details"
            , retryAnalyzer = com.futurework.partner.utilities.RetryExecutor.class)
    @Story("New User Registration")
    @Description("Should successfully create a new user account with valid inputs")
    public void checkAccountCreation() {
        logger.info("Starting test: checkAccountCreation");

        // Generate dynamic email
        String generatedEmail = new Faker().internet().emailAddress();

        // Update JSON file with new email
        JsonUtils.updateEmailInJson(generatedEmail);
        JsonUtils.loadTestData();

        JsonNode data = JsonUtils.getTestData().get("checkAccountCreation");

        logger.info("Generated Email: {}", generatedEmail);
        logger.info("Email from JSON: {}", data.get("email").asText());

        getDriver().get("https://maas-uat.futurework.com.sa/sme-register");

        boolean isAccountCreationSuccessful = new RegisterPage(getDriver())
                .fillRegistrationForm(
                        data.get("name").asText(),
                        data.get("phone").asText(),
                        data.get("city").asText(),
                        data.get("email").asText(),
                        data.get("password").asText(),
                        data.get("confirmPassword").asText())
                .clickOnSubmitButton()
                .checkAccountCreationSuccessfully(data.get("successMessage").asText());

        assertEqual(isAccountCreationSuccessful, true, "Account creation was not successful");

        getSoftAssert().assertAll();
        logger.info("Test finished: checkAccountCreation");
    }
}