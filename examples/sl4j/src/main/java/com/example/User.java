package com.example;

public class User {
    public String username = "john_doe";
    public String password = "secret123";

    public String getPassword() {
        return "password: " + password;
    }
}
