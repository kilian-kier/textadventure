package com.textadventure.things;

import com.textadventure.Story.World;

import java.io.Serializable;
import java.util.ArrayList;

public class Container extends Item implements Serializable {
    ArrayList<String> tools = new ArrayList<>();


    public Container(String name, String description) {
        super(name, description);
    }

    //TODO: vielleicht amol löschen wenns do Herr Gamper nt braucht
    public void removeToolsIndex(int index) throws IndexOutOfBoundsException {
        tools.remove(index);
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

    public String getToolIndex(int index) throws IndexOutOfBoundsException {
        return tools.get(index);
    }

    public String removeTool(String name) {
        tools.remove(name);
        return name;
    }
}
