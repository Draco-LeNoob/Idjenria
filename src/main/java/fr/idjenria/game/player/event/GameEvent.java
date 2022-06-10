package fr.idjenria.game.player.event;

import fr.idjenria.game.item.Item;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface GameEvent {
    static List<Item> items(){
        List<Item> items = new ArrayList<>();

        for(Item material : Item.values()){
            for(int i = 0; i < 100 - material.getRarity(); i++){
                items.add(material);
            }
        }

        return items;
    }

    static EmbedBuilder eventEmbed(String message, Icon avatar, String... infos){
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Événement");
        embed.setColor(Color.yellow);
        embed.setThumbnail(avatar);
        embed.setDescription(message);

        StringBuilder info = new StringBuilder();

        for(String str : infos)
            info.append(str).append("\n");

        if(infos.length > 0)
            embed.addField("Information" + (infos.length > 1 ? "s" : ""), info.toString());

        return embed;
    }

    EmbedBuilder execute(Server server, User user);
}