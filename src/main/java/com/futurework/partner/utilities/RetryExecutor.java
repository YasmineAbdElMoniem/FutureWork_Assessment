package com.futurework.partner.utilities;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryExecutor implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryExecutor.class);
    private int count = 0;
    private static final int MAX_RETRIES = Integer.parseInt(ConfigLoader.RETRY_COUNT);

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRIES && result.getThrowable() != null) {
            count++;
            String retryMessage = String.format("Retrying test '%s' due to [%s], attempt %d/%d",
                    result.getName(),
                    result.getThrowable().getClass().getSimpleName(),
                    count, MAX_RETRIES);

            Allure.addAttachment("Retry Info", retryMessage);
            logger.warn(retryMessage);

            return true;
        }
        return false;
    }
}