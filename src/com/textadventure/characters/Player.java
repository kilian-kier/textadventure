package com.textadventure.characters;

import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Item;

import java.util.Collection;

public class Player implements Containable, RoomChangeable {
    private final java.lang.String name;
    //TODO: welche Collection?
    Collection<Item> inventory;
    public Room currentRoom;
    private Room previousRoom;

    public Player(java.lang.String name, Room currentRoom) {
        this.name = name;
        this.currentRoom = currentRoom;
        this.previousRoom = null;
    }

    @Override
    public void changeRoom(String exit) {
        previousRoom = currentRoom;
        try {
            Exit temp = currentRoom.getExit(exit);
            currentRoom = temp.getDestination();
        } catch (ExitNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void goBack() throws NoBackException {
        if (previousRoom != null) {
            Room temp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = temp;
        } else {
            throw new NoBackException(this.name);
        }
    }


}
