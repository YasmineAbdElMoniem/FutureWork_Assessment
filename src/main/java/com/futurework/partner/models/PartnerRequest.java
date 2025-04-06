package com.futurework.partner.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerRequest {

    private static final Logger logger = LogManager.getLogger(PartnerRequest.class);

    private String name;
    private String email;
    private int phoneNumber;
    private String companyName;
    private String strategicGoals;

    public PartnerRequest(String name, String email, int phoneNumber, String companyName, String strategicGoals) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.strategicGoals = strategicGoals;

        logger.info("PartnerRequest created for: {}", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        logger.debug("Setting name: {}", name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        logger.debug("Setting email: {}", email);
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        logger.debug("Setting phone number: {}", phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        logger.debug("Setting company name: {}", companyName);
        this.companyName = companyName;
    }

    public String getStrategicGoals() {
        return strategicGoals;
    }

    public void setStrategicGoals(String strategicGoals) {
        logger.debug("Setting strategic goals: {}", strategicGoals);
        this.strategicGoals = strategicGoals;
    }
}