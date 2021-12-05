package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.locations.Exit;
import com.textadventure.things.Container;

public class ExitDiff extends Diff{
    public ExitDiff(String name){
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {
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
    boolean checkValidity() {
        return false;
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
