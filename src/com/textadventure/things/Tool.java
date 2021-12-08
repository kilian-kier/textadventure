package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;
import com.textadventure.exeptions.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tool extends Item implements Serializable {
    public Tool(String name, String description) {
        super(name, description);
    }



    @Override
    Container findItemContainer(String container) throws ItemNotFoundException{
        Container newContainer;
        if(container.equals("player")){
            newContainer=World.player.getToolsContainer();
        }else{
            newContainer=World.roomMap.get(container).getToolsContainer();
            if(newContainer==null){
                newContainer=World.containerMap.get(container);
                if(newContainer==null){
                    throw new ItemNotFoundException(container);
                }
            }
        }
        return newContainer;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Tool %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Container/Raum: %s",currentContainer);
        return string;
    }
}
