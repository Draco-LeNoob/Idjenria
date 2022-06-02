package fr.idjenria.command;

import fr.idjenria.command.commands.game.CommandInventory;
import fr.idjenria.command.commands.moderation.CommandClear;
import fr.idjenria.command.commands.moderation.CommandGive;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Classe représentant une commande du bot
// Le 'get' devant le nom des variables est fait pour que les getters soient plus conventionnels
public record Command(String getId, CommandExecutor getExecutor, String... getAliases) {
    // Liste des commandes
    private static final List<Command> commands;

    static{
        // Instancier la liste des commandes
        commands = new ArrayList<>();

        // Ajouter toutes les commandes
        commands.addAll(
                List.of(
                        new Command("inventory", new CommandInventory(), "i", "inv"),
                        new Command("clear", new CommandClear(), "c"),
                        new Command("give", new CommandGive(), "g")
                )
        );
    }

    // Fonction pour récupérer une commande avec son id / alias
    public static Optional<Command> get(String id){
        for(Command command : commands){
            if(command.getId().equals(id) || Arrays.asList(command.getAliases()).contains(id)) return Optional.of(command);
        }

        // return un 'Optional' vide si la commande n'est pas trouvée
        return Optional.empty();
    }

    // Méthode d'exécution de la commande
    public CommandResult execute(MessageCreateEvent event, String[] args){ return this.getExecutor().execute(event, this, args); }
}