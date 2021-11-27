package com.textadventure.locations;

import java.util.Collection;

public class Location {
    private final String name;
    private final String description;
    //TODO: welche Collection?
    private Collection<Room> rooms;

    Location(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
