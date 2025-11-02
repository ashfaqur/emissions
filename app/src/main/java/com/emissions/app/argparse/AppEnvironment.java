package com.emissions.app.argparse;

import org.springframework.stereotype.Component;

@Component
public class AppEnvironment {

    private static final String ORS_TOKEN = "ORS_TOKEN";

    /**
     * Finds the ORS key token from environment variable
     *
     * @return the key as string
     * @throws IllegalArgumentException if environment not found
     */
    public String findOrsToken() {
        String token = System.getenv(ORS_TOKEN);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("No ORS_TOKEN environment variable specified.");
        }
        return token;
    }
}
