package com.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

// RAPPEL du principe de JWT (JSON Web Token)
// Un JWT est composé de 3 parties encodées en Base64URL et séparées par des points :
// 1. Header : contient le type de token et l'algorithme de signature   
// 2. Payload : contient les claims (informations sur l'utilisateur)
// 3. Signature : permet de vérifier l'intégrité du token

// HEADER.PAYLOAD.SIGNATURE


public class JwtServer {

    // secret key
    private static final String SECRET = "mySuperSecretKey";

    // JWT generation
    public String generateToken(String username) throws Exception {

        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"sub\":\"" + username + "\"}";

        String header = base64UrlEncode(headerJson);
        String payload = base64UrlEncode(payloadJson);

        String signature = sign(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    // JWT validation 
    public boolean validateToken(String token) throws Exception {

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        String data = parts[0] + "." + parts[1];
        String expectedSignature = sign(data);

        return expectedSignature.equals(parts[2]);
    }

    // --- utilitaries ---

    private String sign(String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(key);

        byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(raw);
    }

    private String base64UrlEncode(String value) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
