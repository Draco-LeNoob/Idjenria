package fr.idjenria.utils;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class CustomMessage {
    public static final EmbedBuilder SYNTAXE_ERROR;
    public static final EmbedBuilder CHANNEL_ERROR;
    public static final EmbedBuilder ARGUMENT_ERROR;
    public static final EmbedBuilder PERMISSION_ERROR;

    static{
        SYNTAXE_ERROR = new EmbedBuilder();
        SYNTAXE_ERROR.setTitle("Erreur de syntaxe");
        SYNTAXE_ERROR.setColor(Color.red);
        SYNTAXE_ERROR.setDescription("Il semblerait que vous vous soyez trompé de syntaxe pour l'utilisation de cette commande...");

        CHANNEL_ERROR = new EmbedBuilder();
        CHANNEL_ERROR.setTitle("Erreur de salon");
        CHANNEL_ERROR.setColor(Color.red);
        CHANNEL_ERROR.setDescription("Il semblerait que vous vous soyez trompé de salon pour l'utilisation de cette commande...");

        PERMISSION_ERROR = new EmbedBuilder();
        PERMISSION_ERROR.setTitle("Erreur de permission");
        PERMISSION_ERROR.setColor(Color.red);
        PERMISSION_ERROR.setDescription("Il semblerait que vous n'ayez pas la permission d'exécuter cette commande...");

        ARGUMENT_ERROR = new EmbedBuilder();
        ARGUMENT_ERROR.setTitle("Erreur d'argument");
        ARGUMENT_ERROR.setColor(Color.red);
        ARGUMENT_ERROR.setDescription("Il semblerait que vous n'ayez pas fournis d'arguments valides pour exécuter cette commande...");
    }
}