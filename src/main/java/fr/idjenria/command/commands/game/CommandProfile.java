package fr.idjenria.command.commands.game;

import fr.idjenria.command.Command;
import fr.idjenria.command.CommandExecutor;
import fr.idjenria.command.CommandResult;
import fr.idjenria.game.player.Player;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

import static fr.idjenria.utils.Utils.fromOptional;
import static fr.idjenria.utils.Utils.userFromAuthor;

public class CommandProfile implements CommandExecutor {
    @Override
    public CommandResult execute(MessageCreateEvent event, Command command, String[] args) {
        if(args.length == 0){
            // Récupérer le serveur
            Server server = fromOptional(event.getServer());
            // Récupérer l'utilisateur
            User user = userFromAuthor(event.getMessageAuthor());

            // Informations de l'utilisateur
            int level = Player.getLevelOf(server, user);
            int nextLevelExp = Player.getExperienceForLevel(level + 1);
            int experience = Player.getExperienceOf(server, user);

            float nextLevelPercentage = (float)experience / (float)nextLevelExp * 100;

            System.out.println("level: " + level);
            System.out.println("next level exp: " + nextLevelExp);
            System.out.println("exp: " + experience);
            System.out.println("percentage: " + nextLevelPercentage);

            // Définir la barre de progression
            int dashLength = Math.round(nextLevelPercentage) / 5;

            // Texte de la barre de progression
            String bar = "[" + "#".repeat(dashLength) + "...".repeat(20 - dashLength) + "]\n" + experience + " / " + nextLevelExp;

            // Lancer un événement aléatoire
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Profile de " + user.getDisplayName(server))
                    .setThumbnail(user.getAvatar())
                    .setColor(Color.green)
                    .addField("Niveau", "" + level)
                    .addField("Points d'Expérience", "" + experience)
                    .addField("Avancement d'expérience", bar)
            );

            // Valider la commande
            return CommandResult.SUCCESS;
        }else{
            // Trop d'arguments : on retourne une erreur
            return CommandResult.WRONG_SYNTAXE;
        }
    }
}