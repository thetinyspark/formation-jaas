package com.example;
import java.util.Scanner;
import com.example.login.DatabaseLogin;
import com.example.user.MyUser;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Entrez votre nom d'utilisateur: ");
        String username = scanner.nextLine();

        System.out.print("Entrez votre mot de passe: ");
        String userpwd = scanner.nextLine();

        DatabaseLogin dbLogin = new DatabaseLogin();
        MyUser user = dbLogin.login(username, userpwd);

        if( user.getIsConnected() ) {
            System.out.println("Authentification reussie pour l'utilisateur : " + user.getName());
            System.out.println("Role attribue : " + String.join(", ", user.getRole()));
        } else {
            System.out.println("Echec de l'authentification.");
        }

        scanner.close();
    }
}
