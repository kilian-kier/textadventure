package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;

import java.util.Collection;

public class RoomDiff extends Diff{
    public RoomDiff(String name)  {
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {

    }

    public void setLocation(String location)  {
        differences.put("location",location);
    }
    public String getLocation() {
        return (String)differences.get("location");
    }
    public void setExits(Collection<String> exits){
        differences.put("exits",exits);
    }
    public Collection<String> getExits(Collection<String> exits){
        return (Collection<String>)differences.get("exits");
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
    public void setContainer(Collection<String> container){
        differences.put("container",container);
    }
    public Collection<String> getContainer(){
        return (Collection<String>) differences.get("container");
    }
}
