package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the shared state for Authentication Token and User ID 
 * across different Cucumber scenarios.
 */
public class TokenManager {
    private static final Logger LOGGER = LogManager.getLogger(TokenManager.class);
    private static String token;
    private static String userId;

    /**
     * Stores the generated Bearer token.
     */
    public static void setToken(String generatedToken) {
        token = generatedToken;
        LOGGER.info("Token has been updated in TokenManager.");
    }

    /**
     * Retrieves the current token. 
     * Throws an exception if the token is missing to prevent 401 errors.
     */
    public static String getToken() {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Auth Token is missing! Please ensure the Login scenario has executed successfully and saveToken() was called.");
        }
        return token;
    }

    /**
     * Stores the User ID retrieved from the login response.
     */
    public static void setUserId(String newUserId) {
        userId = newUserId;
        LOGGER.info("User ID has been updated in TokenManager: {}", newUserId);
    }

    /**
     * Retrieves the current User ID.
     */
    public static String getUserId() {
        if (userId == null || userId.isEmpty()) {
            LOGGER.warn("User ID is currently null or empty.");
        }
        return userId;
    }

    /**
     * Resets the manager. Call this in a @Before or @After hook if 
     * you want a clean state for different test runs.
     */
    public static void clear() {
        token = null;
        userId = null;
        LOGGER.info("TokenManager has been cleared.");
    }
}