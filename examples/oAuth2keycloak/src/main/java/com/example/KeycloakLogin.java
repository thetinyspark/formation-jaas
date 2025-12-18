package com.example;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KeycloakLogin {

    public static void main(String[] args) throws Exception {

        String keycloakUrl = "http://localhost:8080";
        String realm = "jaas";
        String clientId = "my-client";

        String username = "admin";
        String password = "admin123";

        String form = "grant_type=password"
                + "&client_id=" + encode(clientId)
                + "&username=" + encode(username)
                + "&password=" + encode(password)
                + "&scope=openid";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP " + response.statusCode());
        String body = response.body();

        String accessToken = extractAccessToken(body);
        System.out.println(accessToken);

        decodeJwt(accessToken);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static String extractAccessToken(String json) {

        if (json == null || !json.contains("\"access_token\"")) {
            throw new IllegalArgumentException("access_token non trouvé");
        }

        return json
                .split("\"access_token\"\\s*:\\s*\"")[1]
                .split("\"")[0];
    }

    private static void decodeJwt(String jwt) {

        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("JWT invalide");
        }

        String headerJson = decodeBase64Url(parts[0]);
        String payloadJson = decodeBase64Url(parts[1]);

        System.out.println("JWT HEADER:");
        System.out.println(headerJson);

        System.out.println("\nJWT PAYLOAD:");
        System.out.println(payloadJson);
    }

    private static String decodeBase64Url(String value) {
        return new String(
                Base64.getUrlDecoder().decode(value),
                StandardCharsets.UTF_8);
    }

}
