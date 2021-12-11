package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.locations.Room;

import java.io.Serializable;

/**
 * Eine Art Item ohne Möglichkeit weitere Items zu speichern
 */
public class Tool extends Item implements Serializable {
    public Tool(String name, String description) {
        super(name, description);
    }


    @Override
    Container findItemContainer(String container) throws ItemNotFoundException {
        Container newContainer;
        if (container.equals("player")) {
            newContainer = World.player.getToolsContainer();
        } else {
            Room room = World.roomMap.get(container);
            if (room == null) {
                newContainer = World.containerMap.get(container);
                if (newContainer == null) {
                    throw new ItemNotFoundException(container);
                }
            } else {
                newContainer = room.getToolsContainer();
            }
        }
        return newContainer;
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("Tool %s\n", name);
        string += String.format("Beschreibung: %s\n", description);
        string += String.format("Container/Raum: %s", currentContainer);
        return string;
    }
}
