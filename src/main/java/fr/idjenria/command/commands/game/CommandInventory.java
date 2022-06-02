package fr.idjenria.command.commands.game;

import fr.idjenria.command.Command;
import fr.idjenria.command.CommandExecutor;
import fr.idjenria.command.CommandResult;
import fr.idjenria.game.item.Item;
import fr.idjenria.game.player.Player;
import fr.idjenria.utils.Utils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

// Commande pour afficher son inventaire ou celui d'un autre joueur
public class CommandInventory implements CommandExecutor {
    @Override
    public CommandResult execute(MessageCreateEvent event, Command command, String[] args) {
        if(args.length == 0){
            // Envoyer le message
            send(event, Utils.fromOptional(event.getMessageAuthor().asUser()));

            // Valider la commande
            return CommandResult.SUCCESS;
        }else if(args.length == 1){
            // Récupérer la cible de la commande
            User user;

            try {
                user = event.getApi().getUserById(args[0]
                        .replace("<", "")
                        .replace(">", "")
                        .replace("@", "")
                        .replace("!", "")
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                // La cible n'existe pas : on retourne une erreur
                return CommandResult.INVALID_TARGET;
            }

            // Envoyer le message
            send(event, user);

            // Valider la commande
            return CommandResult.SUCCESS;
        }else{
            return CommandResult.WRONG_SYNTAXE;
        }
    }

    private void send(MessageCreateEvent event, User user){
        // Récupérer le serveur, l'auteur de la commande et son inventaire
        List<Item> inventory = Player.getInventoryOf(Utils.fromOptional(event.getServer()), user);

        // Création de l'Embed de réponse
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Inventaire") // Titre de l'Embed
                .setColor(Color.cyan); // Couleur de l'Embed (couleur du rôle de l'utilisateur)

        // Ajouter une image pour indiquer le propriétaire de l'inventaire
        embed.setThumbnail(user.getAvatar());

        // Définir la description
        embed.setDescription(
                inventory.isEmpty() ? "Votre inventaire est vide... Alors vous, vous êtes pas bien riche !" : Arrays.toString(inventory.toArray())
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", "\n")
        );

        // Envoyer le message
        event.getChannel().sendMessage(embed);
    }
}