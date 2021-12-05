package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.locations.Location;

import java.util.ArrayList;
import java.util.Collection;

public class NPCDiff extends Diff{
    public NPCDiff(String name) {
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {
        NPC npc ;
        try {
            npc= World.npcMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"npc");
        }
        try{ //Description
            npc.setDescription(getDescription());
        }catch(Exception e){}
        try{  //CurrentContainer
            npc.changeContainer(getRoom());
        }catch(Exception e){}
        try{ //Dialog
            npc.setDialog((ArrayList<String[]>) getDialog());
        }catch(Exception e){}
    }

    @Override
    boolean checkValidity() {
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
    public void setRoom(String room)  {
        differences.put("room",room);
    }
    public String getRoom()  {
        return (String)differences.get("room");
    }
    public void setDialog(Collection<String[]> dialog){
        differences.put("dialog",dialog);
    }
    public Collection<String[]> getDialog(){
        return (Collection<String[]>)differences.get("dialog");
    }
}
