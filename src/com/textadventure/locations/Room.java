package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Enthält eine Liste mit Ausgängen, Tools, Containern, NPCs und Orten.
 */
public class Room extends GameElement implements Serializable {

    private final ArrayList<String> exits = new ArrayList<>();
    private final Container tools = new Container(this.name, "Dinge, die sich in diesem Raum (" + this.name + ") befinden");
    private final ArrayList<String> container = new ArrayList<>();
    private final ArrayList<String> npcs = new ArrayList<>();
    private String location;

    public Room(String name, String description) {
        super(name);
        this.description = description;
        World.containerMap.put(this.name, tools);
    }

    public java.lang.String getName() {
        return name;
    }


    //TODO: vielleicht amol löschen wenns do Herr Gamper nt braucht
    public void addExit(String exit) {
        exits.add(exit);
    }
    public ArrayList<String> getExits() {
        return this.exits;
    }

    public void removeToolsKey(String name) throws IndexOutOfBoundsException {
        tools.removeTool(name);
    }

    public ArrayList<String> getTools() {
        return this.tools.getTools();
    }
    public Container getToolsContainer() {
        return this.tools;
    }

    public Tool getTool(String name) throws ItemNotFoundException {
        if (tools.getTools().contains(name)) {
            return tools.getTool(name);
        } else
            throw new ItemNotFoundException(name);
    }

    public void addTool(String tool) {
        String str = tool.toLowerCase();
        if(!tools.getTools().contains(str)){
            tools.addTool(str);
        }
    }


    public ArrayList<String> getContainers() {
        return this.container;
    }

    public Container getContainer(String name) throws ItemNotFoundException {
        if (!container.contains(name))
            throw new ItemNotFoundException(name);
        Container ret = World.containerMap.get(name);
        if (ret == null)
            throw new ItemNotFoundException(name);
        return ret;
    }
    public void removeContainer(String string){
        container.remove(string);
    }
    public void addNpcs(String item) {
        if(!npcs.contains(item)){
            npcs.add(item);
        }
    }

    public ArrayList<String> getNpcs() {
        return this.npcs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public void investigate() {
        System.out.println("Ausgänge:");
        for (String exit : exits) {
            System.out.println(exit);
        }
    }

    public void addContainer(String name) {
        String tmp = name.toLowerCase();
        if(!container.contains(tmp)){
            container.add(tmp);
        }
    }

    /**
     * Ändert den ort des Raumes
     * @param newLocationString Neuer Ort
     * @throws ItemNotFoundException Wenn neuer Ort nicht existiert
     * @throws NullPointerException Wenn ungültige Parameter übergeben werden
     */
    public void changeLocation(String newLocationString) throws ItemNotFoundException, NullPointerException{
        Location newLocation= World.locationMap.get(newLocationString);
        if(this.location!=null) {
            Location oldLocation = World.locationMap.get(location);
            if (oldLocation.getRooms().contains(this.name)) {
                oldLocation.getRooms().remove(name);
                newLocation.addRoom(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newLocation.addRoom(this.name);
        }
        this.name=newLocationString;
    }
    
    public boolean check(){
        boolean ret=true;
        if(World.locationMap.get(location)==null){
            System.out.printf("Location %s von Raum %s existiert nicht oder ist noch nicht gesetzt\n",location,name);
            ret=false;
        }else{
            if(!World.locationMap.get(location).getRooms().contains(name)){
                System.out.printf("Location %s wird von Raum %s referenziert aber nicht umgekehrt\n",location,name);
                ret=false;
            }
        }
        for (String tool:tools.getTools()) {
            if(World.toolMap.get(tool)==null){
                System.out.printf("Tool %s von Raum %s existiert nicht\n",tool,name);
                ret=false;
            }else{
                if(!World.toolMap.get(tool).getCurrentContainer().equals(name)){
                    System.out.printf("Tool %s wird von Raum %s referenziert aber nicht umgekehrt\n",tool,name);
                    ret=false;
                }
            }
        }
        for (String cont:container) {
            if(World.containerMap.get(cont)==null){
                System.out.printf("Container %s von Raum %s existiert nicht\n",cont,name);
                ret=false;
            }else{
                if(!World.containerMap.get(cont).getCurrentContainer().equals(name)){
                    System.out.printf("Container %s wird von Raum %s referenziert aber nicht umgekehrt\n",cont,name);
                    ret=false;
                }
            }
        }
        for (String npc:npcs) {
            if(World.npcMap.get(npc)==null){
                System.out.printf("NPC %s von Raum %s existiert nicht\n",npc,name);
                ret=false;
            }else{
                if(!World.npcMap.get(npc).getRoom().equals(name)){
                    System.out.printf("NPC %s wird von Raum %s referenziert aber nicht umgekehrt\n",npc,name);
                    ret=false;
                }
            }
        }
        for (String exit:exits) {
            if(World.exitMap.get(exit)==null){
                System.out.printf("Exit %s von Raum %s existiert nicht\n",exit,name);
                ret=false;
            }else{
                if(!World.exitMap.get(exit).getDestination1().equals(name) && !World.exitMap.get(exit).getDestination2().equals(name)){
                    System.out.printf("Exit %s wird von Raum %s referenziert aber nicht umgekehrt\n",exit,name);
                    ret=false;
                }
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("Raum %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Location: %s\n",getLocation());
        string+=String.format("Tools: %s\n",tools.getTools()!=null?tools.getTools().toString():null);
        string+=String.format("Npcs: %s\n",npcs.toString());
        string+=String.format("Container: %s\n",container.toString());
        string+=String.format("Exits: %s",exits.toString());
        return string;
    }
}
