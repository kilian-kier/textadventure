package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;

abstract public class Item  extends GameElement implements RoomChangeable{
    private Containable currentIn;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
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
