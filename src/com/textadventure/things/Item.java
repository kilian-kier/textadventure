package com.textadventure.things;

import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;

public abstract class Item implements RoomChangeable {
    private final String name;
    private final String description;
    private Containable currentIn;

    public Item(String name, String description, Containable currentIn) {
        this.name = name;
        this.description = description;
        this.currentIn = currentIn;
    }

    @Override
    public void changeRoom(Exit exit) {
        //TODO: Container wechseln
    }

    @Override
    public void goBack() throws NoBackException {
        throw new NoBackException(this.name);
    }
}
