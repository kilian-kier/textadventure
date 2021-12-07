package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.locations.Exit;
import com.textadventure.things.Container;

import java.util.Collection;

public class ExitDiff extends Diff{
    public ExitDiff(String name){
        super(name);
    }

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Exit exit ;
        try {
            exit= World.exitMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"exit");
        }
        try{ //Description
            exit.setDescription(getDescription());
        }catch(Exception e){}
        try{  //CurrentContainer
            exit.changeContainer(getRoom());
        }catch(Exception e){}
        try{ //Destination1
            exit.setDestination1(getDestination1());
        }catch(Exception e){}
        try{ //Destination1
            exit.setDestination2(getDestination2());
        }catch(Exception e){}
    }

    @Override
    public boolean checkValidity() {
        String stringTemp;
        boolean ret=true;
        stringTemp=getRoom();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        stringTemp=getDestination1();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum (Destination 1) %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        stringTemp=getDestination2();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum (Destination 2) %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
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
        string+=String.format("Destination1: %s\n",getDestination1());
        string+=String.format("Destination2: %s\n",getDestination2());
        return string;
    }
    public void setRoom(String room)  {
        differences.put("room",room);
    }
    public String getRoom()  {
        return (String)differences.get("room");
    }
    public void setDestination1(String destination1)  {
        differences.put("destination1",destination1);
    }
    public String getDestination1()  {
        return (String)differences.get("destination1");
    }
    public void setDestination2(String destination2)  {
        differences.put("destination2",destination2);
    }
    public String getDestination2()  {
        return (String)differences.get("destination2");
    }
}
