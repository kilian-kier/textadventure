package com.textadventure.things;

import com.textadventure.interfaces.Containable;

import java.io.Serializable;
import java.util.ArrayList;

public class Container extends Item  implements Serializable {
    ArrayList<Tool> tools = new ArrayList<>();
    public Container(String name, String description) {
        super(name, description);
    }

    public void addTool(Tool item) {
        tools.add(item);
    }
    public void removeToolsIndex(int index) throws IndexOutOfBoundsException{
        tools.remove(index);
    }
    public ArrayList<Tool> getTools(){
        return this.tools;
    }
    public void removeAllTools(){
        tools.clear();
    }
    public Tool getToolIndex(int index) throws IndexOutOfBoundsException{
        return tools.get(index);
    }
}
