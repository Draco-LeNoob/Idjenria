package fr.idjenria.command.commands.game;

import fr.idjenria.command.Command;
import fr.idjenria.command.CommandExecutor;
import fr.idjenria.command.CommandResult;
import fr.idjenria.game.Game;
import fr.idjenria.game.player.Player;
import fr.idjenria.game.player.event.GameEvent;
import fr.idjenria.utils.Utils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;

import static fr.idjenria.utils.Utils.*;

public class CommandEvent implements CommandExecutor {
    @Override
    public CommandResult execute(MessageCreateEvent event, Command command, String[] args) {
        if(args.length == 0){
            // Récupérer le serveur
            Server server = fromOptional(event.getServer());
            // Récupérer l'utilisateur
            User user = userFromAuthor(event.getMessageAuthor());

            int level = Player.getLevelOf(server, user);
            int experience = Utils.random(1, 5);
            String s = experience > 1 ? "s" : "";

            boolean levelUp = Player.addExperienceTo(server, user, experience);

            List<String> infos = new ArrayList<>();

            infos.add("Vous gagnez " + experience + " point" + s + " d'expérience" + s + " !");

            if(levelUp) infos.add("Félicitations ! Vous êtes désormais niveau " + (level + 1) + " !");

            // Créer l'Embed
            EmbedBuilder embed = Utils.addEmbedField(getRandomEvent().execute(server, user), "Informations", infos.toArray(new String[0]));

            // Lancer un événement aléatoire et envoyer le message
            event.getChannel().sendMessage(embed);

            // Valider la commande
            return CommandResult.SUCCESS;
        }else{
            // Trop d'arguments : on retourne une erreur
            return CommandResult.WRONG_SYNTAXE;
        }
    }

    public GameEvent getRandomEvent(){ return Game.EVENTS[random(0, Game.EVENTS.length - 1)]; }
}