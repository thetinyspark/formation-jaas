package com.example;

import java.util.Scanner;

/**
 * Client MFA (simulation utilisateur)
 */
public class MfaClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MfaServer server = new MfaServer();

        System.out.print("Username : ");
        String username = scanner.nextLine();

        System.out.print("Password : ");
        String password = scanner.nextLine();

        // Facteur 1
        if (!server.authenticate(username, password)) {
            System.out.println("Auth failed");
            return;
        }

        System.out.println("Valid password");

        // Facteur 2
        server.generateOtp(username);

        System.out.print("Enter the OTP Code : ");
        String otp = scanner.nextLine();

        if (server.verifyOtp(username, otp)) {
            System.out.println("Access granted (MFA validated)");
        } else {
            System.out.println("Invalid OTP code");
        }

        scanner.close();
    }
}
