package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.*;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.util.ArrayList;

public class Player extends GameElement implements RoomChangeable {
    private final Container inventory = new Container("Rucksack", "Das ist dein Rucksack. Hier kannst du alle Dinge finden, die du besitzt.");
    private Room currentRoom;
    private Room previousRoom;

    public Player(String name, String description, Room currentRoom) {
        super(name);
        this.description = description;
        this.currentRoom = currentRoom;
        this.previousRoom = null;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    //TODO: löschen, hons lei in do Main zin testen gebraucht
    public Room getPreviousRoom() {
        return previousRoom;
    }

    @Override
    public void changeRoom(String exit) throws ExitNotFoundException {
        if (!currentRoom.getExits().contains(exit))
            throw new ExitNotFoundException(exit);
        Exit temp = World.exitMap.get(exit);
        if (temp == null)
            throw new ExitNotFoundException(exit);
        if (temp.getDestination1().equals(currentRoom.getName())) {
            Room tmp = currentRoom;
            currentRoom = World.roomMap.get(temp.getDestination2());
            previousRoom = tmp;
            return;
        } else if (temp.getDestination2().equals(currentRoom.getName())) {
            Room tmp = currentRoom;
            currentRoom = World.roomMap.get(temp.getDestination1());
            previousRoom = tmp;
            return;
        }
        throw new ExitNotFoundException(exit);
    }

    @Override
    public void goBack() throws NoBackException {
        if (previousRoom != null) {
            Room temp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = temp;
        } else {
            throw new NoBackException(this.name);
        }
    }

    @Override
    public void investigate() {
        System.out.println("Rucksack:");
        for (String item : inventory.getTools()) {
            System.out.println(item);
        }
    }

    public ArrayList<String> getTools() {
        return this.inventory.getTools();
    }

    public Tool getTool(String name) throws ItemNotFoundException {
        if (inventory.getTools().contains(name)) {
            return inventory.getTool(name);
        } else
            throw new ItemNotFoundException(name);
    }
    public Container getToolsContainer(){
        return this.inventory;
    }

    public void removeAllTools() {
        inventory.removeAllTools();
    }

    public void addTool(String tool) {
        inventory.addTool(tool);
    }
    public void removeTool(String tool) {
        inventory.removeTool(tool);
    }
}
