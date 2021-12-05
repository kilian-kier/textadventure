package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
        if(!tools.getTools().contains(tool)){
            tools.addTool(tool);
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



    public String getContainerKey(String string){
        return container.get(container.indexOf(string));
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
        if(!container.contains(name)){
            container.add(name);
        }
    }
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
}
