package com.textadventure.Event;

import com.textadventure.help.Help;
import com.textadventure.story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Location;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class LocationDiff extends Diff implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;
    public LocationDiff(String name) {
        super(name);
    }
    private Collection<String> addRooms =null;
    private Collection <String> rmRooms =null;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException  {
        Location location ;
        try {
            location= World.locationMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"location");
        }
        if(description!=null){ //Description
            location.setDescription(getDescription());
        }
        try {
            if (addRooms != null) {
                    for (String i : addRooms) {
                        location.addRoom(i);
                    }

            }
            if (rmRooms != null) {
                    for (String i : rmRooms) {
                        location.getRooms().remove(i);
                    }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        Collection<String> collTemp;
        boolean ret=true;
        collTemp=getAddRooms();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.roomMap.get(i)==null){
                    System.out.printf("Raum (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmRooms();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.roomMap.get(i)==null){
                    System.out.printf("Raum (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        return ret;
    }
    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("addrooms")){
            addRooms=Input.splitInput(map.get("addrooms"));
        }
        if(map.containsKey("rmrooms")){
            rmRooms=Input.splitInput(map.get("rmrooms"));
        }
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Räume (Add): %s\n",getAddRooms()!=null?getAddRooms().toString():null);
        string+=String.format("Räume (Rm): %s",getRmRooms()!=null?getRmRooms().toString():null);
        return string;
    }
    public void setAddRooms(Collection<String> rooms){
        this.addRooms =rooms;
    }
    public Collection<String> getAddRooms(){
        return addRooms;
    }
    public void setRmRooms(Collection<String> rooms){
        this.rmRooms =rooms;
    }
    public Collection<String> getRmRooms(){
        return rmRooms;
    }



    @Override
    public void edit() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Location Diff " + getName()  + ">> ");
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
                            System.out.println("Beschreibung hinzugefügt");
                            break;
                        case "addrooms":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setAddRooms(commands);
                            System.out.println("Addrooms hinzugefügt");
                            break;
                        case "rmrooms":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setRmRooms(commands);
                            System.out.println("Rmrooms hinzugefügt");
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
                        case "addrooms":
                            setAddRooms(null);
                            System.out.println("Addrooms entfernt");
                            break;
                        case "rmrooms":
                            setRmRooms(null);
                            System.out.println("Rmrooms entfernt");
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
                            System.out.println(Help.help("LocationDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("LocationDiffEditor", null));
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
}
