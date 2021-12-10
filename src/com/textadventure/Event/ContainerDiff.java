package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.things.Container;

import java.io.Serializable;
import java.util.Collection;

public class ContainerDiff extends Diff implements Serializable {
    public ContainerDiff(String name){
        super(name);
    }
    private String room=null;
    private Collection<String> addTools =null;
    private Collection<String> rmTools =null;

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Container container ;
        try {
            container= World.containerMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"container");
        }
        if(description!=null) { //Description
            container.setDescription(description);
        }
       if(room!=null){ //Current Room
           try {
               container.changeContainer(room);
           }catch(Exception e){
               System.out.println(e.getMessage());
           }
       }
       try{
       if(addTools!=null){
               for (String i:addTools) {
                   World.toolMap.get(i).changeContainer(this.name);
                   container.addTool(i);
               }
       }
        if(rmTools!=null){
                for (String i:rmTools) {
                    World.toolMap.get(i).setContainer(null);
                    container.removeTool(i);
                }
        }}
        catch(Exception e){
           e.printStackTrace();
        }
       }


    @Override
    public boolean check() {
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
                    System.out.printf("Tool (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
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
        string+=String.format("Raum: %s\n",getRoom());
        string+=String.format("AddTools: %s\n",getAddTools()!=null?getAddTools().toString():null);
        string+=String.format("RmTools: %s",getRmTools()!=null?getRmTools().toString():null);
        return string;
    }

    public void setRoom(String room)  {
        this.room=room;
    }
    public String getRoom()  {
        return room;
    }

    public void setAddTools(Collection<String> tools){
        this.addTools =tools;
    }
    public Collection<String> getAddTools(){
        return addTools;
    }

    public void setRmTools(Collection <String> tools){
        this.rmTools =tools;
    }
    public Collection<String> getRmTools(){
        return rmTools;
    }
}
