package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.locations.Room;

import java.io.Serializable;
import java.util.HashMap;

/**
 *  Nicht Spieler Charakter im Spiel verfügt über Dialoge (dialog) und befindet sich in einem Raum (room).
 */
public class NPC extends GameElement implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;
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
        if(newRoomString!=null) {
            Room newRoom = World.roomMap.get(newRoomString);
            if (room != null) {
                Room oldRoom = World.roomMap.get(room);
                if (oldRoom.getNpcs().contains(this.name)) {
                    oldRoom.getNpcs().remove(name);
                    newRoom.addNpcs(this.name);
                } else {
                    throw new ItemNotFoundException(name);
                }
            } else {
                newRoom.addNpcs(this.name);
            }
        }else{
            if (room != null) {
                Room oldRoom = World.roomMap.get(room);
                if (oldRoom.getNpcs().contains(this.name)) {
                    oldRoom.getNpcs().remove(name);
                } else {
                    throw new ItemNotFoundException(name);
                }

        }
        }
        this.room=newRoomString;
    }

    public boolean check(boolean fix){
        boolean ret=true;
        if(World.roomMap.get(room)==null){
            System.out.printf("Raum %s von NPC %s existiert nicht\n",room,name);
            ret=false;
        }else{
            if(!World.roomMap.get(room).getNpcs().contains(name)){
                System.out.printf("Raum %s wird von NPC %s referenziert aber nicht umgekehrt\n",room,name);
                if(fix){
                    World.roomMap.get(room).addNpcs(name);
                    System.out.println("Fehler ausgebessert");
                }
                ret=false;
            }
        }
        dialog.check(fix);
        return ret;
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("NPC %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Interactable: %b\n",interactable);
        string+=String.format("Raum: %s\n",room);
        string+="Dialog:";
        string+=dialog.toString();
        return string;
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
        if(map.containsKey("room")){
            room=map.get("room");
        }
        if(map.containsKey("dialog")){
            try {
                dialog.loadFromHashMap(LoadStoreWorld.createMap(map.get("dialog")));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
