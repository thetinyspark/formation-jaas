package com.example;

public class MyUser {
    private String name;
    private String role;
    private boolean isConnected = false;

    public MyUser(String name, String role) {
        this.name = name;
        this.role = role;
        this.isConnected = false;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
