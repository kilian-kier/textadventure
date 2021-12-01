package com.textadventure.characters;

import com.textadventure.GameElement;

public class NPC extends GameElement {
    public NPC(String name, String description) {
        this.name = name;
        this.description = description;
    }
    String room;
    public void setRoom(String room){
        this.room=room;
    }
    public String getRoom(){
        return room;
    }

}
