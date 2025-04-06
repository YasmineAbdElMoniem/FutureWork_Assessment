package com.futurework.partner.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Home (Dashboard) Page of the app.
 */
public class HomePage extends PageBase {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // ===== Locator =====
    private final By subscriptionTile = By.xpath("//h4[normalize-space()='Subscriptions']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ===== Validation Methods =====

    /**
     * Verifies that the Dashboard is visible by checking for the tile with the expected text.
     *
     * @param expectedText The text to verify on the Dashboard tile.
     * @return True if the expected text is found on the tile, otherwise false.
     */
    @Step("Verify that Dashboard is opened by checking tile with text: {0}")
    public boolean isDashboardVisible(String expectedText) {
        try {
            String actualText = getElementText(subscriptionTile);
            boolean isVisible = actualText.contains(expectedText);
            if (isVisible) {
                logger.info("Dashboard is visible with text: {}", expectedText);
            } else {
                logger.warn("Dashboard tile text does not match. Expected: {}, Found: {}", expectedText, actualText);
            }
            return isVisible;
        } catch (Exception e) {
            logger.error("Failed to verify Dashboard visibility with text: {}", expectedText, e);
            return false;
        }
    }
}