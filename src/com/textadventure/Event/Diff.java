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
    protected boolean happened=false;

    private String[] dependent;
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
    public String[] getDependent() {
        return dependent;
    }

    public void setDependent(String[] dependent) {
        this.dependent = dependent;
    }

    public boolean isHappened() {
        return happened;
    }

    public void setHappened(boolean happened) {
        this.happened = happened;
    }
    abstract void applyDiffToWorld() throws GameElementNotFoundException;
    abstract boolean checkValidity();
    /*
    Ibosicht vo mi, dass i mi auskenn
    location String       check      room
    room String           check      container, tool, exit
    rooms List<String>    check      location
    dialog List<String[]> check      npc
    destination1 String   check      exit
    destination2 String   check      exit
    description String    check      container, tool, exit, location, room, npc
    exits List<String>    check      room
    tools List<String>    check      container, room
    container List<String>check      room
   */

}
