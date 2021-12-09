package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.locations.Room;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.util.Collection;

public class ToolDiff extends Diff implements Serializable {
    public ToolDiff(String name){
        super(name);
    }

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Tool tool ;
        try {
            tool= World.toolMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"tool");
        }
        try{ //Description
            tool.setDescription(getDescription());
        }catch(Exception e){}
        try{
            tool.changeContainer(getContainer());
        }catch(Exception e){}
    }

    @Override
    public boolean check() {
        String stringTemp;
        boolean ret=true;
        stringTemp=getContainer();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null && World.containerMap.get(stringTemp)==null){
                System.out.printf("Raum/Container %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Container/Raum: %s",getContainer());
        return string;
    }
    public void setContainer(String container)  {
        differences.put("container",container);
    }
    public String getContainer()  {
        return (String)differences.get("container");
    }
}
