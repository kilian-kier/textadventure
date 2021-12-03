package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.KeyAlreadyUsedException;
import com.textadventure.interfaces.Containable;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Room extends GameElement implements Containable, Serializable {

    private ArrayList<String> exits=new ArrayList<>();
    private ArrayList<String> tools =new ArrayList<>();
    private ArrayList<String> container = new ArrayList<>();
    private ArrayList<String> npcs = new ArrayList<>();
    private String location;
    public Room(String name, String description) {
        super(name);
        this.description = description;
    }

    public java.lang.String getName() {
        return name;
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



    public void addExit(String exit) {
        exits.add(exit);
    }
    public void removeExitIndex(int index) throws IndexOutOfBoundsException{
        exits.remove(index);
    }
    public ArrayList<String> getExits(){
        return this.exits;
    }
    public void removeAllExits(){
        exits.clear();
    }
    public String getExitIndex(int index)throws IndexOutOfBoundsException{
        return exits.get(index);
    }

    public void removeToolsKey(String name) throws IndexOutOfBoundsException{
        tools.remove(name);
    }
    public ArrayList<String> getTools(){
        return this.tools;
    }
    public void removeAllTools(){
        tools.clear();
    }


    public void addContainer(String item) {
        container.add(item);
    }
    public void removeContainerIndex(int index) throws IndexOutOfBoundsException{
        container.remove(index);
    }
    public ArrayList<String> getContainer(){
        return this.container;
    }
    public void removeAllContainer(){
        container.clear();
    }
    public String getContainerIndex(int index) throws IndexOutOfBoundsException{
        return container.get(index);
    }

    public void addNpcs(String item) {
        npcs.add(item);
    }
    public void removeNpcsIndex(int index) throws IndexOutOfBoundsException{
        npcs.remove(index);
    }
    public ArrayList<String> getNpcs(){
        return this.npcs;
    }
    public void removeAllNpcs(){
        npcs.clear();
    }
    public String getNpcsIndex(int index) throws IndexOutOfBoundsException{
        return npcs.get(index);
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
}
