package com.example;

public class JwtClientExample {

    public static void main(String[] args) throws Exception {

        // Petit TP 
        // Créer une classe User  avec un champ username  et un champ role ainsi qu'un champ age 
        // Le but du jeu est de transformer les informations de user en claims dans le payload du JWT
        // Exemple de payload : { name: alice, role: admin, age: 30 }

        JwtServer server = new JwtServer();

        System.out.println("Client ask for jwt token...");
        String token = server.generateToken("alice");

        System.out.println("received token :");
        System.out.println(token);

        System.out.println("Access to protected ressource...");

        if (server.validateToken(token)) {
            System.out.println("Acces granted (valid token)");
        } else {
            System.out.println("Acces refused (invalid token)");
        }
    }
}
