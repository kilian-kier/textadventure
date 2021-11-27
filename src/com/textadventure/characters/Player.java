package com.textadventure.characters;

import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.Interactable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Item;

import java.util.Collection;
import java.util.LinkedList;

public class Player implements Containable, RoomChangeable {
    private final String name;
    //TODO: welche Collection?
    Collection<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;

    Player(String name) {
        this.name = name;
    }

    @Override
    public void changeRoom(Exit exit) {
        previousRoom = currentRoom;
        currentRoom = exit.getDestination();
    }

    @Override
    public void goBack() {
        Room temp = currentRoom;
        currentRoom = previousRoom;
        previousRoom = temp;
    }


}
