package com.textadventure.things;

import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.locations.Room;
import com.textadventure.story.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Ein Item welches es ermöglicht Tools zu speichern. Tools befinden sich in einer Liste (tools).
 */
public class Container extends Item implements Serializable, Editable {
    private static final long serialVersionUID = 7218620408718730599L;
    private final ArrayList<String> tools = new ArrayList<>();


    public Container(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean check(boolean fix) {
        boolean ret=true;
        if(World.roomMap.get(currentContainer)==null){
            System.out.printf("Raum %s von Container %s existiert nicht\n",currentContainer,name);
            ret=false;
        }else{
            if(!World.roomMap.get(currentContainer).getContainers().contains(name)){
                System.out.printf("Raum %s wird von Container %s referenziert aber nicht umgekehrt\n",currentContainer,name);
                if(fix){
                    World.roomMap.get(currentContainer).addContainer(name);
                    System.out.println("Fehler ausgebessert");
                }
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
                    if(fix){
                        World.toolMap.get(tool).setContainer(name);
                        System.out.println("Fehler ausgebessert");
                    }
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
        string+=String.format("Interactable: %b\n",interactable);
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

    @Override /**
     * Ändert den Container in dem sich ein Item befindet.
     * @param newContainerString neuer Container
     * @throws ItemNotFoundException Wenn neuer Container nicht existiert
     * @throws NullPointerException Wenn ein ungültiger Parameter übergeben wird.
     */
    public void changeContainer(String newContainerString) throws ItemNotFoundException , NullPointerException{
        if(newContainerString!=null){
            Room newContainer=World.roomMap.get(newContainerString);

            if(this.currentContainer!=null) {
                Room oldContainer = World.roomMap.get(this.currentContainer);
                if (oldContainer.getContainers().contains(this.name)) {
                    oldContainer.removeContainer(this.name);
                    newContainer.addContainer(this.name);
                } else {
                    throw new ItemNotFoundException(name);
                }
            }else{
                newContainer.addContainer(this.name);
            }
        }else{
            if(this.currentContainer!=null) {
                Room oldContainer = World.roomMap.get(this.currentContainer);
                if (oldContainer.getContainers().contains(this.name)) {
                    oldContainer.removeContainer(this.name);
                } else {
                    throw new ItemNotFoundException(name);
                }
            }
        }
        this.currentContainer=newContainerString;
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
        if(map.containsKey("room")){
            currentContainer=map.get("room");
        }
    }

    /**
     * Mit dieser Methode können Beschreibung und Raum eines Containers geändert werden. Zudem können Objekte der Klasse Tools zum Container hinzugefügt bzw. entfernt werden
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Container " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("ContainerEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("ContainerEditor", null));
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "back":
                        return true;
                    case "show":
                        System.out.println(this);
                        break;
                    case "add":
                        command.removeFirst();
                        for (String x : command) {
                            if (World.toolMap.containsKey(x)) this.addTool(x);
                            else System.out.println(x + " nicht gefunden");
                        }
                        break;
                    case "rm":
                        command.removeFirst();
                        for (String x : command) {
                            if (!this.tools.remove(x)) System.out.println(x + " nicht gefunden!");
                        }
                        break;
                    case "set":
                        switch (command.get(1)) {
                            case "description": this.description = Input.input("description", false); break;
                            case "room": {
                                if (World.roomMap.containsKey(command.get(2))) this.currentContainer = command.get(2);
                                else System.out.println(command.get(2) + "nicht gefunden");
                            }break;
                       
                            case "interactable" : {
                                this.setInteractable(!this.isInteractable());
                                System.out.printf("Interactable wurde auf %b gesetzt\n", this.isInteractable());
                            }break;
                            default : System.out.println("command not found");
                        }
                        break;
                    default:
                        System.out.println("command not found");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    Help.help("ContainerEditor", command.get(0));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }
}
