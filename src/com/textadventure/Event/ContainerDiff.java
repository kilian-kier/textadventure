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

        try{ //Description
            container.setDescription(getDescription());
        }catch(Exception e){}
        try{  //CurrentContainer
           container.changeContainer(getRoom());
        }catch(Exception e){}
        try{ //AddTool
            for (String i:getAddTools()) {
                World.toolMap.get(i).changeContainer(this.name);
                container.addTool(i);
            }
        }catch(Exception e){}
        try{  //RemoveTool
            for (String i:getRmTools()) {
                World.toolMap.get(i).setContainer(null);
                container.removeTool(i);
            }
        }catch(Exception e){}
    }

    @Override
    boolean checkValidity() {
        String stringTemp;
        Collection<String> collTemp;
        boolean ret=true;
        stringTemp=getRoom();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        collTemp=getAddTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        return ret;
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
