package com.futurework.partner.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    // Data Provider for valid partner data
    @DataProvider(name = "validPartnerData")
    public static Object[][] validPartnerData() {
        return new Object[][]{
                {RandomDataGenerator.generateRandomPartner()}
        };
    }

    // Data Provider for invalid partner data with expected error messages
    @DataProvider(name = "invalidPartnerData")
    public static Object[][] invalidPartnerData() {
        return new Object[][]{
                {RandomDataGenerator.generatePartnerWithRegisteredEmail(), "email_already_exists"},
                {RandomDataGenerator.generatePartnerWithRegisteredPhoneNumber(), "phone_already_exists"},
                {RandomDataGenerator.generatePartnerWithInvalidEmail(), "email format is not correct"},
                {RandomDataGenerator.generatePartnerWithInvalidPhone(), "phone number is not correct"},
                {RandomDataGenerator.generatePartnerWithNullName(), "name can not be blank"},
                {RandomDataGenerator.generatePartnerWithEmptyEmail(), "email can not be blank"},
                {RandomDataGenerator.generatePartnerWithStrategicGoals(19), "strategicGoals should be of minimum 20 and maximum 1500 characters"},
                {RandomDataGenerator.generatePartnerWithStrategicGoals(1501), "strategicGoals should be of minimum 20 and maximum 1500 characters"}
        };
    }
}