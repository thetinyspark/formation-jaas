package com.example;

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
            return "Protected Resource Data";
        } else {
            return "Access Denied";
        }
    }
}
