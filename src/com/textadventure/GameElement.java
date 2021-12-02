package com.textadventure;

import com.textadventure.interfaces.Interactable;

import java.io.Serializable;

public class GameElement implements Serializable, Interactable {
    protected String description;
    protected String name;
    public String getName(){
        return name;
    }

    public GameElement(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public void look() {
        //TODO
    }

    @Override
    public void investigate() {
        //TODO
    }
}
