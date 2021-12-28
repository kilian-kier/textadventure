package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.Story.World;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Enthält eine Liste mit Räumen, die sich an diesem Ort befinden
 */
public class Location extends GameElement implements Serializable {
    private static final long serialVersionUID = -6064497007961119893L;
    private ArrayList<String> rooms = new ArrayList<>();

    public Location(String name, String description) {
        super(name);
        this.description = description;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(String room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        }
    }

    public String getRoomIndex(int index) throws IndexOutOfBoundsException {
        return rooms.get(index);
    }

    public boolean removeRoom(String name) {
        return rooms.remove(name);
    }

    public void removeAllRooms() {
        rooms.clear();
    }

    public void removeRoomIndex(int index) throws IndexOutOfBoundsException {
        rooms.remove(index);
    }

    public boolean check() {
        boolean ret = true;
        for (String room : rooms) {
            if (World.roomMap.get(room) == null) {
                System.out.printf("Raum %s von Location %s existiert nicht\n", room, name);
                ret = false;
            } else {
                if (!World.roomMap.get(room).getLocation().equals(name)) {
                    System.out.printf("Raum %s wird von Location %s referenziert aber nicht umgekehrt\n", room, name);
                    ret = false;
                }
            }
        }
        return ret;
    }


    @Override
    public String toString() {
        String string = "";
        string += String.format("Location %s\n", name);
        string += String.format("Beschreibung: %s\n", getDescription());
        string += String.format("Räume: %s", rooms.toString());
        return string;
    }
}
