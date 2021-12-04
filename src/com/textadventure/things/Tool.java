package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

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
}
