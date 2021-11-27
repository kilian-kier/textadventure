package com.textadventure.locations;

import com.textadventure.interfaces.Containable;

public class Room implements Containable {
    private final String name;
    private final String description;

    Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
