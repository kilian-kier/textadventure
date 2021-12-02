package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;

import java.io.Serializable;

abstract public class Item  extends GameElement implements RoomChangeable, Serializable {
    private Room room;


    public Item(String name, String description) {
        super(name);
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


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void investigate() {
        //TODO: comparen
    }
}
