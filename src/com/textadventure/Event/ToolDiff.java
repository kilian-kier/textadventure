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
    private String container;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Tool tool ;
        try {
            tool= World.toolMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"tool");
        }
        if(description!=null){
            tool.setDescription(getDescription());
        }
        if(container!=null){
            try {
                tool.changeContainer(getContainer());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
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
    public void edit() {

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
        this.container=container;
    }
    public String getContainer()  {
        return container;
    }
}
