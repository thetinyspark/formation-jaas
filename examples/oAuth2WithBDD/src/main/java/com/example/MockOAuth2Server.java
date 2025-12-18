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
    }

    /**
     * Valide le client et retourne un token.
     */
    public String getToken(String clientId, String clientSecret) {
        // c'est comme une connexion avec username + password

        DatabaseLogin db = new DatabaseLogin(); 
        MyUser user = db.login(clientId, clientSecret);

        if( !user.getIsConnected())
            return null;
        

        String role = user.getRole(); 

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
