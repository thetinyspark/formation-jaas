
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Auth simplifiée pour l’exemple
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/home").permitAll()
                .anyRequest().permitAll()
            )
            // CSRF activé avec token stocké en cookie (mais on aurait pu le stocker en BDD)
            // permet au frontend de lire le token en question et l'oblige à renvoyer le token via : header X-XSRF-TOKEN (ou paramètre _csrf)
            // comme les cookies sont protégés et ne peuvent être capturés depuis un autre domaine, l'attaquant ne peut pas le deviner et la requête échouera
            .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            // Auth basique pour tester facilement
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}