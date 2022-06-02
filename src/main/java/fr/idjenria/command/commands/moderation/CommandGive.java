package fr.idjenria.command.commands.moderation;

import fr.idjenria.command.Command;
import fr.idjenria.command.CommandExecutor;
import fr.idjenria.command.CommandResult;
import fr.idjenria.game.item.Item;
import fr.idjenria.game.item.Material;
import fr.idjenria.game.player.Player;
import fr.idjenria.utils.Utils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Color;

public class CommandGive implements CommandExecutor {
    @Override
    public CommandResult execute(MessageCreateEvent event, Command command, String[] args) {
        if(args.length == 1){
            // Récupérer l'utilisateur
            User user = Utils.fromOptional(event.getMessageAuthor().asUser());

            // Récupérer l'item
            Material material = getMaterial(args[0]);

            if(material == null) return CommandResult.INVALID_ARGUMENT;

            // Ajouter l'item
            add(event, user, material, 1);

            // Valider la commande
            return CommandResult.SUCCESS;
        }else if(args.length == 2){
            // Récupérer l'utilisateur
            User user = Utils.userFromAuthor(event.getMessageAuthor());

            // Vérifier l'utilisateur
            if(user == null) return CommandResult.INVALID_ARGUMENT;

            // Définir l'item à donner et sa quantité
            Material material = getMaterial(args[0]);
            int amount = getAmount(args[1]);

            // Vérifier l'item et sa quantité
            if(material == null) return CommandResult.INVALID_ARGUMENT;
            if(amount < 1) return CommandResult.INVALID_ARGUMENT;

            // Ajouter le ou les items au joueur
            add(event, user, material, amount);

            // Valider la commande
            return CommandResult.SUCCESS;
        }else if(args.length == 3){
            // Récupérer l'utilisateur cible
            User user = Utils.fromOptional(Utils.userFromId(event.getApi(), args[0]));

            // Vérifier l'utilisateur
            if(user == null) return CommandResult.INVALID_ARGUMENT;

            // Définir l'item à donner et sa quantité
            Material material = getMaterial(args[1]);
            int amount = getAmount(args[2]);

            // Vérifier l'item et sa quantité
            if(material == null) return CommandResult.INVALID_ARGUMENT;
            if(amount < 1) return CommandResult.INVALID_ARGUMENT;

            // Ajouter le ou les items au joueur
            add(event, user, material, amount);

            // Valider la commande
            return CommandResult.SUCCESS;
        }else{
            return CommandResult.WRONG_SYNTAXE;
        }
    }

    private Material getMaterial(String arg){
        Material material;

        try{
            material = Material.valueOf(arg.toUpperCase());
        }catch(Exception e){
            return null;
        }

        return material;
    }

    private int getAmount(String arg){
        int amount;

        try{
            amount = Integer.parseInt(arg);
        }catch(Exception e){
            return 0;
        }

        return amount;
    }

    private void add(MessageCreateEvent event, User user, Material material, int amount){
        // Ajouter l'item à l'inventaire
        Player.addItemToInventoryOf(Utils.fromOptional(event.getServer()), user, new Item(material, amount));

        // Récupérer le serveur
        Server server = Utils.fromOptional(event.getServer(), null);
        assert server != null;

        // Création de l'Embed de réponse
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Don") // Titre de l'Embed
                .setColor(Color.cyan); // Couleur de l'Embed (couleur du rôle de l'utilisateur)

        // Ajouter une image pour indiquer le propriétaire de l'inventaire
        embed.setThumbnail(user.getAvatar());

        // Définir la description
        embed.setDescription(user.getDisplayName(server) + " a reçu " + amount + " * " + material.getName());

        // Envoyer le message
        event.getChannel().sendMessage(embed);
    }
}