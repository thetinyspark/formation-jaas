package com.example;

public class JwtClientExample {

    public static void main(String[] args) throws Exception {

        // Petit TP 
        // Créer une classe User  avec un champ username  et un champ role ainsi qu'un champ age 
        // Le but du jeu est de transformer les informations de user en claims dans le payload du JWT
        // Exemple de payload : { name: alice, role: admin, age: 30 }

        // première partie, créer un token à partir du json 

        // L'intérêt d'un token JWT est de pouvoir transmettre des informations de manière sécurisée entre un client et un serveur.
        // Le client (par exemple une application web ou mobile) peut inclure le token JWT dans les en-têtes des requêtes HTTP (généralement dans l'en-tête Authorization).
        // Le serveur peut alors valider le token pour authentifier l'utilisateur et autoriser l'accès aux ressources protégées.

        // ATTENTION : Ne mettez jamais d'informations sensibles dans un JWT sauf si vous les chiffrez avant (ex: mot de passe, numéro de carte bancaire, etc.)

        JwtServer server = new JwtServer();

        System.out.println("Client ask for jwt token...");
        String token = server.generateToken( "{ name: alice, role: admin, age: 30 }");

        System.out.println("received token :");
        System.out.println(token);

        System.out.println("Access to protected ressource...");

        if (server.validateToken(token)) {

            // ici en partie 2 on décodera notre token pour extraire les informations du user
            System.out.println("Acces granted (valid token)");

            String payload = server.getPayload(token);
            System.out.println("Payload : " + payload);

        } else {
            System.out.println("Acces refused (invalid token)");
        }
    }
}
