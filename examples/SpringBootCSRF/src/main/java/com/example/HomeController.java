package com.example;

// OWASP
import org.owasp.encoder.Encode;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class HomeController {

    private static final Logger SECURITY_LOG = LoggerFactory.getLogger("SECURITY_EVENT");

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("{\"message\":\"Hello world\"}");
    }

    // Endpoint sécurisé contre XSS
    @GetMapping("/secured")
    public ResponseEntity<String> secured(@RequestParam(defaultValue = "") String data, HttpServletRequest request) {
        // Encodage HTML pour éviter XSS
        String sanitizedData = Encode.forHtml(data);

        // Logging SIEM-ready
        String ip = request.getRemoteAddr();
        SECURITY_LOG.info("event=ACCESS data={} ip={} endpoint=/secured", data, ip);

        String response = String.format("{\"message\":\"%s\"}", sanitizedData);
        return ResponseEntity.ok(response);
    }

    // Endpoint non sécurisé contre XSS
    @GetMapping("/notsecured")
    public ResponseEntity<String> notsecured(@RequestParam(defaultValue = "") String data) {
        String response = String.format("{\"message\":\"%s\"}", data);
        return ResponseEntity.ok(response);
    }

    // ce endpoint est sensible à une attaque CRSF pour plusieurs raisons: 
    // On ne vérifie pas l'origine de la requête cela pourrait venir de n'importe quel formulaire hébergé n'importe où ailleurs, 
    // ou même d'un script javascript utilisé depuis un autre domaine

    // Pas de vérification de token CSRF ( et pour cause il n'y en a pas )

    // si l'utilisateur est connecté et a une session en cours, alors la requête est automatiquement acceptée
    @PostMapping("/changemail")
    public ResponseEntity<String> changePassword(@RequestParam String email,HttpServletRequest request) {

        // Simulation d’un utilisateur déjà authentifié avec une session active
        String user = "alice@example.com";

        // ❌ Aucune protection CSRF
        String response = String.format(
            "{\"message\":\"Email de %s changé en %s\"}",
            user,
            email
        );

        return ResponseEntity.ok(response);
    }
}
