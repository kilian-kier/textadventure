package com.textadventure;

import java.io.Serializable;

public class GameElement implements Serializable {
    protected String description;
    protected String name;
    public String getName(){
        return name;
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
}
