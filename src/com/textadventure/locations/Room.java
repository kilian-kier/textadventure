package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.interfaces.Containable;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.util.ArrayList;

public class Room extends GameElement implements Containable, Serializable {

    private ArrayList<Exit> exits=new ArrayList<>();
    private ArrayList<Tool> tools =new ArrayList<>();
    private ArrayList<Container> container = new ArrayList<>();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private Location location;
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }



    @Override
    public void put(Tool tool) {

    }

    @Override
    public Tool take(String name) {
        return null;
    }



    public void addExit(Exit exit) {
        exits.add(exit);
    }
    public void removeExitIndex(int index) throws IndexOutOfBoundsException{
        exits.remove(index);
    }
    public ArrayList<Exit> getExits(){
        return this.exits;
    }
    public void removeAllExits(){
        exits.clear();
    }
    public Exit getExitIndex(int index)throws IndexOutOfBoundsException{
        return exits.get(index);
    }

    public void addTool(Tool tool) {
        tools.add(tool);
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

    public void addContainer(Container container) {
        this.container.add(container);
    }
    public void removeContainerIndex(int index) throws IndexOutOfBoundsException{
        container.remove(index);
    }
    public ArrayList<Container> getContainer(){
        return this.container;
    }
    public void removeAllContainer(){
        container.clear();
    }
    public Container getContainerIndex(int index) throws IndexOutOfBoundsException{
        return container.get(index);
    }

    public void addNpcs(NPC npc) {
        npcs.add(npc);
    }
    public void removeNpcsIndex(int index) throws IndexOutOfBoundsException{
        npcs.remove(index);
    }
    public ArrayList<NPC> getNpcs(){
        return this.npcs;
    }
    public void removeAllNpcs(){
        npcs.clear();
    }
    public NPC getNpcsIndex(int index) throws IndexOutOfBoundsException{
        return npcs.get(index);
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }


}
