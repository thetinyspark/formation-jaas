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
        if( !sanitizedData.equals(data)){
            String ip = request.getRemoteAddr();
            SECURITY_LOG.info("event=ACCESS data={} ip={} endpoint=/secured XSS blocked", data, ip);
        }

        // String response = String.format("{\"message\":\"%s\"}", sanitizedData);
        String response = sanitizedData;
        return ResponseEntity.ok(response);
    }

    // Endpoint non sécurisé contre XSS
    @GetMapping("/notsecured")
    public ResponseEntity<String> notsecured(@RequestParam(defaultValue = "") String data) {
        String response = String.format("{\"message\":\"%s\"}", data);
        return ResponseEntity.ok(response);
    }
}
