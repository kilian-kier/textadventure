package com.textadventure.locations;

import com.textadventure.GameElement;

import java.util.Collection;
import java.util.HashSet;

public class Location extends GameElement {
    //TODO: welche Collection?
    private Collection<Room> rooms = new HashSet<>();

    public Location(String name, String description, String info) {
        this.name = name;
        this.description = description;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
