package com.futurework.partner.testdata;

import com.futurework.partner.models.PartnerRequest;
import com.github.javafaker.Faker;

public class RandomDataGenerator {

    private static final Faker faker = new Faker();

    // Helper method to generate a base PartnerRequest with random data
    private static PartnerRequest generateBasePartnerRequest() {
        return new PartnerRequest(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.number().numberBetween(500000000, 599999999),
                faker.lorem().characters(10),
                faker.lorem().characters(25) // Default valid strategic goal length
        );
    }

    // Generate a valid random PartnerRequest
    public static PartnerRequest generateRandomPartner() {
        return generateBasePartnerRequest();
    }

    // Generate a PartnerRequest with a registered email
    public static PartnerRequest generatePartnerWithRegisteredEmail() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setEmail("testmanager@test.com"); // Use a predefined registered email
        return partner;
    }

    // Generate a PartnerRequest with a registered phone number
    public static PartnerRequest generatePartnerWithRegisteredPhoneNumber() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setPhoneNumber(591097511); // Use a predefined registered phone number
        return partner;
    }

    // Generate a PartnerRequest with an invalid email format
    public static PartnerRequest generatePartnerWithInvalidEmail() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setEmail("ttt%^%%com"); // Invalid email format
        return partner;
    }

    // Generate a PartnerRequest with an invalid phone number
    public static PartnerRequest generatePartnerWithInvalidPhone() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setPhoneNumber(123456789); // Invalid phone number format
        return partner;
    }

    // Generate a PartnerRequest with null name
    public static PartnerRequest generatePartnerWithNullName() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setName(null); // Null name for invalid case
        return partner;
    }

    // Generate a PartnerRequest with empty email
    public static PartnerRequest generatePartnerWithEmptyEmail() {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setEmail(""); // Empty email for invalid case
        return partner;
    }

    // Generate a PartnerRequest with boundary values for strategicGoals
    public static PartnerRequest generatePartnerWithStrategicGoals(int length) {
        PartnerRequest partner = generateBasePartnerRequest();
        partner.setStrategicGoals(faker.lorem().characters(length)); // Set strategic goals with custom length
        return partner;
    }
}