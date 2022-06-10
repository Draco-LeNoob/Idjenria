package fr.idjenria.game.player.event.events;

import fr.idjenria.game.item.Item;
import fr.idjenria.game.player.Player;
import fr.idjenria.game.player.event.GameEvent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

import static fr.idjenria.utils.Utils.random;

public class DiscussEvent implements GameEvent {
    @Override
    public EmbedBuilder execute(Server server, User user) {
        List<Item> items = GameEvent.items();
        items.removeIf(item -> !item.canObtainDiscussing());

        Item item = new Item(items.get(random(0, items.size() - 1)), 1);
        Player.addItemToInventoryOf(server, user, item);

        return GameEvent.eventEmbed(
                "Vous avez rencontré une personne en vous baladant, et elle vous offre généreusement " + item,
                user.getAvatar()
        );
    }
}