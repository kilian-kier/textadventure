package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Diff implements Serializable  {
    protected String name;
    protected HashMap<String,Object> differences= new HashMap<>();
    public Diff(String name)  {
        this.name=name;
    }
    public void setDescription(String description){
        differences.put("description",description);
    }
    public String getDescription(){
        return (String)differences.get("description");
    }

    public abstract void applyDiffToWorld() throws GameElementNotFoundException;
    public abstract boolean check();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
