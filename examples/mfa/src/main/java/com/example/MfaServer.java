package com.example;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Serveur MFA simplifié
 */
public class MfaServer {

    private static final SecureRandom random = new SecureRandom();

    // utilisateurs "en base"
    private final Map<String, String> users = new HashMap<>();

    // stockage temporaire des OTP
    private final Map<String, String> otpStore = new HashMap<>();

    public MfaServer() {
        // username -> password
        users.put("admin", "admin123");
    }

    /**
     * Étape 1 : authentification classique
     */
    public boolean authenticate(String username, String password) {
        return users.containsKey(username)
                && users.get(username).equals(password);
    }

    /**
     * Étape 2 : génération du code OTP
     */
    public String generateOtp(String username) {
        String otp = random.nextInt(1000000) + "";
        otpStore.put(username, otp);

        // simulation d'envoi (SMS / email)
        System.out.println("[SERVEUR] OTP sent : " + otp);

        return otp;
    }

    /**
     * Étape 3 : vérification du code OTP
     */
    public boolean verifyOtp(String username, String otp) {
        String expected = otpStore.get(username);
        boolean valid = expected != null && expected.equals(otp);

        if( valid )
            otpStore.remove(username);
        
        return valid;
    }
}
