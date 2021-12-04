package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.things.Container;

import java.util.Collection;

public class ContainerDiff extends Diff{
    public ContainerDiff(String name){
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {
        Container container ;
        try {
            container= World.containerMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"container");
        }
        try{
            container.setDescription(getDescription());
        }catch(Exception e){}
        try{
           container.changeContainer(getRoom());
        }catch(Exception e){}
        try{
            for (String i:getAddTools()) {
                World.toolMap.get("i").changeContainer(this.name);
                container.addTool(i);
            }
        }catch(Exception e){}
        try{
            for (String i:getRmTools()) {
                World.toolMap.get("i").setContainer(null);
                container.removeTool(i);
            }
        }catch(Exception e){}
    }

    public void setRoom(String room)  {
        differences.put("room",room);
    }
    public String getRoom()  {
        return (String)differences.get("room");
    }
    public void setAddTools(Collection<String> tools){
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
}
