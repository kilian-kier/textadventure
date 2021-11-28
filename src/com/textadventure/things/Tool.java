package com.textadventure.things;

import com.textadventure.interfaces.Containable;
import com.textadventure.locations.Room;

public class Tool extends Item {
    Tool(String name, String description, String info, Containable currentIn) {
        super(name, description, info,currentIn);
    }
}
