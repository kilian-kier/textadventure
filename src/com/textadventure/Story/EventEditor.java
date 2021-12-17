package com.textadventure.Story;

import com.textadventure.Event.*;
import com.textadventure.exeptions.ElementExistsException;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Event Editor zum bearbeiten von Events
 */
public class EventEditor {
    public static boolean edit(String name) {
        Event event=null;
        if (name == null) {
            name = Input.input("Name");
        }
        if (World.eventKeyMap.containsKey(name)) {
            event = World.eventMap.get(World.eventKeyMap.get(name));
        } else {
            try {
                event = new Event(name);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
            String access = Input.input("Auslöser");
            event.storeEvent(Input.splitInput(access));
        }

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit){
            System.out.print("Event " + name + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "cmd":
                    commands.removeFirst();
                    event.storeEvent(commands);
                    System.out.println("Befehl wurde geändert");
                    break;
                case "needs": //Abhängigkeiten
                    commands.removeFirst();
                    event.setDependent(commands.isEmpty()?null:commands);
                    System.out.println("Abhängigkeiten gesetzt");
                    break;
                case "info": //Erzähler Text
                    String info = event.getInfo();
                    if (info == null) {
                        info = "";
                    }
                    if (Input.getEditor() != null) {
                        info = Input.edit(info);
                    } else {
                        info = Input.input("Info");
                    }
                    event.setInfo(info);
                    System.out.println("Info hinzugefügt");
                    break;
                case "room": //Raum in dem das Event funktioniert
                    try{
                        event.setRoom(commands.get(1));
                    }catch(IndexOutOfBoundsException e){
                        event.setRoom(Input.input("Raum"));
                    }
                    System.out.println("Raum hinzugefügt");
                case "add": //Diff hinzufügen und editieren, wenn es existiert
                    try{
                        newDiff(commands,event);
                        System.out.println("Neues Diff Hinzugefügt");
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }catch(ElementExistsException e){
                        System.out.println("Diff mit diesem Namen existiert bereits");
                    }catch(Exception e){
                        System.out.println("Unbekannter Diff Typ");
                    }
                    break;
                case "edit":
                    try{
                        event.getDiff(commands.get(1)).edit();
                        System.out.println("Diff bearbeitet");
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }catch(Exception e){
                        System.out.println("Diff existiert nicht");
                    }
                    break;
                case "rm": //Diff entfernen
                    try{
                        event.rmDiff(commands.get(1));
                        System.out.printf("Diff %s gelöscht\n",commands.get(1));
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }catch(ElementNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "show":
                    if(commands.size()>1){
                        try {
                            System.out.println(event.getDiff(commands.get(1)).toString());
                        }catch (Exception e){
                            System.out.println("Diff nicht gefunden");
                        }
                    }else {
                        System.out.println(event.toString());
                    }
                    break;
                case "back":
                    return true;
                case "help" :
                    //TODO help
                    break;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }
        return false;
    }
    private static Diff newDiff(LinkedList<String> args,Event event) throws IndexOutOfBoundsException, ElementExistsException {
        String name ;
        args.get(1);
        if(args.size()>2){
            name = args.get(2);
        }else{
            name=Input.input("Name");
        }
        if(event.getDiff(name)!=null){
            throw new ElementExistsException(name);
        }

        Diff diff=null;
        switch(args.get(1)){
            case "container":
                diff=new ContainerDiff(name);
                break;
            case "exit":
                diff=new ExitDiff(name);
                break;
            case "location":
                diff=new LocationDiff(name);
                break;
            case "npc":
                diff=new NPCDiff(name);
                break;
            case "room":
                diff=new RoomDiff(name);
                break;
            case "tool":
                diff=new ToolDiff(name);
                break;
            default:
                throw new NullPointerException();
        }
        event.addDiff(diff);
        diff.edit();
        return diff;
    }


}