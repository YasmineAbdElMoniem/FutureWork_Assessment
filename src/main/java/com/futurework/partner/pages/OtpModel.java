package com.futurework.partner.pages;

import com.futurework.partner.apis.OtpExtractor;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the OTP Modal that appears after login.
 */
public class OtpModel extends PageBase {

    private static final Logger logger = LogManager.getLogger(OtpModel.class);

    public OtpModel(WebDriver driver) {
        super(driver);
    }

    // ===== Locators =====
    private final By otpInput1 = By.xpath("//app-otp-view/div/form/div/div[1]/input");
    private final By otpInput2 = By.xpath("//app-otp-view/div/form/div/div[2]/input");
    private final By otpInput3 = By.xpath("//app-otp-view/div/form/div/div[3]/input");
    private final By otpInput4 = By.xpath("//app-otp-view/div/form/div/div[4]/input");
    private final By verifyButton = By.id("submit-login-otp");

    // ===== Actions =====

    @Step("Enter invalid OTP and click verify")
    public void enterInvalidOtp() {
        try {
            enterOtpDigits("0000");
            clickElement(verifyButton);
            logger.info("Entered invalid OTP and clicked verify.");
        } catch (Exception e) {
            logger.error("Failed to enter invalid OTP and click verify.", e);
        }
    }

    @Step("Enter valid OTP retrieved from API and click verify")
    public HomePage enterValidOtp() {
        try {
            Response response = OtpExtractor.login();
            String otp = OtpExtractor.getOtp(response);

            if (otp != null && otp.length() == 4) {
                enterOtpDigits(otp);
                clickElement(verifyButton);
                logger.info("Entered valid OTP: {} and clicked verify.", otp);
            } else {
                logger.error("Invalid OTP format received: {}", otp);
            }
        } catch (Exception e) {
            logger.error("Failed to enter valid OTP and click verify.", e);
        }
        return new HomePage(driver);
    }

    @Step("Enter OTP digits: {0}")
    private void enterOtpDigits(String otp) {
        try {
            sendText(otpInput1, String.valueOf(otp.charAt(0)));
            sendText(otpInput2, String.valueOf(otp.charAt(1)));
            sendText(otpInput3, String.valueOf(otp.charAt(2)));
            sendText(otpInput4, String.valueOf(otp.charAt(3)));
            logger.info("Entered OTP digits: {}", otp);
        } catch (Exception e) {
            logger.error("Failed to enter OTP digits: {}", otp, e);
        }
    }
}