package com.futurework.partner.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object representing the Login page.
 */
public class LoginPage extends PageBase {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ===== Locators =====
    private final By errorMessage = By.xpath("//div[contains(text(), 'Incorrect email or password')]");
    private final By otpModal = By.xpath("//span[contains(text(), ' OTP Code ')]");
    private final By emailInput = By.xpath("//input[@placeholder='Email *']");
    private final By passwordInput = By.xpath("//input[@placeholder='Password *']");
    private final By loginButton = By.xpath("(//button[contains(text(), 'Login')])[2]");
    private final By englishButton = By.xpath("//button[normalize-space()='EN']");

    // ===== Actions =====

    @Step("Click on 'EN' button to switch language to English")
    public LoginPage clickEnglishButton() {
        try {
            clickElement(englishButton);
            logger.info("Clicked on English button");
        } catch (Exception e) {
            logger.error("Failed to click on English button", e);
        }
        return this;
    }

    /**
     * Enters the given email into the email input field.
     *
     * @param email The email to be entered into the email input field.
     * @return The current instance of LoginPage to allow method chaining.
     */
    @Step("Enter email: {0}")
    public LoginPage enterEmail(String email) {
        try {
            sendText(emailInput, email);
            logger.info("Entered email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to enter email: {}", email, e);
        }
        return this;
    }

    /**
     * Enters the given password into the password input field.
     *
     * @param password The password to be entered into the password input field.
     * @return The current instance of LoginPage to allow method chaining.
     */
    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        try {
            sendText(passwordInput, password);
            logger.info("Entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password", e);
        }
        return this;
    }

    /**
     * Clicks the Login button to submit the login form.
     *
     * @return The current instance of LoginPage to allow method chaining.
     */
    @Step("Click on Login button")
    public LoginPage clickLogin() {
        try {
            clickElement(loginButton);
            logger.info("Clicked on Login button");
        } catch (Exception e) {
            logger.error("Failed to click on Login button", e);
        }
        return this;
    }

    /**
     * Performs the full login action by entering the email and password, then clicking login.
     *
     * @param email    The email for login.
     * @param password The password for login.
     * @return The current instance of LoginPage.
     */
    @Step("Perform login with email: {0}")
    public LoginPage login(String email, String password) {
        return clickEnglishButton().
                enterEmail(email)
                .enterPassword(password)
                .clickLogin();
    }

    // ===== Validation Methods =====

    @Step("Check if error message contains: {0}")
    public boolean isErrorMessageDisplayed(String expectedText) {
        try {
            boolean isDisplayed = getElementText(errorMessage).contains(expectedText);
            if (isDisplayed) {
                logger.info("Error message displayed with text: {}", expectedText);
            } else {
                logger.warn("Error message does not contain expected text. Expected: {}", expectedText);
            }
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Failed to check if error message is displayed with text: {}", expectedText, e);
            return false;
        }
    }

    @Step("Check if OTP modal is displayed with text: {0}")
    public boolean isOtpModalDisplayed(String expectedText) {
        try {
            boolean isDisplayed = getElementText(otpModal).contains(expectedText);
            if (isDisplayed) {
                logger.info("OTP modal is displayed with text: {}", expectedText);
            } else {
                logger.warn("OTP modal text does not match expected. Expected: {}", expectedText);
            }
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Failed to check if OTP modal is displayed with text: {}", expectedText, e);
            return false;
        }
    }
}