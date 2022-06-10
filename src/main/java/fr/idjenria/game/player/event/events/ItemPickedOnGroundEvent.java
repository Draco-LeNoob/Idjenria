package fr.idjenria.game.player.event.events;

import fr.idjenria.game.item.Item;
import fr.idjenria.game.player.Player;
import fr.idjenria.game.player.event.GameEvent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static fr.idjenria.utils.Utils.fromOptional;
import static fr.idjenria.utils.Utils.random;

public class ItemPickedOnGroundEvent implements GameEvent {
    @Override
    public EmbedBuilder execute(Server server, User user) {
        List<Long> items = new ArrayList<>();

        for(Item material : Item.values()){
            for(int i = 0; i < 100 - material.getRarity(); i++){
                items.add(material.getId());
            }
        }

        int amount = random(1, 3) - random(-1, 3);

        if(amount < 1) amount = 1;
        if(amount > 3) amount = 3;

        Item item = new Item(fromOptional(Item.fromId(items.get(random(0, items.size() - 1)))), amount);

        Player.addItemToInventoryOf(server, user, item);
        return GameEvent.eventEmbed("Vous marchez et trouver par chance, sur le sol : " + item, user.getAvatar());
    }
}