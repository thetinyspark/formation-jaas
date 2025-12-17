package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureLoggingExample {

    private static final Logger logger = LoggerFactory.getLogger(SecureLoggingExample.class);

    public static void main(String[] args) {
        int username = 0xffaabbcc; // Example of non-sensitive data
        String action = "login";

        logger.info("Application started");
        logger.info("User {} action {}", username, action);
    }
}
