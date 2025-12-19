package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public access";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "User access";
    }

    @GetMapping("/commercial")
    public String commercialEndpoint() {
        return "Commercial access";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin access";
    }
}
