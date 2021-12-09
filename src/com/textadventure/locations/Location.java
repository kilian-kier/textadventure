package com.textadventure.locations;

import com.textadventure.GameElement;

import java.io.Serializable;
import java.util.ArrayList;

public class Location extends GameElement implements Serializable {
    private ArrayList<String> rooms = new ArrayList<>();

    public Location(String name, String description) {
        super(name);
        this.description = description;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void addRoom(String room) {
        if(!rooms.contains(room)){
            rooms.add(room);
        }
    }
    public void setRooms(ArrayList<String> rooms){
        this.rooms=rooms;
    }
    public String getRoomIndex(int index) throws IndexOutOfBoundsException {
        return rooms.get(index);
    }

    public boolean removeRoom (String name){
        return rooms.remove(name);
    }
    public void removeAllRooms() {
        rooms.clear();
    }

    public void removeRoomIndex(int index) throws IndexOutOfBoundsException {
        rooms.remove(index);
    }
}
