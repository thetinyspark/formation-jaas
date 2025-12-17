package com.example;
import java.util.Scanner;

public class Example1 {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        String secretPwd = "aerfz";

        // 26 charactères alphabétiques possibles minuscle 
        // 26 charactères alphabétiques possibles majuscule  
        // 10 chiffres 
        // donc 62 possibilités par caractère 

        // meilleure façon de se prémunir d'une attaque par bruteforce: 

        // Autoriser un nombre de caractère suffisamment grand (ici dans notre exemple on en a 62)
        // Faire en sorte de limiter le nombre de tentatives par utilisateur (par ex: 5 tentatives max)
        // Demander un minimum de caractères (ex: au moins 8 caractères) 
        // la limite basse de 8 caractères finira par augmenter avec l'avènement de l'informatique quantique.


        String userInput = "";

        int i = 0;
        int char1Index = 0; 
        int char2Index = 0; 
        int char3Index = 0; 
        int char4Index = 0; 
        int char5Index = 0; 
        int max = 62*62*62*62*62;

        String char1 = "";
        String char2 = "";
        String char3 = "";
        String char4 = "";
        String char5 = "";

        for( i = 0; i < max; i++) {

            char1Index = i % 62;
            char2Index = (i / 62) % 62;
            char3Index = (i / (62*62)) % 62;
            char4Index = (i / (62*62*62)) % 62;
            char5Index = (i / (62*62*62*62)) % 62;

            char1 = generateCharacter(char1Index);
            char2 = generateCharacter(char2Index);
            char3 = generateCharacter(char3Index);
            char4 = generateCharacter(char4Index);
            char5 = generateCharacter(char5Index);

            userInput = char1; 
            if( i > 62) userInput += char2;
            if( i > 62*62) userInput += char3;
            if( i > 62*62*62) userInput += char4;
            if( i > 62*62*62*62) userInput += char5;

            if (userInput.equals(secretPwd)) {
                System.out.println("Authentification réussie !");
                break;
            } else {
                // System.out.println("Mot de passe incorrect.");
            }
        }


        
        // System.out.print("Entrez votre mot de passe: ");
        // String userInput = scanner.nextLine();
        
      

        // scanner.close();
    }

    public static String generateCharacter(int index) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char character = characters.charAt(index);
        return character + "";
    }
}