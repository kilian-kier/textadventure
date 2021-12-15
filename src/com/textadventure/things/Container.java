package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ein Item welches es ermöglicht Tools zu speichern. Tools befinden sich in einer Liste (tools).
 */
public class Container extends Item implements Serializable {
    private final ArrayList<String> tools = new ArrayList<>();


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

    @Override
    public boolean check() {
        boolean ret= super.check();
        for (String tool:tools) {
            if(World.toolMap.get(tool)==null){
                System.out.printf("Tool %s von Container %s existiert nicht\n",tool,name);
                ret=false;
            }else{
                if(!World.toolMap.get(tool).getCurrentContainer().equals(name)){
                    System.out.printf("Tool %s wird von Container %s referenziert aber nicht umgekehrt\n",tool,name);
                    ret=false;
                }
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Container %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Container/Raum: %s\n",currentContainer);
        string+=String.format("Tools: %s",tools);
        return string;
    }

    public Tool getTool(String name) {
        return World.toolMap.get(name);
    }

    public void addTool(String tool) {
        String str = tool.toLowerCase();
        if (!tools.contains(str))
            tools.add(str);
    }

    public ArrayList<String> getTools() {
        return this.tools;
    }

    public void removeAllTools() {
        tools.clear();
    }
    public boolean removeTool(String name) {
        return tools.remove(name);
    }

}
