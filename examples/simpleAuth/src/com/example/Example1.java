package com.example;
import java.util.Scanner;

public class Example1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String secretPwd = "secret123";
        
        System.out.print("Entrez votre mot de passe: ");
        String userInput = scanner.nextLine();
        
        if (userInput.equals(secretPwd)) {
            System.out.println("Authentification réussie !");
        } else {
            System.out.println("Mot de passe incorrect.");
        }

        scanner.close();
    }
}