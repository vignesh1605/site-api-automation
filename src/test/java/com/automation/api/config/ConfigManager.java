package com.automation.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final String QA_CONFIG = "config/qa.properties";
    private ConfigManager() {
    }

    public static String baseUrl(){
        String override = System.getenv("API_BASE_URL");
        if (override != null && !override.isBlank()) {
            return override.trim();
        }
        Properties properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream(QA_CONFIG)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Missing test resource: " + QA_CONFIG);
            }

            properties.load(input);
        } catch (IOException error) {
            throw new IllegalStateException(
                    "Could not read test resource: " + QA_CONFIG, error);
        }
        String baseUrl = properties.getProperty("base.url");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Missing base.url in " + QA_CONFIG);
        }
        return baseUrl.trim();
    }

    public static String apiUsername() {
        return requiredEnvironmentVariable("API_USERNAME");
    }

    public static String apiPassword() {
        return requiredEnvironmentVariable("API_PASSWORD");
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Set the " + name + " environment variable before running auth tests");
        }

        return value;
    }
}
