package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.things.Container;

import java.io.Serializable;

public class Exit extends GameElement implements Serializable {
    private String destination1;
    private String destination2;
    private String room;

    public Exit(String name, String description) {
        super(name);
        this.description = description; //Beschreibung 1 isch links vor an @ und die Beschreibung 2 ich rechts noch an @
    }

    public void changeContainer(String newRoomString) throws ItemNotFoundException, NullPointerException{
        Room newRoom= World.roomMap.get(newRoomString);
        if(this.room!=null) {
            Room oldRoom = World.roomMap.get(room);
            if (oldRoom.getExits().contains(this.name)) {
                oldRoom.getExits().remove(name);
                newRoom.addExit(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newRoom.addExit(this.name);
        }
        this.name=newRoomString;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public String getDestination2() {
        return destination2;
    }

    public void setDestination2(String destination2) {
        this.destination2 = destination2;
    }

    public String getDestination1() {
        return destination1;
    }

    public void setDestination1(String destination1) {
        this.destination1 = destination1;
    }

    @Override
    public void look() {
        //TODO: schaugn wo man isch und donn in ondon Raum ausgeben
        //System.out.println("Da ist ein Ausgang, der nach " + this.destination.getName() + " führt.");
    }

    @Override
    public void investigate() {
        System.out.println(this.description);
    }

    public java.lang.String getName() {
        return this.name;
    }
}
