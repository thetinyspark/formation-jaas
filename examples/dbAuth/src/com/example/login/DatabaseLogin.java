package com.example.login;
import java.sql.*;
import com.example.user.MyUser;


// Ici nos deux applications sont deux applications qui tournent sur un même serveur. 
// Pour communiquer entre elles, on utilise un certificat qui permet une connexion 
// sécurisée entre ces deux applications via le protocole TLS (successeur de SSL)


// Dans le cas où une application hébergée sur un serveur X voudrait effectuer une 
// requête sur une base de données hébergée sur un serveur Y il existe deux cas de figure: 


// cas de figure numéro 1 , la base de données est exposée en direct sur internet (peu recommandé)
// cela signifie que l'on ouvre le port sur lequel elle écoute et qu'on peut se connecter en direct
// à cette base de données via son IP + son port à travers un certificat sécurisé + une clé publique. 


// Le deuxième cas de figure, plus courant et recommandé, est le suivant: 

// L'application hébergée sur X envoie une requête sécurisée à une autre application hébergée sur Y
// L'application hébergée sur Y est connectée à sa propre base de données locale (via une connexion sécurisée interne, comme dans notre exemple)
// Elle effectue les contrôles de sécurité adéquats (filtrage des inputs utilisateurs + CORS etc ..)
// Puis une fois la demande validée, elle effectue une requête en BDD et renvoie le résultat dans un fort compréhensible par le demandeur/client

// en règle générale, cela se passe entre deux applications web qui communiquent via un certificat qui gère le protocole HTTPS. 


// le certificat HTTPS est intégré dans la configuration du serveur HTTP qui héberge l'application qui se trouve sur le serveur Y. 

public class DatabaseLogin {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jaas_example" 
        + "?useSSL=true"
        + "&requireSSL=true"
        + "&verifyServerCertificate=true"
        + "&trustCertificateKeyStoreUrl=file:C:/mysql/certs/client-truststore.p12"
        + "&trustCertificateKeyStorePassword=changeit";

    // Handshake 

    // Demande de connexion sécurisée de la part du client 
    // Si le serveur a une disponibilité pour communiquer via un canal sécurisé, alors il accorde la connexion en envoyant un token sécurisé au client 

    // le serveur génère une paire de clé, une publique et une privée 

    // Man of the middle 

    // La clé privée sert à décrypter les messages qui sont cryptés avec la clé publique 
    // Le client possède aussi la capacité de crypter et décrypter ses échanges avc le serveur 

    // Le client doit systématiquement utiliser le token de sécurité pour communiquer avec le serveur 

    private static final String DB_USER = "root"; // Change selon ton utilisateur DB
    private static final String DB_PASSWORD = "root"; // Change selon ton mot de passe DB

    public MyUser login( String username, String password) {

        // System.setProperty("javax.net.ssl.trustStore", "client-truststore.p12");
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
            

            // String sql = "SELECT password, username FROM users WHERE username='"+username+"' AND password='"+password+"'";
            // System.out.println(sql);

            // connection.close();
            // return user;

            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
