package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.interfaces.Containable;
import com.textadventure.things.Tool;

import java.util.HashMap;
import java.util.Map;

public class Room extends GameElement implements Containable{

    //TODO: welche Collection?
    private final Map<java.lang.String, Exit> exits = new HashMap<>();
    private String location;
    public Room(String name, String description) {
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

    @Override
    public void put(Tool tool) {

    }

    @Override
    public Tool take(String name) {
        return null;
    }
}
