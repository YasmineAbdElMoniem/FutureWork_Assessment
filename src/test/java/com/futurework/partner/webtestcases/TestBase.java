package com.futurework.partner.webtestcases;

import com.futurework.partner.driverfactory.Driver;
import com.futurework.partner.utilities.JsonUtils;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Objects;

public class TestBase {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();

    @Step("Initialize test environment for browser: {browser}")
    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("firefox") String browser) {
        String browserUpper = browser.toUpperCase();
        WebDriver webDriver = Driver.createWebDriver(Driver.Browser.valueOf(browserUpper));
        driver.set(webDriver);
        softAssert.set(new SoftAssert());
        // Set the correct JSON file path
        String jsonFilePath = "src/test/resources/testData/User.json";  // Specify the relative path here
        JsonUtils.initializeJsonFilePath(jsonFilePath);  // Initialize the JSON path
        JsonUtils.loadTestData();
    }

    @Step("Clean up test environment")
    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            captureScreenshot("After Test: " + getCurrentTestMethodName()); // Captures screenshot after every test
            getDriver().quit();
            driver.remove();
        }
        softAssert.remove();
    }

    // Capture screenshot for every test case run
    @Attachment(value = "{attachmentName}", type = "image/png")
    public byte[] captureScreenshot(String attachmentName) {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    // Method to get current test method name
    private String getCurrentTestMethodName() {
        return org.testng.Reporter.getCurrentTestResult().getMethod().getMethodName();
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    protected SoftAssert getSoftAssert() {
        return softAssert.get();
    }

    @Step("Verify {message}")
    public void assertEqual(Object actual, Object expected, String message) {
        boolean isEqual = Objects.equals(actual, expected);
        getSoftAssert().assertEquals(actual, expected, message);

        // Always take screenshot regardless of result
        if (!isEqual) {
            captureScreenshot("Assertion Failure: " + message);
        } else {
            captureScreenshot("Assertion Success: " + message);
        }
    }
}