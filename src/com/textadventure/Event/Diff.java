package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Diff implements Serializable  {
    private static final long serialVersionUID = 6806889103294573466L;
    protected String name;
    protected String description;
    protected Boolean interactable=null;
    public Diff(String name)  {
        this.name=name;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }


    public abstract void applyDiffToWorld() throws GameElementNotFoundException;
    public abstract boolean check();
    public abstract void edit();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void loadFromHashMap(HashMap<String, String> diff);
}
