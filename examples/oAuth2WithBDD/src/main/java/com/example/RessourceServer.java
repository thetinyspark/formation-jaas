package com.example;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RessourceServer {

    private MockOAuth2Server authServer;

    // TP 
    // Une fois le token JWT validé, récupérer le payload contenant le rôle du client
    // si le role est admin -> retourner la valeur ADMIN SECRET DATA
    // si le role est commercial -> retourner la valeur COMMERCIAL SECRET DATA
    // si le role est autre ou null -> retourner la valeur NOT ALLOWED

    public RessourceServer(){
        // ici notre serveur se connecte au serveur oAuth2 pour valider les tokens
        // qui lui sont présentés, si demain on veut changer le serveur oAuth2 responsable
        // de l'authentification, on le fait ici. 

        // Le client devra alors se mettre à jour et demander son token au même serveur oAuth2
        this.authServer = new MockOAuth2Server();
    }

    public String getProtectedData(String token) {
        if (authServer.validateToken(token)) {
            String payload = getPayload(token);
            String[] parts = payload.split(":");
            String role = parts[1].replace("\"", "");
            role = role.replace("}", "");
            if( role.equals("admin") ){
                return "ADMIN SECRET DATA";
            }
            else if( role.equals("commercial")){
                return "COMMERCIAL SECRET DATA";
            }
            else{
                return "NOT ALLOWED";
            }
        } else {
            return "Access Denied";
        }
    }

    public String base64UrlDecode(String value) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(value);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public String getPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }
        return base64UrlDecode(parts[1]);
    }
}
