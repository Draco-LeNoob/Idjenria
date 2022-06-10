package fr.idjenria.game;

import fr.idjenria.game.player.event.ElementGameEvent;
import fr.idjenria.game.player.event.GameEvent;
import fr.idjenria.game.player.event.events.DiscussEvent;
import fr.idjenria.game.player.event.events.ItemPickedOnGroundEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Game {
    @ElementGameEvent public static final GameEvent EVENT_ITEM_PICKED_ON_GROUND = new ItemPickedOnGroundEvent();
    @ElementGameEvent public static final GameEvent DISCUSS = new DiscussEvent();

    public static final GameEvent[] EVENTS;

    static{
        List<GameEvent> events = new ArrayList<>();
        final Field[] fields = Game.class.getDeclaredFields();

        for(final Field field : fields){
            if(!field.isAnnotationPresent(ElementGameEvent.class)) continue;
            if(field.getType() != GameEvent.class) continue;

            field.setAccessible(true);

            try {
                events.add((GameEvent) field.get(GameEvent.class));

                System.out.println("Added " + field.getName() + " event");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        EVENTS = events.toArray(new GameEvent[0]);
    }
}