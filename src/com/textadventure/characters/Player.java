package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.KeyAlreadyUsedException;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.Containable;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Tool;

import java.util.ArrayList;

public class Player extends GameElement implements Containable, RoomChangeable {
    public Room currentRoom;
    ArrayList<String> tools = new ArrayList<>();
    private Room previousRoom;

    public Player(String name, String description, Room currentRoom) {
        super(name);
        this.description = description;
        this.currentRoom = currentRoom;
        this.previousRoom = null;
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
        System.out.println("Inventar:");
        for (String item : tools) {
            System.out.println(item);
        }
    }

    @Override
    public void put(Tool tool) throws KeyAlreadyUsedException {
        if (tools.contains(tool.getName()))
            throw new KeyAlreadyUsedException(tool.getName());
        if (World.toolMap.containsKey(tool.getName()))
            throw new KeyAlreadyUsedException(tool.getName());
        World.toolMap.put(tool.getName(), tool);
        tool.setContainer(this.name);
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
}
