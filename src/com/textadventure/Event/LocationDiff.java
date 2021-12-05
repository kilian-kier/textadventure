package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;

import java.util.ArrayList;
import java.util.Collection;

public class LocationDiff extends Diff{
    public LocationDiff(String name) {
        super(name);
    }

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Location location ;
        try {
            location= World.locationMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"location");
        }
        try{ //Description
            location.setDescription(getDescription());
        }catch(Exception e){}
        try{ //AddRooms
            Collection<String> addrooms= getAddRooms();
            for (String i:addrooms) {
                location.addRoom(i);
            }
        }catch(Exception e){}
        try{ //Remove Rooms
            Collection<String> rmrooms= getRmRooms();
            for (String i:rmrooms) {
                location.getRooms().remove(i);
            }
        }catch(Exception e){}
    }

    @Override
    public boolean checkValidity() {
        Collection<String> collTemp;
        boolean ret=true;
        collTemp=getAddRooms();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.roomMap.get(i)==null){
                    System.out.printf("Raum (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmRooms();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.roomMap.get(i)==null){
                    System.out.printf("Raum (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
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
        string+=String.format("Räume (Add): %s\n",getAddRooms()!=null?getAddRooms().toString():null);
        string+=String.format("Räume (Rm): %s\n",getRmRooms()!=null?getRmRooms().toString():null);
        return string;
    }
    public void setAddRooms(Collection<String> rooms){
        differences.put("addrooms",rooms);
    }
    public Collection<String> getAddRooms(){
        return (Collection<String>) differences.get("addrooms");
    }
    public void setRmRooms(Collection<String> rooms){
        differences.put("rmrooms",rooms);
    }
    public Collection<String> getRmRooms(){
        return (Collection<String>) differences.get("rmrooms");
    }
}
