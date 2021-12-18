package com.textadventure.Event;

import com.textadventure.Story.Help;
import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.input.Input;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class RoomDiff extends Diff implements Serializable {
    public RoomDiff(String name)  {
        super(name);
    }
    String location=null;
    Collection<String> addTools =null;
    Collection<String> rmTools =null;
    Collection<String> addContainer =null;
    Collection<String> rmContainer =null;
    Collection<String> addNPCs =null;
    Collection<String> rmNPCs =null;



    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Room room;
        try {
            room = World.roomMap.get(name);
        } catch (Exception e) {
            throw new GameElementNotFoundException(name, "room");
        }
        if (description != null) { //Description
            room.setDescription(getDescription());
        }
        try{
            if (addTools != null) {
                for (String i : addTools) {
                    World.toolMap.get(i).changeContainer(this.name);
                    room.addTool(i);
                }
            }
            if (rmTools != null) {
                for (String i : rmTools) {
                    World.toolMap.get(i).setContainer(null);
                    room.getToolsContainer().removeTool(i);
                }
            }
            if (addContainer != null) {
                for (String i : addContainer) {
                    World.containerMap.get(i).changeContainer(this.name);
                    room.addContainer(i);
                }
            }
            if (rmContainer!=null){
                    for (String i : rmContainer) {
                        World.containerMap.get(i).changeContainer(null);
                        room.getContainers().remove(i);
                    }
                }
           if(addNPCs!=null){
                for (String i : addNPCs) {
                    World.npcMap.get(i).changeContainer(this.name);
                    room.addNpcs(i);
                }
            }
            if(rmNPCs!=null){
                for (String i : rmNPCs) {
                    World.containerMap.get(i).changeContainer(null);
                    room.getNpcs().remove(i);
                }
            }
            if(location!=null){
                room.changeLocation(getLocation());
            }
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public boolean check() {
        String stringTemp;
        Collection<String> collTemp;
        boolean ret=true;
        stringTemp=getLocation();
        if(stringTemp!=null){
            if(World.locationMap.get(stringTemp)==null){
                System.out.printf("Location %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
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
        collTemp=getAddContainer();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.containerMap.get(i)==null){
                    System.out.printf("Container (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmContainer();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.containerMap.get(i)==null){
                    System.out.printf("Container (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getAddNpcs();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.npcMap.get(i)==null){
                    System.out.printf("Npc (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmNpcs();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.npcMap.get(i)==null){
                    System.out.printf("Npc (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        return ret;
    }

    @Override
    public void edit() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Raum Diff " + getName()  + ">> ");
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
                                setDescription(Input.input("Beschreibung"));
                            }
                            System.out.println("Beschreibung hinzugefügt");
                            break;
                        case "location":
                            if(commands.size()>2){
                                setLocation(commands.get(2));
                            }else {
                                setLocation(Input.input("Ort"));
                            }
                            System.out.println("Ort hinzugefügt");
                            break;
                        case "addtools":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setAddTools(commands);
                            System.out.println("Addtools hinzugefügt");
                            break;
                        case "rmtools":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setRmTools(commands);
                            System.out.println("Rmtools hinzugefügt");
                            break;
                        case "addcontainer":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setAddContainer(commands);
                            System.out.println("Addcontainer hinzugefügt");
                            break;
                        case "rmcontainer":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setRmContainer(commands);
                            System.out.println("Rmcontainer hinzugefügt");
                            break;
                        case "addnpcs":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setAddNpcs(commands);
                            System.out.println("AddNPCs hinzugefügt");
                            break;
                        case "rmnpcs":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setRmNpcs(commands);
                            System.out.println("RmNPCs hinzugefügt");
                            break;    
                        default:
                            System.out.println("Parameter ungültig");
                            break;
                    }
                    break;
                case "rm":
                    switch(commands.get(1)){
                        case "description":
                            setDescription(null);
                            System.out.println("Beschreibung entfernt");
                            break;
                        case "location":
                            setLocation(null);
                            System.out.println("Ort entfernt");
                            break;
                        case "addtools":
                            setAddTools(null);
                            System.out.println("Addtools entfernt");
                            break;
                        case "rmtools":
                            setRmTools(null);
                            System.out.println("Rmtools entfernt");
                            break;
                        case "addcontainer":
                            setAddContainer(null);
                            System.out.println("Addcontainer entfernt");
                            break;
                        case "rmcontainer":
                            setRmContainer(null);
                            System.out.println("Rmcontainer entfernt");
                            break;
                        case "addnpcs":
                            setAddNpcs(null);
                            System.out.println("AddNPCs entfernt");
                            break;
                        case "rmnpcs":
                            setRmNpcs(null);
                            System.out.println("RmNPCs entfernt");
                            break;
                        default:
                            System.out.println("Parameter ungültig");
                            break;
                    }
                    break;
                case "show":
                    System.out.println(this.toString());
                    break;
                case "back":
                    return;
                case "help":
                    try{
                        if(commands.size()>1) {
                            System.out.println(Help.help("RoomDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("RoomDiffEditor", null));
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
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Location: %s\n",getLocation());
        string+=String.format("AddTools: %s\n",getAddTools()!=null?getAddTools().toString():null);
        string+=String.format("RmTools: %s\n",getRmTools()!=null?getRmTools().toString():null);
        string+=String.format("AddNpcs: %s\n",getAddNpcs()!=null?getAddNpcs().toString():null);
        string+=String.format("RmNpcs: %s\n",getRmNpcs()!=null?getRmNpcs().toString():null);
        string+=String.format("AddContainer: %s\n",getAddContainer()!=null?getAddContainer().toString():null);
        string+=String.format("RmContainer: %s",getRmContainer()!=null?getRmContainer().toString():null);
        return string;
    }
    public void setLocation(String location)  {
        this.location=location;
    }
    public String getLocation() {
        return location;
    }
    public void setAddTools(Collection <String> tools){
        this.addTools=tools;
    }
    public Collection<String> getAddTools(){
        return addTools;
    }
    public void setRmTools(Collection <String> tools){
        this.rmTools=tools;
    }
    public Collection<String> getRmTools(){
       return rmTools;
    }
    public void setAddContainer(Collection<String> container){
        this.addContainer=container;
    }
    public Collection<String> getAddContainer(){
        return addContainer;
    }
    public void setRmContainer(Collection<String> container){
        this.rmContainer=container;
    }
    public Collection<String> getRmContainer(){
        return rmContainer;
    }
    public void setAddNpcs(Collection<String> npcs){
        this.addNPCs=npcs;
    }
    public Collection<String> getAddNpcs(){
        return addNPCs;
    }
    public void setRmNpcs(Collection<String> npcs){
       this.rmNPCs=npcs;
    }
    public Collection<String> getRmNpcs(){
        return rmNPCs;
    }
}
