package com.textadventure.things;

import com.textadventure.interfaces.Containable;
import com.textadventure.locations.Room;

import java.io.Serializable;

public class Tool extends Item implements Serializable {
    public Tool(String name, String description) {
        super(name, description);
    }
}
