package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureLoggingExample {

    private static final Logger logger = LoggerFactory.getLogger(SecureLoggingExample.class);

    public static void main(String[] args) {

        String username = "myUserName";
        String action = "myAction";
        String pwd = "secret123";
        logger.info("Login user={} action={} password={}", username, action, pwd);
    }
}
