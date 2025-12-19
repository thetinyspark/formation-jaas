package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            // .allowedOrigins("https://example.com")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type")
            .allowCredentials(false);
    }
}
