package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.KeyAlreadyUsedException;
import com.textadventure.interfaces.Containable;

import java.io.Serializable;
import java.util.ArrayList;

public class Container extends Item  implements Serializable, Containable {
    ArrayList<String> tools = new ArrayList<>();
    public Container(String name, String description) {
        super(name, description);
    }

    //TODO: vielleicht amol löschen wenns do Herr Gamper nt braucht
    public void removeToolsIndex(int index) throws IndexOutOfBoundsException{
        tools.remove(index);
    }
    public ArrayList<String> getTools(){
        return this.tools;
    }
    public void removeAllTools(){
        tools.clear();
    }
    public String getToolIndex(int index) throws IndexOutOfBoundsException{
        return tools.get(index);
    }

    @Override
    public void put(Tool tool) throws KeyAlreadyUsedException {
        if (tools.contains(tool.getName()))
            throw new KeyAlreadyUsedException(tool.getName());
        if (World.toolMap.containsKey(tool.getName()))
            throw new KeyAlreadyUsedException(tool.getName());
        World.toolMap.put(tool.getName(), tool);
    }

    @Override
    public Tool take(String name) throws ItemNotFoundException {
        Tool ret = World.toolMap.get(name);
        if (ret == null)
            throw new ItemNotFoundException(name);
        if (!tools.contains(name))
            throw new ItemNotFoundException(name);
        return ret;
    }
}
