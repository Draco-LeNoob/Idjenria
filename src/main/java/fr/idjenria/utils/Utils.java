package fr.idjenria.utils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class Utils {
    public static void sendMessageTo(MessageAuthor author, EmbedBuilder message){ Utils.userFromAuthor(author).sendMessage(message); }
    public static int random(int a, int b){ return a + (int)(Math.random() * ((b - a) + 1)); }

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

    public static File createFile(File parent, String child){ return createFile(new File(parent, child)); }
    public static File createFile(String parent, String child){ return createFile(new File(parent, child)); }
    public static File createFile(File file){
        try{
            // Condition uniquement pour que le résultat de File::createNewFile() ne soit pas ignoré, IntelliJ casse les bonbons sur ça...
            if(!file.createNewFile()) System.out.print("");
        }catch(IOException e){
            e.printStackTrace();
        }

        return file;
    }

    public static EmbedBuilder addEmbedField(EmbedBuilder embed, String name, String... lines){
        StringBuilder info = new StringBuilder();

        for(String str : lines)
            info.append(str).append("\n");

        if(lines.length > 0)
            embed.addField("Information" + (lines.length > 1 ? "s" : ""), info.toString());

        return embed;
    }
}