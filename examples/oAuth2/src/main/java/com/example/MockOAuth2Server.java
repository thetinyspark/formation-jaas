package com.example;

import java.util.HashMap;
import java.util.Map;
/**
 * Serveur OAuth2 simulé.
 */
public class MockOAuth2Server {

    // TP : 
    // Modifier le serveur oAuth2 afin qu'il utilise notre gestionnaire de token JWT 
    // vu au TP précédent. 

    // Ensuite, retourner dans le ressourceServer

    private Map<String, String> validClients = new HashMap<>();
    private Map<String, String> roleClients = new HashMap<>();
    private JwtServer server = new JwtServer();

    public MockOAuth2Server() {
        // client_id -> client_secret
        validClients.put("user1", "titi");
        validClients.put("user2", "toto");
        validClients.put("user3", "tata");

        roleClients.put("user1", "admin");
        roleClients.put("user2", "commercial");
        roleClients.put("user3", "peon");
    }

    /**
     * Valide le client et retourne un token.
     */
    public String getToken(String clientId, String clientSecret) {
        // c'est comme une connexion avec username + password
        String expectedSecret = validClients.get(clientId);
        if (expectedSecret != null && expectedSecret.equals(clientSecret)) {

            String role = roleClients.get(clientId); 

            // si les crédentials sont valides alors 
            // Génère un token JWT et on lui fait porter 
            // en payload le role du client
            try{
                return this.server.generateToken(role);
            }
            catch( Exception e){
                return null;
            }
        }
        return null; // Auth échouée
    }

    /**
     * Vérifie le token pour accéder à la ressource.
     */
    public boolean validateToken(String token) {
        // Ici, tout token non nul est considéré valide
        try{
            return this.server.validateToken(token);
        }
        catch( Exception e)
        {
            return false;
        }
    }
}
