package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*") //  tous les domaines sont autorisés
            .allowedMethods("GET", "POST", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type")
            .allowCredentials(false);

        registry.addMapping("/sapi/**")
            .allowedOrigins("http://securedomain.com") 
            //  liste de endpoints sécurisés qui ne prennent leur requêtes que depuis un ou plusieurs domaines de confiance
            .allowedMethods("GET", "POST", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type")
            .allowCredentials(false);
    }
}
