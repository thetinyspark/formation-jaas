package com.example;

/**
 * Client qui utilise le serveur OAuth2 simulé.
 */
public class OAuth2ClientExample {

    public static void main(String[] args) {

        // ici mon serveur oAuth2 peut être google authenticator, okta etc ...
        // l'url de connexion à mon serveur oAuth2 est supposée connue du client 
        // ET est supposée être la même sur le serveur 
        // ex: https://accounts.google.com/o/oauth2/token
        MockOAuth2Server authServer = new MockOAuth2Server();

        // ici mon serveur de ressource protégé peut être une API quelconque 
        // techniquement en tant que client je ne suis pas responsable du serveur  
        // oAuth2 utilisé par le serveur de ressource
        RessourceServer resourceServer = new RessourceServer();

        String clientId = "my-client";
        String clientSecret = "secret123";

        System.out.println("Client ask for a token...");
        String token = authServer.getToken(clientId, clientSecret);

        if (token != null) {
            System.out.println("Received token : " + token);
        } else {
            System.out.println("Auth failure");
        }

        System.out.println("Access to protected ressource...");
        
        String protectedData = resourceServer.getProtectedData(token);
        System.out.println(protectedData);
    }
}
