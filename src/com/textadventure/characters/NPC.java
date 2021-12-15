package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.locations.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *  Nicht Spieler Charakter im Spiel verfügt über Dialoge (dialog) und befindet sich in einem Raum (room).
 */
public class NPC extends GameElement implements Serializable {
    private Dialog dialog = new Dialog();
    private String room;

    public NPC(String name, String description) {
        super(name);
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public void addDialog(String[] dialog) {
        this.dialog.getDialog().add(dialog);
    }
    public void setDialog(Dialog dialog){
        this.dialog=dialog;
    }

    public Dialog getDialog() {
        return this.dialog;
    }

    public void removeDialogIndex(int index) throws IndexOutOfBoundsException {
        dialog.getDialog().remove(index);
    }

    public String[] getDialogIndex(int index) throws IndexOutOfBoundsException {
        return dialog.getDialog().get(index);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    public void changeContainer(String newRoomString) throws ItemNotFoundException, NullPointerException{
        Room newRoom= World.roomMap.get(newRoomString);
        if(this.room!=null) {
            Room oldRoom = World.roomMap.get(room);
            if (oldRoom.getNpcs().contains(this.name)) {
                oldRoom.getNpcs().remove(name);
                newRoom.addNpcs(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newRoom.addNpcs(this.name);
        }
        this.name=newRoomString;
    }

    public boolean check(){
        boolean ret=true;
        if(World.roomMap.get(room)==null){
            System.out.printf("Raum %s von NPC %s existiert nicht\n",room,name);
            ret=false;
        }else{
            if(!World.roomMap.get(room).getNpcs().contains(name)){
                System.out.printf("Raum %s wird von NPC %s referenziert aber nicht umgekehrt\n",room,name);
                ret=false;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("NPC %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Raum: %s\n",room);
        string+="Dialog:";
        string+=dialog.toString();
        return string;
    }
}
