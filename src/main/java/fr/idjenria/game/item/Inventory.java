package fr.idjenria.game.item;

import fr.idjenria.game.player.Player;
import fr.idjenria.io.Files;
import fr.idjenria.io.Folder;
import fr.idjenria.utils.Utils;

import java.io.File;
import java.util.HashMap;

public class Inventory {
    // Extension des fichiers d'inventaire
    public static final String FILE_EXTENSION = ".idjinv";

    // Variables
    private final Player owner;
    private HashMap<Item, Integer> items;

    // Constructeur
    public Inventory(Player owner){
        this.owner = owner;
        this.items = new HashMap<>();
    }

    // Récupérer le nombre d'un item donné dans l'inventaire
    public int getAmount(Item item){ return items.getOrDefault(item, 0); }
    // Vérifier si l'inventaire contient un item
    public boolean containItem(Item item){ return items.containsKey(item); }
    // Ajouter un item à l'inventaire
    public void add(Item item, int amount){
        // Ne pas ajouter un nombre négatif d'items
        if(amount < 1) return;

        if(!items.containsKey(item)){
            // Ajouter l'item s'il n'est pas déjà dans l'inventaire et le sauvegarder
            items.put(item, amount);
            this.save();

            return;
        }

        // Ajouter le montant indiqué d'items de l'inventaire et le sauvegarder
        items.replace(item, items.get(item) + amount);
        this.save();
    }

    // Retirer complètement un item donné de l'inventaire
    public void remove(Item item){
        items.remove(item);
        this.save();
    }

    // Retirer un nombre donné d'un item donné de l'inventaire
    public void remove(Item item, int amount){
        // Ne pas retirer un nombre négatif d'items
        if(amount < 1) return;
        // Ne rien faire si l'item est dans l'inventaire
        if(!items.containsKey(item)) return;

        // Nombre avant changement d'items dans l'inventaire
        int currentAmount = items.get(item);

        // Si le nombre d'items à supprimer est supérieur ou égal au nombre d'items avant changement, retirer l'item de l'inventaire et le sauvegarder
        if(currentAmount - amount < 1){
            items.remove(item);
            this.save();
            return;
        }

        // Retirer l'item de l'inventaire et le sauvegarder
        items.replace(item, currentAmount - amount);
        this.save();
    }

    // Sauvegarder l'inventaire
    public void save(){
        // Créer un tableau qui contiendra les différentes lignes du fichier, chaque ligne contiendra un item et sa quantité, tout deux séparés d'un espace
        String[] lines = new String[items.size()];

        // Boucler sur tous les items de l'inventaire
        for(int i = 0; i < items.size(); i++){
            // Récupérer l'item actuel de l'itération de la boucle
            Item item = items.keySet().stream().toList().get(i);

            // Id de l'item à sauvegarder
            long id = item.getId();
            // Quantité de l'item à sauvegarder
            int count = items.get(item);

            // Remplir la ligne avec les informations récupérées
            lines[i] = id + " " + count;
        }

        // Écrire toutes les lignes dans l'inventaire
        Files.writeLines(Inventory.getFileOf(owner), lines);
    }

    // Récupérer le fichier de l'inventaire d'un joueur donné
    public static File getFileOf(Player player){
        // Le fichier est dans : [Bot Folder]/[Server Id]/[Player Id]-inventory.[Inventory Extension]
        // Un texte entre [] indique que l'information dépend d'une variable
        // La méthode createFile(File) permet de créer le fichier s'il n'existe pas, et de le récupérer, pour le retourner

        return Utils.createFile(new File(Folder.players(player.getServer()), player.getUser().getIdAsString() + "-inventory" + FILE_EXTENSION));
    }
}