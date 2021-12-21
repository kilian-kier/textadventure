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
    private static final long serialVersionUID = 7218620408718730599L;
    private final ArrayList<String> tools = new ArrayList<>();


    public Container(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean check() {
        boolean ret=true;
        if(World.roomMap.get(currentContainer)==null){
            System.out.printf("Raum %s von Container %s existiert nicht\n",currentContainer,name);
            ret=false;
        }else{
            if(!World.roomMap.get(currentContainer).getContainers().contains(name)){
                System.out.printf("Raum %s wird von Container %s referenziert aber nicht umgekehrt\n",currentContainer,name);
                ret=false;
            }
        }
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

    /**
     *
     * @param name Name des Tools
     * @return das Objekt des Tools oder null falls es das Tool nicht in diesem Container befindet
     */
    public Tool getTool(String name) {
        return World.toolMap.get(name);
    }

    /**
     * speichert das Tool in kleingeschiebener Form in diesem Container
     * @param tool der Name des zu speichernden Tools
     */
    public void addTool(String tool) {
        String str = tool.toLowerCase();
        if (!tools.contains(str))
            tools.add(str);
    }

    /**
     *
     * @return die Liste aller Tools in diesem Container
     */
    public ArrayList<String> getTools() {
        return this.tools;
    }

    /**
     *
     * @param name Name des zu entfernenden Tools
     * @return true falls das Tool entfernt wurde, false falls das Tool nicht in diesem Container gefunden wurde
     */
    public boolean removeTool(String name) {
        return tools.remove(name);
    }

}
