package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.locations.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NPCDiff extends Diff implements Serializable {
    public NPCDiff(String name) {
        super(name);
    }
    private Collection<String[]>  dialog=null;
    private String room=null;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        NPC npc ;
        try {
            npc= World.npcMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"npc");
        }
        if(description!=null){//Description
            npc.setDescription(getDescription());
        }
        try {
            if (room != null) {
                    npc.changeContainer(room);
            }
            if (dialog != null) {
                    npc.setDialog((ArrayList<String[]>) getDialog());

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        String stringTemp;
        boolean ret=true;
        stringTemp=getRoom();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Raum: %s\n",getRoom());
        string+="Dialog:\n";
        if(getDialog()!=null){
            for (String[] i: getDialog()) {
                try {
                    string += String.format("Frage: %s; Antwort: %s; Event: %s\n", i[0], i[1], i[2]);
                }catch(IndexOutOfBoundsException e){
                    System.err.println("Ungültiger Dialog\n");
                    e.printStackTrace();
                }
            }
        }
        return string;
    }
    public void setRoom(String room)  {
        this.room=room;
    }
    public String getRoom()  {
        return room;
    }
    public void setDialog(Collection<String[]> dialog){
        this.dialog=dialog;
    }
    public Collection<String[]> getDialog(){
       return dialog;
    }
}
