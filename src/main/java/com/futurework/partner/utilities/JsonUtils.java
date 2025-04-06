package com.futurework.partner.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for working with test data JSON files.
 */
public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);

    // Constants for JSON keys
    private static final String CHECK_ACCOUNT_CREATION = "checkAccountCreation";
    private static final String EMAIL = "email";

    private static JsonNode testData;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String jsonFilePath;

    // Private constructor to prevent instantiation
    private JsonUtils() {
    }

    /**
     * Initializes the JSON file path for test data loading.
     *
     * @param jsonFilePath Path to the JSON file.
     */
    public static void initializeJsonFilePath(String jsonFilePath) {
        JsonUtils.jsonFilePath = jsonFilePath;
    }

    /**
     * Loads the test data JSON from the specified file path.
     */
    public static void loadTestData() {
        if (jsonFilePath == null || jsonFilePath.isEmpty()) {
            logger.error("JSON file path is not set. Current value: {}", jsonFilePath);
            throw new IllegalStateException("JSON file path is not set. Current value: " + jsonFilePath);
        }

        File file = new File(jsonFilePath);
        if (!file.exists()) {
            logger.error("JSON file not found at path: {}", jsonFilePath);
            throw new RuntimeException("JSON file not found.");
        }

        try {
            testData = objectMapper.readTree(file);

            // Conditionally check if the path exists before accessing it
            if (testData.has(CHECK_ACCOUNT_CREATION) && testData.path(CHECK_ACCOUNT_CREATION).has(EMAIL)) {
                String email = testData.path(CHECK_ACCOUNT_CREATION).path(EMAIL).asText();
                logger.info("Reloaded email from JSON: {}", email);
            } else {
                logger.warn("Email not found in the JSON data.");
            }
        } catch (IOException e) {
            logger.error("Failed to load test data JSON file.", e);
            throw new RuntimeException("Failed to load test data JSON file: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the loaded test data as a JsonNode.
     *
     * @return JsonNode object containing test data.
     */
    public static JsonNode getTestData() {
        if (testData == null) {
            loadTestData();  // Lazy load if not already loaded
        }
        return testData;
    }

    /**
     * Updates the email value under "checkAccountCreation" and saves the file.
     *
     * @param generatedEmail New email to insert into the JSON.
     */
    public static void updateEmailInJson(String generatedEmail) {
        if (jsonFilePath == null || jsonFilePath.isEmpty()) {
            logger.error("JSON file path is not set.");
            throw new IllegalStateException("JSON file path is not set.");
        }

        File file = new File(jsonFilePath);
        if (!file.exists()) {
            logger.error("JSON file not found at path: {}", jsonFilePath);
            throw new RuntimeException("JSON file not found.");
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode accountNode = rootNode.path(CHECK_ACCOUNT_CREATION);

            if (accountNode.isObject()) {
                ((ObjectNode) accountNode).put(EMAIL, generatedEmail);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
                logger.info("Updated email in JSON: {}", generatedEmail);
            } else {
                throw new IllegalStateException("Expected 'checkAccountCreation' to be an ObjectNode.");
            }

        } catch (IOException e) {
            logger.error("Failed to update email in JSON file.", e);
            throw new RuntimeException("Failed to update email in JSON file: " + e.getMessage(), e);
        }
    }
}