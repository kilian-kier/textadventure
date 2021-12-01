package com.textadventure.locations;

import com.textadventure.GameElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Location extends GameElement implements Serializable {
    private ArrayList<Room> rooms=new ArrayList<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public ArrayList<Room> getRooms()  {
        return rooms;
    }
    public void addRoom(Room room) {
            rooms.add(room);
    }
    public Room getRoomIndex(int index) throws IndexOutOfBoundsException{
            return rooms.get(index);
    }
    public void removeAllRooms(){
        rooms.clear();
    }
    public void removeRoomIndex(int index) throws IndexOutOfBoundsException{
        rooms.remove(index);
    }
}
