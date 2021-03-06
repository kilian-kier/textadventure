package com.textadventure.Event;

import com.textadventure.help.Help;
import com.textadventure.story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.things.Container;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class ContainerDiff extends ElementDiff implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;
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

        if(interactable!=null){
            container.setInteractable(interactable);
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
    public boolean check(boolean fix) {
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
    public boolean edit() {
            Scanner scanner = new Scanner(System.in);
            LinkedList<String> commands;
            String input;
            while(true) {
                System.out.print("Container Diff " + getName()  + ">> ");
                input = scanner.nextLine();
                commands = Input.splitInput(input);
                if (commands == null) continue;
                switch (commands.get(0)) {
                    case "add":
                        switch(commands.get(1)){
                            case "description":
                                if (Input.getEditor() != null) {
                                    setDescription(Input.edit(getDescription()));
                                } else {
                                    setDescription(Input.input("Beschreibung",false));
                                }
                                System.out.println("Beschreibung hinzugef??gt");
                                break;
                            case "room":
                                if(commands.size()>2){
                                    setRoom(commands.get(2));
                                }else {
                                    setRoom(Input.input("Raum",true));
                                }
                                System.out.println("Raum hinzugef??gt");
                                break;
                            case "addtools":
                                commands.removeFirst();
                                commands.removeFirst();
                                if(commands.isEmpty()){
                                    System.out.println("Zu wenig Parameter");
                                    break;
                                }
                                setAddTools(commands);
                                System.out.println("Addtools hinzugef??gt");
                                break;
                            case "rmtools":
                                commands.removeFirst();
                                commands.removeFirst();
                                if(commands.isEmpty()){
                                    System.out.println("Zu wenig Parameter");
                                    break;
                                }
                                setRmTools(commands);
                                System.out.println("Rmtools hinzugef??gt");
                                break;
                            default:
                                System.out.println("Parameter ung??ltig");
                                break;
                        }
                        break;
                    case "rm":
                        switch(commands.get(1)){
                            case "description":
                                setDescription(null);
                                System.out.println("Beschreibung entfernt");
                                break;
                            case "room":
                                setRoom(null);
                                System.out.println("Raum entfernt");
                                break;
                            case "addtools":
                                setAddTools(null);
                                System.out.println("Addtools entfernt");
                                break;
                            case "rmtools":
                                setRmTools(null);
                                System.out.println("Rmtools entfernt");
                                break;
                            default:
                                System.out.println("Parameter ung??ltig");
                                break;
                        }
                        break;
                    case "show":
                        System.out.println(this);
                        break;
                    case "back":
                        return true;
                    case "help":
                        try{
                            if(commands.size()>1) {
                                System.out.println(Help.help("ContainerDiffEditor", commands.get(1)));
                            }else{
                                System.out.println(Help.help("ContainerDiffEditor", null));
                            }
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Befehl nicht gefunden");
                        break;
                }
            }
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("room")){
            room=map.get("room");
        }
        if(map.containsKey("addtools")){
            addTools=Input.splitInput(map.get("addtools"));
        }
        if(map.containsKey("rmtools")){
            rmTools=Input.splitInput(map.get("rmtools"));
        }
        if(map.containsKey("interactable")){
            try {
                interactable = Boolean.parseBoolean(map.get("interactable"));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
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
