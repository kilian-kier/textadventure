package com.textadventure.characters;

import com.textadventure.GameElement;

import java.io.Serializable;
import java.util.ArrayList;

public class NPC extends GameElement implements Serializable {
    private final ArrayList<String[]> dialog = new ArrayList<>(); // Array[3]: An index 0 die Froge, an index 1 die Antwort und an index 2 a event (string ) oder null
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

    public ArrayList<String[]> getDialog() {
        return this.dialog;
    }

    public void removeDialogIndex(int index) throws IndexOutOfBoundsException {
        dialog.remove(index);
    }

    public String[] getDialogIndex(int index) throws IndexOutOfBoundsException {
        return dialog.get(index);
    }
}
