package fr.idjenria.io.toml;

import com.moandjiezana.toml.Toml;

import java.io.File;

// Classe donnant accès à la lecture des fichiers Toml
public class TomlReader {
    // Fichier de configuration du projet
    public static final File CONFIG_FILE = new File(System.getProperty("user.dir"), "config.toml");

    // Méthode permettant de récupérer à partir d'un fichier toml donné, le contenu d'une clef donnée
    public static String get(File file, String key){
        Toml toml = new Toml().read(file);

        return toml.getString(key);
    }
}