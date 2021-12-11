package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

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


    @Override
    public void edit() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
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
                                setDescription(Input.input("Beschreibung"));
                            }
                            System.out.println("Beschreibung hinzugefügt");
                            break;
                        case "destination1":
                            if(commands.size()>2){
                                setDestination1(commands.get(2));
                            }else {
                                setDestination1(Input.input("Destination1"));
                            }
                            System.out.println("Destination1 hinzugefügt");
                            break;
                        case "destination2":
                            if(commands.size()>2){
                                setDestination2(commands.get(2));
                            }else {
                                setDestination2(Input.input("Destination2"));
                            }
                            System.out.println("Destination2 hinzugefügt");
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
                        case "destination1":
                            setDestination1(null);
                            System.out.println("Raum entfernt");
                            break;
                        case "destination2":
                            setDestination2(null);
                            System.out.println("Raum entfernt");
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
                    //TODO help
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }

    }
}
