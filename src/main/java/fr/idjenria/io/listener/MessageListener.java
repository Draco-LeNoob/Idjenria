package fr.idjenria.io.listener;

import fr.idjenria.command.Command;
import fr.idjenria.io.toml.TomlReader;
import fr.idjenria.utils.CustomMessage;
import fr.idjenria.utils.Utils;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;

// Classe contenant les Listener des messages
public class MessageListener {
    // Préfixe des commandes
    private static final String prefix = TomlReader.get(TomlReader.CONFIG_FILE, "bot.prefix");

    // Lorsqu'un message est envoyé
    public static void create(MessageCreateEvent event){
        // Ne pas accepter les messages privés
        Server server = event.getServer().orElse(null);
        if(server == null) return;

        // Ne pas prendre en compte les messages envoyés par des bots
        MessageAuthor author = event.getMessageAuthor();
        if(author.isBotUser()) return;

        String content = event.getMessageContent(); // Contenu du message

        // Détecter si le message est une commande
        if(!content.startsWith(prefix)) return;
        // Retirer les espaces doublons
        while(content.contains("  ")) content = content.replace("  ", " ");

        // Arguments de la commande
        String[] args = content.split(" ");
        // Id de la commande : premier argument du message
        String commandId = args[0].substring(prefix.length());

        // Retirer l'id de la commande des arguments
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        // Rendre le tableau d'argument en 'final'
        String[] arguments = args;

        // Supprimer le message appelant la commande
        event.getMessage().delete().join();

        // Exécuter la commande
        Command.get(commandId).ifPresent(command -> {
            switch(command.execute(event, arguments)){
                case WRONG_SYNTAXE -> event.getChannel().sendMessage(CustomMessage.SYNTAXE_ERROR);
                case WRONG_CHANNEL -> Utils.sendMessageTo(author, CustomMessage.CHANNEL_ERROR);
                case INVALID_ARGUMENT -> event.getChannel().sendMessage(CustomMessage.ARGUMENT_ERROR);
                case NO_PERMISSION -> Utils.sendMessageTo(author, CustomMessage.PERMISSION_ERROR);
            }
        });
    }
}