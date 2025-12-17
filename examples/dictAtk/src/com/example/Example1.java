package com.example;
import java.util.Arrays;
import java.util.List;

public class Example1 {

    // Pour se prémunir d'une attaque par dictionnaire:
    // Utiliser des mots de passe longs et complexes (combinaison de lettres, chiffres et symboles)

    // Pour générer un mot de passe long et complexe mais facilement retrouvable en mémoire 
    // On peut utiliser un objet ou une phrase, la hacher avec un algorithme de hachage sécurisé (ex: SHA-256)
    // et ajouter des symboles au début et à la fin du mot de passe hach 
    // Ou alors on peut hacher le mot de passe haché avec un autre algorithme de hachage sécurisé (ex: MD5)
    // Et on peut recommencer plusieurs fois de suite pour augmenter la complexité du mot de passe

    
    public static void main(String[] args) {
        String correctPassword = "78c6b9a0481c995fe70a1fbd6f8c15c6bdfae54f270ac3e2f284b3e143252d52!/-"; // Mot de passe réel
        List<String> dictionary = Arrays.asList("123456", "password", "secure123", "admin");
        for (String attempt : dictionary) {
            if (attempt.equals(correctPassword)) {
                System.out.println("Mot de passe trouvé : " + attempt);
                break;
            }
        }
    }
}
