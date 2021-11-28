package com.textadventure.things;

import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;

public abstract class Item implements RoomChangeable {
    private final java.lang.String name;
    private final java.lang.String description;
    private Containable currentIn;

    public Item(java.lang.String name, java.lang.String description, Containable currentIn) {
        this.name = name;
        this.description = description;
        this.currentIn = currentIn;
    }

    @Override
    public void changeRoom(String exit) {
        //TODO: Container wechseln
    }

    @Override
    public void goBack() throws NoBackException {
        throw new NoBackException(this.name);
    }

    public java.lang.String getName() {
        return name;
    }
}
