package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenManager {
    private static final Logger LOGGER = LogManager.getLogger(TokenManager.class);
    private static String token;  // Original Login Token
    private static String token1; // Forgot Password / Confirm Email Token
    private static String userId;
    private static String dynamicPassword;

    public static void setToken(String generatedToken) {
        token = generatedToken;
        LOGGER.info("Login Token updated.");
    }

    public static String getToken() {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Login Token is missing!");
        }
        return token;
    }

    // --- New Methods for token1 ---
    public static void setToken1(String generatedToken) {
        token1 = generatedToken;
        LOGGER.info("Token1 (Forgot Password) updated.");
    }

    public static String getToken1() {
        if (token1 == null || token1.isEmpty()) {
            throw new RuntimeException("Token1 is missing! Run Confirm Email scenario first.");
        }
        return token1;
    }

    public static void setDynamicPassword(String pass) {
        dynamicPassword = pass;
    }

    public static String getDynamicPassword() {
        return dynamicPassword;
    }

    public static void clear() {
        token = null;
        token1 = null;
        userId = null;
        dynamicPassword = null;
        LOGGER.info("TokenManager cleared.");
    }
    
    public static void setUserId(String newUserId) {
        userId = newUserId;
        LOGGER.info("User ID has been updated in TokenManager: {}", newUserId);
    }
    
    public static String getUserId() {
        if (userId == null || userId.isEmpty()) {
            LOGGER.warn("User ID is currently null or empty.");
        }
        return userId;
    }
    
    public class TokenManager1 {
        private static String token;
        private static String userId;

        // Set token (from your branch)

        public static void setToken(String generatedToken) {
    		token = generatedToken;
    	}
        // Get token (optionally provide default/test token if none is set)
        public static String getToken() {
            if (token == null || token.isEmpty()) {
                // Optional: default token from team5 for testing
               // token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtNUBnbWFpbC5jb20iLCJpYXQiOjE3NjgzOTkyODYsImV4cCI6MTc2ODQyODA4Nn0.uNz099lY0MWRkorogrtwWwucp9WzC_D84Vgnjh4a-SLxiDzgE425KohuPVWo5cwxYC7AH224CNQ2UCDNDtC6Kw";
            	token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaHVydXZhbGFrMDFAZ21haWwuY29tIiwiaWF0IjoxNzY4NTczNTg3LCJleHAiOjE3Njg2MDIzODd9.K_lkyyFK_f0X7QcMBq5rksFgLnUKhpTnr4BkO85T85PRe5h3K7ZusFI8VarjIyIv19m46XyLPCIWf7MAaAIc9g";
            }
            return token;
        }

        public static void setUserId(String newUserId) {
            userId = newUserId;
        }

        public static String getUserId() {
            return userId;
        }
        public static void clearToken() {
            token = null;
            userId = null;
        }
    }
   













}