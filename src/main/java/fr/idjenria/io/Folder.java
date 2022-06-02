package fr.idjenria.io;

import org.javacord.api.entity.server.Server;

import java.io.File;

// Classe contenant tous les dossiers du plugin
public class Folder {
    // Dossiers communs à tous les serveurs
    public static final File base = init(new File(System.getProperty("user.home"), "Idjenria/"));

    // Dossiers en fonction du serveur
    public static File players(Server server){ return init(new File(server(server), "players/")); }
    public static File server(Server server){ return init(new File(base, "server-" + server.getIdAsString())); }

    // Méthode pour créer les dossiers non existants
    private static File init(File folder){
        if(!folder.exists() && !folder.mkdir()) System.out.print("");
        return folder;
    }
}