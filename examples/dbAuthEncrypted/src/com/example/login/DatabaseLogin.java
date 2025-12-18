package com.example.login;

import java.security.MessageDigest;
import java.sql.*;
import com.example.user.MyUser;

public class DatabaseLogin {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jaas_example";

    private static final String DB_USER = "root"; // Change selon ton utilisateur DB
    private static final String DB_PASSWORD = "root"; // Change selon ton mot de passe DB

    private static final String SALT = "_buibferiubeirubgeg1e89g1892g_";

    public MyUser login(String username, String password) {
        // créer un nouvel utilisateur par défaut
        MyUser user = new MyUser("", "");
        user.setIsConnected(false);

        try {
            // Enregistrer le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données MySQL
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            // Vérifier si les informations d'identification sont correctes
            String sql = "SELECT password, username FROM users WHERE username=? AND password=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, hashPassword(password));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user.setName(rs.getString("username"));
                    user.setIsConnected(true);
                } else {
                    System.out.println("Invalid credentials");
                }
            }

            // Récupérer les rôles de l'utilisateur
            // ce code est naturellement protégé des injections SQL grâce aux requêtes
            // préparées.
            sql = "SELECT role_name FROM roles WHERE username=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String role = rs.getString("role_name");
                    user.setRole(role);
                }
            }

            // libère la connexion à la base de données
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trouvé !");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error during login process: " + e.getMessage());
        }
        return user;
    }

    public boolean addUser(String username, String password, String role) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            String hashedPassword = hashPassword(password);

            // Ajouter dans la table users
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                stmt.executeUpdate();
            }

            // Ajouter le rôle
            sql = "INSERT INTO roles (username, role_name) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, role);
                stmt.executeUpdate();
            }

            connection.close();
            return true;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'ajout de l'utilisateur : " + e.getMessage());
        }

        return false;
    }

    // -----------------------------
    // Méthode de hash SHA-256
    // -----------------------------
    private String hashPassword(String password) {
        try {
            password = SALT + password;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));
            // conversion bytes -> hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erreur de hash du mot de passe", e);
        }
    }
}
