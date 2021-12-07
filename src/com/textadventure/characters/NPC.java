package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.locations.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NPC extends GameElement implements Serializable {
    private ArrayList<String[]> dialog = new ArrayList<>(); // Array[3]: An index 0 die Froge, an index 1 die Antwort und an index 2 a event (string ) oder null
    private String room;

    public NPC(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void removeAllDialog() {
        dialog.clear();
    }

    public void addDialog(String[] dialog) {
        this.dialog.add(dialog);
    }
    public void setDialog(ArrayList<String[]> dialog){
        this.dialog=dialog;
    }

    public ArrayList<String[]> getDialog() {
        return this.dialog;
    }

    public void removeDialogIndex(int index) throws IndexOutOfBoundsException {
        dialog.remove(index);
    }

    public String[] getDialogIndex(int index) throws IndexOutOfBoundsException {
        return dialog.get(index);
    }

    public void changeContainer(String newRoomString) throws ItemNotFoundException, NullPointerException{
        Room newRoom= World.roomMap.get(newRoomString);
        if(this.room!=null) {
            Room oldRoom = World.roomMap.get(room);
            if (oldRoom.getNpcs().contains(this.name)) {
                oldRoom.getNpcs().remove(name);
                newRoom.addNpcs(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newRoom.addNpcs(this.name);
        }
        this.name=newRoomString;
    }
}
