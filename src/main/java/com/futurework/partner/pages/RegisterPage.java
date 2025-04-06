package com.futurework.partner.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Registration Page.
 */
public class RegisterPage extends PageBase {

    private static final Logger logger = LogManager.getLogger(RegisterPage.class);

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By englishButton = By.xpath("//button[normalize-space()='EN']");
    private final By nameTextBox = By.xpath("//input[@placeholder='Name *']");
    private final By phoneNumberTextBox = By.xpath("(//input[@placeholder='Phone Number *'])[1]");
    private final By emailTextBox = By.xpath("//input[@type='email']");
    private final By passwordTextBox = By.xpath("(//input[@placeholder='Password *'])[1]");
    private final By confirmPasswordTextBox = By.xpath("(//input[@placeholder='Confirm Password *'])[1]");
    private final By cityDropDown = By.xpath("(//ng-select[@id='city']//input)[1]");
    private final By selectSpecificCity = By.xpath("//span[contains(text(), 'Jeddah')]");
    private final By checkboxButton = By.xpath("//*[@id=\"mat-mdc-checkbox-1\"]");
    private final By submitButton = By.xpath("//div[@class='d-flex justify-content-end submit-btn']//button[contains(text(), 'Submit')]");
    private final By clearIcon = By.xpath("//span[@title='Clear all']");

    // Validation locators
    private final By nameFieldValidation = By.xpath("//span[contains(text(),'Invalid name - the name must contain 3-100 charact')]");
    private final By phoneFieldValidation = By.xpath("//span[normalize-space()='Invalid phone number format']");
    private final By cityFieldValidation = By.xpath("//span[normalize-space()='Please Choose The City']");
    private final By emailFieldValidation = By.xpath("//span[normalize-space()='Invalid Email']");
    private final By passwordFieldValidation = By.xpath("//span[contains(text(), 'The password must contain minimum 8 characters')]");
    private final By confirmPasswordFieldValidation = By.xpath("//span[normalize-space()='Confirm password mismatch']");
    private final By emailExistenceValidation = By.xpath("//div[contains(text(), 'Email Already Existing')]");
    private final By successMessage = By.xpath("//div[contains(text(), 'Account created successfully')]");

    // Actions

    @Step("Click on English button")
    public RegisterPage clickOnEngButton() {
        try {
            clickElement(englishButton);
            logger.info("Clicked on English button.");
        } catch (Exception e) {
            logger.error("Failed to click on English button.", e);
        }
        return this;
    }

    @Step("Enter name: {0}")
    public RegisterPage enterName(String name) {
        try {
            sendText(nameTextBox, name);
            logger.info("Entered name: {}", name);
        } catch (Exception e) {
            logger.error("Failed to enter name: {}", name, e);
        }
        return this;
    }

    @Step("Enter phone number: {0}")
    public RegisterPage enterPhoneNumber(String phone) {
        try {
            sendText(phoneNumberTextBox, phone);
            logger.info("Entered phone number: {}", phone);
        } catch (Exception e) {
            logger.error("Failed to enter phone number: {}", phone, e);
        }
        return this;
    }

    @Step("Select city: {0}")
    public RegisterPage selectCity(String city) {
        try {
            sendText(cityDropDown, city);
            clickElement(selectSpecificCity);
            logger.info("Selected city: {}", city);
        } catch (Exception e) {
            logger.error("Failed to select city: {}", city, e);
        }
        return this;
    }

    @Step("Enter email: {0}")
    public RegisterPage enterEmail(String email) {
        try {
            sendText(emailTextBox, email);
            logger.info("Entered email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to enter email: {}", email, e);
        }
        return this;
    }

    @Step("Enter password")
    public RegisterPage enterPassword(String password) {
        try {
            sendText(passwordTextBox, password);
            logger.info("Entered password.");
        } catch (Exception e) {
            logger.error("Failed to enter password.", e);
        }
        return this;
    }

    @Step("Enter confirm password")
    public RegisterPage enterConfirmPassword(String password) {
        try {
            sendText(confirmPasswordTextBox, password);
            logger.info("Entered confirm password.");
        } catch (Exception e) {
            logger.error("Failed to enter confirm password.", e);
        }
        return this;
    }

    @Step("Click on terms checkbox")
    public RegisterPage clickOnCheckBox() {
        try {
            clickElement(checkboxButton);
            logger.info("Clicked on terms checkbox.");
        } catch (Exception e) {
            logger.error("Failed to click on terms checkbox.", e);
        }
        return this;
    }

    @Step("Click on Submit button")
    public RegisterPage clickOnSubmitButton() {
        try {
            clickElement(submitButton);
            logger.info("Clicked on Submit button.");
        } catch (Exception e) {
            logger.error("Failed to click on Submit button.", e);
        }
        return this;
    }

    @Step("Click on Clear icon")
    public void clickOnClearIcon() {
        try {
            clickElement(clearIcon);
            logger.info("Clicked on Clear icon.");
        } catch (Exception e) {
            logger.error("Failed to click on Clear icon.", e);
        }
    }

    // Validations
    @Step("Check name field validation contains: {0}")
    public boolean checkNameFieldValidation(String text) {
        return validateField(nameFieldValidation, text);
    }

    @Step("Check phone field validation contains: {0}")
    public boolean checkPhoneFieldValidation(String text) {
        return validateField(phoneFieldValidation, text);
    }

    @Step("Check city field validation contains: {0}")
    public boolean checkCityFieldValidation(String text) {
        return validateField(cityFieldValidation, text);
    }

    @Step("Check email field validation contains: {0}")
    public boolean checkEmailFieldValidation(String text) {
        return validateField(emailFieldValidation, text);
    }

    @Step("Check password field validation contains: {0}")
    public boolean checkPasswordFieldValidation(String text) {
        return validateField(passwordFieldValidation, text);
    }

    @Step("Check confirm password field validation contains: {0}")
    public boolean checkConfirmPasswordFieldValidation(String text) {
        return validateField(confirmPasswordFieldValidation, text);
    }

    @Step("Check validation for existing email contains: {0}")
    public boolean checkValidationForExistentEmail(String text) {
        return validateField(emailExistenceValidation, text);
    }

    @Step("Check if success message contains: {0}")
    public boolean checkAccountCreationSuccessfully(String text) {
        return validateField(successMessage, text);
    }

    // Helper method for field validation
    private boolean validateField(By field, String expectedText) {
        try {
            return getElementText(field).contains(expectedText);
        } catch (Exception e) {
            logger.error("Failed to validate field: {}", field, e);
            return false;
        }
    }

    // Composite Step

    @Step("Fill registration form with data")
    public RegisterPage fillRegistrationForm(String name, String phone, String city, String email, String password, String confirmPassword) {
        return clickOnEngButton()
                .enterName(name)
                .enterPhoneNumber(phone)
                .selectCity(city)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .clickOnCheckBox();
    }
}