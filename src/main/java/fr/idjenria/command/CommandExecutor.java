package fr.idjenria.command;

import org.javacord.api.event.message.MessageCreateEvent;

// Interface contenant la méthode d'exécution d'une commande
public interface CommandExecutor {
    // Méthode appelée pour exécuter une commande
    CommandResult execute(MessageCreateEvent event, Command command, String[] args);
}