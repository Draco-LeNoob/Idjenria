package fr.idjenria;

import fr.idjenria.io.listener.MessageListener;
import fr.idjenria.io.toml.TomlReader;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

// Classe principale
public class Main {
    public static void main(String[] args) throws Exception {
        // Connecter le bot
        DiscordApi api = new DiscordApiBuilder()
                .setToken(TomlReader.get(TomlReader.CONFIG_FILE, "bot.token"))
                .login().join();

        // Ajouter le Message Create Listener
        api.addMessageCreateListener(MessageListener::create);
    }
}