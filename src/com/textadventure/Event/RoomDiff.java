package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;

import java.util.Collection;

public class RoomDiff extends Diff{
    public RoomDiff(String name)  {
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {
        Room room ;
        try {
            room= World.roomMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"room");
        }
        try{ //Description
            room.setDescription(getDescription());
        }catch(Exception e){}
        try{ //AddTool
            for (String i:getAddTools()) {
                World.toolMap.get(i).changeContainer(this.name);
                room.addTool(i);
            }
        }catch(Exception e){}
        try{  //RemoveTool
            for (String i:getRmTools()) {
                World.toolMap.get(i).setContainer(null);
                room.getTools().remove(i);
            }
        }catch(Exception e){}
        try{ //AddExits
            for (String i:getAddExits()) {
                World.exitMap.get(i).changeContainer(this.name);
                room.addExit(i);
            }
        }catch(Exception e){}
        try{  //RemoveExits
            for (String i:getRmExits()) {
                World.exitMap.get(i).setRoom(null);
                room.getTools().remove(i);
            }
        }catch(Exception e){}
        try{ //AddContainer
            for (String i:getAddContainer()) {
                World.containerMap.get(i).changeContainer(this.name);
                room.addContainer(i);
            }
        }catch(Exception e){}
        try{  //RemoveContainer
            for (String i:getRmContainer()) {
                World.containerMap.get(i).changeContainer(null);
                room.getContainers().remove(i);
            }
        }catch(Exception e){}
        try{ //AddNpcs
            for (String i:getAddNpcs()) {
                World.npcMap.get(i).changeContainer(this.name);
                room.addNpcs(i);
            }
        }catch(Exception e){}
        try{  //RemoveNpcs
            for (String i:getRmNpcs()) {
                World.containerMap.get(i).changeContainer(null);
                room.getNpcs().remove(i);
            }
        }catch(Exception e){}
        try{ //Change Location
            room.changeLocation(getLocation());
        }catch(Exception e){}
    }

    @Override
    boolean checkValidity() {
        return false;
    }

    public void setLocation(String location)  {
        differences.put("location",location);
    }
    public String getLocation() {
        return (String)differences.get("location");
    }
    public void setAddExits(Collection<String> exits){
        differences.put("addexits",exits);
    }
    public Collection<String> getAddExits(){
        return (Collection<String>)differences.get("addexits");
    }
    public void setRmExits(Collection<String> exits){
        differences.put("rmexits",exits);
    }
    public Collection<String> getRmExits(){
        return (Collection<String>)differences.get("rmexits");
    }
    public void setAddTools(Collection <String> tools){
        differences.put("addtools",tools);
    }
    public Collection<String> getAddTools(){
        return (Collection<String>) differences.get("addtools");
    }
    public void setRmTools(Collection <String> tools){
        differences.put("romtools",tools);
    }
    public Collection<String> getRmTools(){
        return (Collection<String>) differences.get("rmtools");
    }
    public void setAddContainer(Collection<String> container){
        differences.put("addcontainer",container);
    }
    public Collection<String> getAddContainer(){
        return (Collection<String>) differences.get("addcontainer");
    }
    public void setRmContainer(Collection<String> container){
        differences.put("rmcontainer",container);
    }
    public Collection<String> getRmContainer(){
        return (Collection<String>) differences.get("rmcontainer");
    }
    public void setAddNpcs(Collection<String> npcs){
        differences.put("addnpcs",npcs);
    }
    public Collection<String> getAddNpcs(){
        return (Collection<String>) differences.get("addnpcs");
    }
    public void setRmNpcs(Collection<String> npcs){
        differences.put("rmnpcs",npcs);
    }
    public Collection<String> getRmNpcs(){
        return (Collection<String>) differences.get("rmnpcs");
    }
}
