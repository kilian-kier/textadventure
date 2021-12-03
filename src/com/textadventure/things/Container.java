package com.textadventure.things;

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

    public void addTool(String item) {
        tools.add(item);
    }
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

    }

    @Override
    public Tool take(String name) throws ItemNotFoundException {
        return null;
    }
}
