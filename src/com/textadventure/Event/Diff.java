package com.textadventure.Event;

import com.textadventure.GameElement;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.locations.Location;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public abstract class Diff implements Serializable {
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
    public abstract boolean checkValidity();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
