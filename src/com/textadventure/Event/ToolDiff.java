package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.locations.Room;
import com.textadventure.things.Tool;

import java.util.Collection;

public class ToolDiff extends Diff{
    public ToolDiff(String name){
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {
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
    boolean checkValidity() {
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

    public void setContainer(String container)  {
        differences.put("container",container);
    }
    public String getContainer()  {
        return (String)differences.get("container");
    }
}
