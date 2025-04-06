package com.futurework.partner.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GlobalRetryListener implements IAnnotationTransformer {

    private static final Logger logger = LogManager.getLogger(GlobalRetryListener.class);

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        logger.info("Applying retry analyzer to test: {}", testMethod.getName());
        annotation.setRetryAnalyzer(RetryExecutor.class);
    }
}