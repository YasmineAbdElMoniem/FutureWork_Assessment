package com.futurework.partner.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all page objects. Provides common Selenium interactions.
 */
public class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(PageBase.class);

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Waits for an element to be clickable and visible.
     *
     * @param element The By locator of the element.
     */
    @Step("Wait for element to be clickable and visible: {0}")
    public void waitForElementToBeClickable(By element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            logger.debug("Element is clickable and visible: {}", element);
        } catch (Exception e) {
            logger.error("Failed to wait for element to be clickable and visible: {}", element, e);
            throw e;
        }
    }

    /**
     * Waits for an element to be visible.
     *
     * @param element The By locator of the element.
     */
    @Step("Wait for element to be visible: {0}")
    protected void waitForElementVisible(By element) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            logger.debug("Element is visible: {}", element);
        } catch (Exception e) {
            logger.error("Failed to wait for element to be visible: {}", element, e);
            throw e;
        }
    }

    /**
     * Clicks on the given element after waiting for it to be clickable.
     *
     * @param element The By locator of the element.
     */
    @Step("Click on element: {0}")
    protected void clickElement(By element) {
        try {
            waitForElementToBeClickable(element);
            driver.findElement(element).click();
            logger.info("Clicked on element: {}", element);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", element, e);
            throw e;
        }
    }

    /**
     * Sends text to the given element after waiting for it to be clickable.
     *
     * @param element The By locator of the element.
     * @param text    The text to be entered into the element.
     */
    @Step("Send text: {1} to element: {0}")
    protected void sendText(By element, String text) {
        try {
            waitForElementToBeClickable(element);
            WebElement input = driver.findElement(element);
            input.click();
            input.clear();
            input.sendKeys(text);
            logger.info("Sent text: {} to element: {}", text, element);
        } catch (Exception e) {
            logger.error("Failed to send text to element: {}", element, e);
            throw e;
        }
    }

    /**
     * Retrieves text from the given element.
     *
     * @param element The By locator of the element.
     * @return The text from the element.
     */
    @Step("Get text from element: {0}")
    protected String getElementText(By element) {
        try {
            waitForElementVisible(element);
            String text = driver.findElement(element).getText();
            logger.info("Retrieved text from element: {} - Text: {}", element, text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to retrieve text from element: {}", element, e);
            throw e;
        }
    }
}