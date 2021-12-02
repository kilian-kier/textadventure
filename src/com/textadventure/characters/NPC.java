package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.locations.Room;

import java.io.Serializable;
import java.util.ArrayList;

public class NPC extends GameElement implements Serializable {
    private Room room;
    public ArrayList<String[]> dialog= new ArrayList<String[]>(); // Array[3]: An index 0 die Froge, an index 1 die Antwort und an index 2 a event (string ) oder null
    public NPC(String name, String description) {
        super(name);
        this.description = description;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    public Room getRoom(){
        return this.room;
    }

    public void removeAllDialog(){
        dialog.clear();
    }
    public void addDialog(String[] dialog){
        this.dialog.add(dialog);
    }
    public ArrayList<String[]> getDialog(){
        return this.dialog;
    }
    public void removeDialogIndex(int index) throws IndexOutOfBoundsException{
        dialog.remove(index);
    }
    public String[] getDialogIndex(int index) throws IndexOutOfBoundsException{
        return dialog.get(index);
    }
}
