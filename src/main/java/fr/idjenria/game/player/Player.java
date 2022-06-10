package fr.idjenria.game.player;

import fr.idjenria.game.item.Inventory;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Player {
    // Extensions de fichiers dans lesquels sont sauvegardés les donnés du joueur
    public static final String INVENTORY_FILE_EXTENSION = ".idjinv";
    public static final String EXPERIENCE_FILE_EXTENSION = ".idjexp";
    public static final String JOB_FILE_EXTENSION = ".idjjob";

    // Informations d'un joueur
    private final Server server;
    private final User user;
    private final Inventory inventory;

    public Player(Server server, User user, Inventory inventory) {
        this.server = server;
        this.user = user;
        this.inventory = inventory;
    }

    // Getters
    public Server getServer() { return server; }
    public User getUser() { return user; }
    public Inventory getInventory() { return inventory; }
}