package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

import java.io.Serializable;
import java.util.ArrayList;

public class Container extends Item implements Serializable {
    ArrayList<String> tools = new ArrayList<>();


    public Container(String name, String description) {
        super(name, description);
    }

    @Override
    Container findItemContainer(String container) throws ItemNotFoundException{
        Container newContainer=World.roomMap.get(container).getToolsContainer();
        if(newContainer==null){
            throw new ItemNotFoundException(container);
        }
        return newContainer;
    }

    public Tool getTool(String name) {
        return World.toolMap.get(name);
    }

    public void addTool(String tool) {
        tools.add(tool);
    }

    public ArrayList<String> getTools() {
        return this.tools;
    }

    public void removeAllTools() {
        tools.clear();
    }
    public String removeTool(String name) {
        tools.remove(name);
        return name;
    }
}
