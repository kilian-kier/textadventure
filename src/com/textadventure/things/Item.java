package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;

public abstract class Item  extends GameElement implements RoomChangeable{
    private Containable currentIn;

    public Item(String name, String description, String info, Containable currentIn) {
        this.name = name;
        this.description = description;
        this.info = info;
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
