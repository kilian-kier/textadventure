package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;

public class ToolDiff extends Diff{
    public ToolDiff(String name){
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
}
