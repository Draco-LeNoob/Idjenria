package fr.idjenria.game.player;

import fr.idjenria.game.item.Item;
import fr.idjenria.game.item.Material;
import fr.idjenria.io.Files;
import fr.idjenria.io.Folder;
import fr.idjenria.utils.Utils;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public static final String INVENTORY_FILE_EXTENSION = ".idjinv";

    public static void saveInventoryOf(Server server, User user, List<Item> inventory){
        String[] lines = new String[inventory.size()];

        for(int i = 0; i < lines.length; i++){
            Item item = inventory.get(i);

            lines[i] = item.getId() + " " + item.getAmount();
        }

        Files.writeLines(Player.getFileOf(server, user), lines);
    }

    public static List<Item> getInventoryOf(Server server, User user){
        List<Item> inventory = new ArrayList<>();

        for(String line : Files.readLines(Player.getFileOf(server, user))){
            String[] args = line.split(" ");

            Material material = Utils.fromOptional(Material.fromId(Long.parseLong(args[0])));
            int amount = Integer.parseInt(args[1]);

            assert material != null;
            inventory.add(new Item(material, amount));
        }

        return inventory;
    }

    public static void addItemToInventoryOf(Server server, User user, Item item){
        List<Item> inventory = Player.getInventoryOf(server, user);

        for(int i = 0; i < inventory.size(); i++){
            Item loopItem = inventory.get(i);

            if(loopItem.getType() == item.getType()){
                inventory.set(i, new Item(item.getType(), inventory.get(i).getAmount() + item.getAmount()));

                Player.saveInventoryOf(server, user, inventory);
                return;
            }
        }

        inventory.add(item);
        Player.saveInventoryOf(server, user, inventory);
    }

    public static void removeItemFromInventoryOf(Server server, User user, Material material){
        List<Item> inventory = Player.getInventoryOf(server, user);
        inventory.removeIf(item -> item.getType() == material);

        Player.saveInventoryOf(server, user, inventory);
    }

    public static void removeItemFromInventoryOf(Server server, User user, Material material, int amount){
        List<Item> inventory = Player.getInventoryOf(server, user);

        for(int i = 0; i < inventory.size(); i++){
            Item item = inventory.get(i);

            if(item.getType() == material){
                if(item.getAmount() <= amount){
                    removeItemFromInventoryOf(server, user, material);
                    return;
                }

                inventory.set(i, new Item(material, item.getAmount() - amount));
            }
        }

        Player.saveInventoryOf(server, user, inventory);
    }

    public static File getFileOf(Server server, User user){
        File file = new File(Folder.players(server), "inventory-" + user.getIdAsString() + Player.INVENTORY_FILE_EXTENSION);

        try{
            // Condition uniquement pour que le résultat de File::createNewFile() ne soit pas ignoré, IntelliJ casse les bonbons sur ça...
            if(!file.createNewFile()) System.out.print("");
        }catch(IOException e){
            e.printStackTrace();
        }

        return file;
    }
}