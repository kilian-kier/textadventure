package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.locations.Location;

import java.io.Serializable;
import java.util.Collection;

public class LocationDiff extends Diff implements Serializable {
    public LocationDiff(String name) {
        super(name);
    }
    private Collection<String> addRooms =null;
    private Collection <String> rmRooms =null;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException  {
        Location location ;
        try {
            location= World.locationMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"location");
        }
        if(description!=null){ //Description
            location.setDescription(getDescription());
        }
        try {
            if (addRooms != null) {
                    for (String i : addRooms) {
                        location.addRoom(i);
                    }

            }
            if (rmRooms != null) {
                    for (String i : rmRooms) {
                        location.getRooms().remove(i);
                    }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
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
        string+=String.format("Räume (Rm): %s",getRmRooms()!=null?getRmRooms().toString():null);
        return string;
    }
    public void setAddRooms(Collection<String> rooms){
        this.addRooms =rooms;
    }
    public Collection<String> getAddRooms(){
        return addRooms;
    }
    public void setRmRooms(Collection<String> rooms){
        this.rmRooms =rooms;
    }
    public Collection<String> getRmRooms(){
        return rmRooms;
    }
}
