package com.example.login;
import java.sql.*;
import com.example.user.MyUser;

public class DatabaseLogin {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jaas_example";
        // + "?useSSL=true"
        // + "&requireSSL=true"
        // + "&verifyServerCertificate=true";

    private static final String DB_USER = "root"; // Change selon ton utilisateur DB
    private static final String DB_PASSWORD = "root"; // Change selon ton mot de passe DB

    public MyUser login( String username, String password) {

        // System.setProperty("javax.net.ssl.trustStore", "client-truststore.jks");
        // System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

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
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user.setName(rs.getString("username"));
                    user.setIsConnected(true);
                } else {
                    System.out.println("Invalid credentials");
                }
            }

            // Récupérer les rôles de l'utilisateur
            // ce code est naturellement protégé des injections SQL grâce aux requêtes préparées.
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
}
