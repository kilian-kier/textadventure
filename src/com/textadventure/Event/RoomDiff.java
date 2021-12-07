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
    public void applyDiffToWorld() throws GameElementNotFoundException {
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
    public boolean checkValidity() {
        String stringTemp;
        Collection<String> collTemp;
        boolean ret=true;
        stringTemp=getLocation();
        if(stringTemp!=null){
            if(World.locationMap.get(stringTemp)==null){
                System.out.printf("Location %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        collTemp=getAddTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getAddExits();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.exitMap.get(i)==null){
                    System.out.printf("Exit (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmExits();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.exitMap.get(i)==null){
                    System.out.printf("Exit (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getAddContainer();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.containerMap.get(i)==null){
                    System.out.printf("Container (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmContainer();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.containerMap.get(i)==null){
                    System.out.printf("Container (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getAddNpcs();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.npcMap.get(i)==null){
                    System.out.printf("Npc (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmNpcs();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.npcMap.get(i)==null){
                    System.out.printf("Npc (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Location: %s\n",getLocation());
        string+=String.format("AddTools: %s\n",getAddTools()!=null?getAddTools().toString():null);
        string+=String.format("RmTools: %s\n",getRmTools()!=null?getRmTools().toString():null);
        string+=String.format("AddNpcs: %s\n",getAddNpcs()!=null?getAddNpcs().toString():null);
        string+=String.format("RmNpcs: %s\n",getRmNpcs()!=null?getRmNpcs().toString():null);
        string+=String.format("AddContainer: %s\n",getAddContainer()!=null?getAddContainer().toString():null);
        string+=String.format("RmContainer: %s\n",getRmContainer()!=null?getRmContainer().toString():null);
        string+=String.format("AddExits: %s\n",getAddExits()!=null?getAddExits().toString():null);
        string+=String.format("RmExits: %s\n",getRmExits()!=null?getRmExits().toString():null);
        return string;
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
