package com.futurework.partner.driverfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;
import java.util.Arrays;

public class Driver {
    private static final long IMPLICIT_WAIT_SECONDS = 10;
    private static final Logger logger = LogManager.getLogger(Driver.class);

    public enum Browser {
        CHROME, FIREFOX, EDGE, SAFARI
    }

    static {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
        try {
            WebDriverManager.safaridriver().setup();
        } catch (Exception e) {
            logger.error("Safari driver setup failed: {}", e.getMessage(), e);
        }
    }

    /**
     * Creates a WebDriver instance for the specified browser.
     *
     * @param browser The browser type (CHROME, FIREFOX, EDGE, SAFARI).
     * @return A WebDriver instance for the selected browser.
     */
    public static WebDriver createWebDriver(Browser browser) {
        try {
            WebDriver driver = instantiateDriver(browser);
            configureDriver(driver);
            return driver;
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: {}", browser, e);
            throw new RuntimeException("Failed to initialize WebDriver for browser: " + browser, e);
        }
    }

    /**
     * Instantiates a WebDriver for the specified browser.
     *
     * @param browser The browser type.
     * @return A WebDriver instance.
     */
    private static WebDriver instantiateDriver(Browser browser) {
        return switch (browser) {
            case CHROME -> new ChromeDriver(getChromeOptions());
            case FIREFOX -> new FirefoxDriver(getFirefoxOptions());
            case EDGE -> new EdgeDriver(getEdgeOptions());
            case SAFARI -> new SafariDriver(getSafariOptions());
            default ->
                    throw new IllegalArgumentException("Unsupported browser: "+ browser + ". Supported browsers: "+ Arrays.toString(Browser.values()));
        };
    }

    /**
     * Configures the WebDriver settings such as timeouts and window size.
     *
     * @param driver The WebDriver to configure.
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.manage().window().maximize();
        logger.info("WebDriver configured for: {}", driver);
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*",
                "--start-maximized"
        );
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    private static SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setAutomaticInspection(false);
        return options;
    }
}