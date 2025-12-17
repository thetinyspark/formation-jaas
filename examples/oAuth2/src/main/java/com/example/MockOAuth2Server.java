package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Serveur OAuth2 simulé.
 */
public class MockOAuth2Server {

    // TP : 
    // Modifier le serveur oAuth2 afin qu'il utilise notre gestionnaire de token JWT 
    // vu au TP précédent. 

    // Ensuite, retourner dans le ressourceServer

    private Map<String, String> validClients = new HashMap<>();

    public MockOAuth2Server() {
        // client_id -> client_secret
        validClients.put("my-client", "secret123");
    }

    /**
     * Valide le client et retourne un token.
     */
    public String getToken(String clientId, String clientSecret) {

        // c'est comme une connexion avec username + password
        String expectedSecret = validClients.get(clientId);
        if (expectedSecret != null && expectedSecret.equals(clientSecret)) {

            // si les crédentials sont valides alors 
            // Génère un token aléatoire
            return UUID.randomUUID().toString();
        }
        return null; // Auth échouée
    }

    /**
     * Vérifie le token pour accéder à la ressource.
     */
    public boolean validateToken(String token) {
        // Ici, tout token non nul est considéré valide
        return token != null && !token.isEmpty();
    }
}
