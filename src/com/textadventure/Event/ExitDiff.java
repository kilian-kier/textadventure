package com.textadventure.Event;

import com.textadventure.help.Help;
import com.textadventure.story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class ExitDiff extends ElementDiff implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;
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
        if(interactable!=null){
            exit.setInteractable(interactable);
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
    public boolean check(boolean fix) {
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
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("destination1")){
            destination1=map.get("destination1");
        }
        if(map.containsKey("destination2")){
            destination2=map.get("destination2");
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
        string+=String.format("Interactable: %b\n",interactable);
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
    public void setDescription1(String description1){
        description = description1+"@"+getDescription2();
    }
    public void setDescription2(String description2){
        description = getDescription1()+"@"+description2;
    }
    public String getDescription1(){
        try {
            return description.split("[@]")[0];
        }catch(Exception e){
            return description;
        }
    }
    public String getDescription2(){
        try {
            return description.split("[@]")[1];
        }catch(Exception e){
            return description;
        }
    }

    @Override
    public boolean edit() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(true) {
            System.out.print("Exit Diff " + getName()  + ">> ");
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
                        case "destination1":
                            if(commands.size()>2){
                                setDestination1(commands.get(2));
                            }else {
                                setDestination1(Input.input("Destination1",true));
                            }
                            System.out.println("Destination1 hinzugef??gt");
                            break;
                        case "destination2":
                            if(commands.size()>2){
                                setDestination2(commands.get(2));
                            }else {
                                setDestination2(Input.input("Destination2",true));
                            }
                            System.out.println("Destination2 hinzugef??gt");
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
                        case "destination1":
                            setDestination1(null);
                            System.out.println("Raum entfernt");
                            break;
                        case "destination2":
                            setDestination2(null);
                            System.out.println("Raum entfernt");
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
                            System.out.println(Help.help("ExitDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("ExitDiffEditor", null));
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
