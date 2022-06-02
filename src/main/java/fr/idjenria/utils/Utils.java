package fr.idjenria.utils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class Utils {
    public static void sendMessageTo(MessageAuthor author, EmbedBuilder message){ Utils.userFromAuthor(author).sendMessage(message); }

    public static User userFromAuthor(MessageAuthor author){ return Utils.fromOptional(author.asUser(), null); }
    public static Optional<User> userFromId(DiscordApi api, String id){
        User user;

        try {
            user = api.getUserById(id
                    .replace("<", "")
                    .replace(">", "")
                    .replace("@", "")
                    .replace("!", "")
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            // L'utilisateur n'existe pas : on retourne une erreur
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public static<E> E fromOptional(Optional<E> optional){ return Utils.fromOptional(optional, null); }
    public static<E> E fromOptional(Optional<E> optional, E orElse){
        E e = optional.orElse(orElse);

        assert e != null;
        return e;
    }
}