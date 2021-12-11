package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.locations.Exit;

import java.io.Serializable;

public class ExitDiff extends Diff implements Serializable {
    public ExitDiff(String name){
        super(name);
    }

    private String destination1=null;
    private String destination2= null;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Exit exit;
        try {
            exit = World.exitMap.get(name);
        } catch (Exception e) {
            throw new GameElementNotFoundException(name, "exit");
        }
        if (description != null) { //Description
            exit.setDescription(description);
        }
        try {
            if (destination1 != null) {//Destination1
                    exit.changeDestination(getDestination1(), false);

            }
            if (destination2 != null) {//Destination2
                    exit.changeDestination(getDestination2(), true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        String stringTemp;
        boolean ret=true;
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
        string+=String.format("Destination1: %s\n",getDestination1());
        string+=String.format("Destination2: %s",getDestination2());
        return string;
    }
    public void setDestination1(String destination1)  {
        this.destination1=destination1;
    }
    public String getDestination1()  {
        return destination1;
    }
    public void setDestination2(String destination2)  {
        this.destination2=destination2;
    }
    public String getDestination2()  {
        return destination2;
    }
}
