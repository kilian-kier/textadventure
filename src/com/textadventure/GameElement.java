package com.textadventure;

abstract public class GameElement {
    protected String description;
    protected String info;
    protected String name;
    public String getInfo(){
        return info;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public void setName(String name){
        this.name = name;
    }
}
