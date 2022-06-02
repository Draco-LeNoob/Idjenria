package fr.idjenria.command.commands.moderation;

import fr.idjenria.command.Command;
import fr.idjenria.command.CommandExecutor;
import fr.idjenria.command.CommandResult;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;

public class CommandClear implements CommandExecutor {
    @Override
    public CommandResult execute(MessageCreateEvent event, Command command, String[] args) {
        // Vérifier le nombre d'arguments
        if(args.length != 1) return CommandResult.WRONG_SYNTAXE;

        // Définir le nombre de messages à supprimer
        int amount;

        try{
            // Essayer de caste en int le premier argument de la commande
            amount = Integer.parseInt(args[0]);
        }catch(Exception e){
            // Caste failed : le premier argument n'est pas un nombre : on retourne une erreur
            return CommandResult.INVALID_ARGUMENT;
        }

        // On récupère les derniers messages du channel jusqu'à la limite donnée
        MessageSet set = event.getChannel().getMessagesBefore(amount, event.getMessage()).join();

        // Supprimer les messages récupérés
        event.getChannel().bulkDelete(set);

        // On retourne la réussite de l'exécution de la commande
        return CommandResult.SUCCESS;
    }
}