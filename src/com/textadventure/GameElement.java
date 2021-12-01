package com.textadventure;

 public class GameElement{
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
