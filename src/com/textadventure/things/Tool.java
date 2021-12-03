package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

import java.io.Serializable;

public class Tool extends Item implements Serializable {
    public Tool(String name, String description) {
        super(name, description);
    }

    public void changeContainer(String container) throws ItemNotFoundException, ItemNotFoundInContainerException {
        Container c = World.containerMap.get(container);
        if (c == null)
            throw new ItemNotFoundException(container);
        if (c.getTools().contains(this.name)) {
            c.getTools().remove(this.name);
            World.player.addTool(this.name);
        } else if (World.player.getTools().contains(this.name)) {
            World.player.getTools().remove(this.name);
            c.addTool(this.name);
        } else
            throw new ItemNotFoundInContainerException(this.name, container, "Player");
    }
}
