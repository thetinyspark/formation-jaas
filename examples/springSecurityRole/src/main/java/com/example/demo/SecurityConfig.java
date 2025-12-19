package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // TP ajouter un utilisateur : 
    
    // Un utilisateur avec le role COMMERCIAL qui peut consulter les pages tout public
    // les pages d'un utilisateur qui a le rôle USER et les pages d'un utilisateur qui a le role COMMERCIAL
    // Ajouter un endpoint (donc une URI prise en charge) qui affiche à l'utilisateur des données dédiées aux commerciaux

    // Ajouter le role COMMERCIAL à l'utilisateur ADMIN

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public").permitAll()
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/commercial").hasRole("COMMERCIAL")
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().denyAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        UserDetails com = User.withUsername("commercial")
                .password("{noop}passcom")
                .roles("USER", "COMMERCIAL")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN", "USER", "COMMERCIAL")
                .build();

        return new InMemoryUserDetailsManager(user, admin, com);
    }
}
