package com.example;

import java.io.*;

public class Main {
    // Aujourd'hui le SecurityManager est désactivé et innaccessible. 
    // Même quand il était disponible il était désactive par défaut, aujourd'hui plus personne ne l'utilise 
    // sauf sur de très vieilles applications qui n'ont pas été mises à jour. 
    public static void main(String[] args) {
        File file = new File("assets/sample.txt");
        
        try{
            if (file.exists()) {
                System.out.println("Fichier existe.");
                System.out.println("Peut lire : " + file.canRead());
                System.out.println("Peut écrire : " + file.canWrite());
                System.out.println("Peut exécuter : " + file.canExecute());
            } else {
                System.out.println("Le fichier n'existe pas.");
            }
        } catch (Exception e){
            e.printStackTrace();    
        }
        
    }
}
