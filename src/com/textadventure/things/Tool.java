package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;

import java.io.Serializable;

public class Tool extends Item implements Serializable {
    public Tool(String name, String description) {
        super(name, description);
    }

    public void changeContainer(String container) throws ItemNotFoundException {
        Container c = World.containerMap.get(container);
        if (c == null)
            throw new ItemNotFoundException(container);
        if (c.getTools().contains(this.name)) {
            //TODO: schauen wos ist und dann ins andere speichern
        }
    }
}
