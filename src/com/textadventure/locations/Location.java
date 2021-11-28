package com.textadventure.locations;

import java.util.Collection;
import java.util.HashSet;

public class Location {
    private final String name;
    private final String description;
    //TODO: welche Collection?
    private Collection<Room> rooms = new HashSet<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
