package fr.idjenria.io;

import java.io.*;

// Fonctions diverses en rapport avec les fichiers
public class Files {
    // Fonction pour écrire des lignes dans un fichier sans garder le contenu déjà dans le fichier
    public static void writeLines(File file, String... lines){ writeLines(file, false, lines); }
    // Fonction pour écrire des lignes dans un fichier en gardant ou non le contenu déjà dans le fichier
    public static void writeLines(File file, boolean append, String... lines){
        try(FileWriter write = new FileWriter(file, append); BufferedWriter writer = new BufferedWriter(write)){
            // Répéter pour chacune des lignes
            for(String line : lines){
                // Vérifier que la ligne n'est pas vide
                if(line == null || line.equals("")) continue;

                // Écrire la ligne
                writer.write(line);
                writer.flush();
                writer.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Fonction pour lire toutes les lignes d'un fichier
    public static String[] readLines(File file){
        // Vérifier que le fichier existe
        assert file.exists();

        // Si aucune erreur ne survient, retourner les lignes du fichier sous la forme d'un tableau de strings
        try(FileReader read = new FileReader(file); BufferedReader reader = new BufferedReader(read)){
            return reader.lines().toList().toArray(new String[0]);
        }catch(IOException e){
            e.printStackTrace();
        }

        // Un tableau vide est retourné, car il a été impossible de lire les donnés du fichier
        return new String[0];
    }
}