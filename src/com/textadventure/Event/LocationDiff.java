package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;

import java.util.Collection;

public class LocationDiff extends Diff{
    public LocationDiff(String name) {
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {

    }

    public void setRooms(Collection<String> rooms)  {
        differences.put("rooms",rooms);
    }
    public Collection<String> getRooms() {
        return (Collection<String>) differences.get("rooms");
    }


}
