package com.textadventure.locations;

import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.interfaces.Containable;

import java.util.HashMap;
import java.util.Map;

public class Room implements Containable {
    private final java.lang.String name;
    private final java.lang.String description;
    //TODO: welche Collection?
    private final Map<java.lang.String, Exit> exits = new HashMap<>();

    public Room(java.lang.String name, java.lang.String description) {
        this.name = name;
        this.description = description;
    }

    public java.lang.String getName() {
        return name;
    }

    public void addExit(Exit exit) {
        exits.put(exit.getName(), exit);
    }

    public Exit getExit(String name) throws ExitNotFoundException {
        Exit tmp = exits.get(name);
        if (tmp == null) {
            throw new ExitNotFoundException(name);
        }
        return exits.get(name);
    }
}
