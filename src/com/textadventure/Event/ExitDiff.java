package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;

public class ExitDiff extends Diff{
    public ExitDiff(String name){
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {

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
