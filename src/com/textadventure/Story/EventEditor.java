package com.textadventure.Story;

import com.textadventure.Event.*;
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
    public static void edit(String name) {
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
                return;
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
                case "needs": //Abhängigkeiten
                    commands.removeFirst();
                    event.setDependent(commands);
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
                    System.out.println(info);
                    event.setInfo(info);
                case "add": //Diff hinzufügen und editieren, wenn es existiert
                    try{
                        event.addDiff(newDiff(commands));
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }
                case "edit":
                    break;


                case "remove": //Diff entfernen
                    try{
                        event.rmDiff(commands.get(1));
                        System.out.printf("Diff %s gelöscht",commands.get(1));
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }
                    break;
                case "show":
                    System.out.println(event.toString());
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }
        return;
    }
    private static Diff newDiff(LinkedList<String> args) throws IndexOutOfBoundsException{
        String name ;
        if(args.size()>2){
            name = args.get(1);
        }else{
            name=Input.input("Name");
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
                System.out.println("Unbekanntes Diff");
                break;
        }
        editDiff(diff);
        return diff;
    }
    private static void editDiff(Diff diff){
       if(diff instanceof ContainerDiff){
           editContainerDiff((ContainerDiff) diff);
       }else if(diff instanceof ExitDiff){
           editExitDiff((ExitDiff) diff);
       }else if(diff instanceof LocationDiff){
           editLocationDiff((LocationDiff) diff);
       }else if(diff instanceof NPCDiff){
           editNPCDiff((NPCDiff) diff);
       }else if(diff instanceof RoomDiff){
           editRoomDiff((RoomDiff) diff);
       }else if(diff instanceof ToolDiff){
           editToolDiff((ToolDiff) diff);
       }else{
           System.out.println("Unbekanntes Diff");
       }
    }
    private static void editContainerDiff(ContainerDiff diff){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Diff " + diff.getName() + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "add":
                    switch(commands.get(1)){
                        case "description":
                            diff.setDescription(Input.input("Beschreibung"));
                            break;
                        case "room":
                            diff.setRoom(Input.input("Raum"));
                            break;
                        case "addtools":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            diff.setAddTools(commands);
                            break;
                        case "rmtools":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            diff.setRmTools(commands);
                            break;
                    }
                    break;
                case "rm":
                    switch(commands.get(1)){
                        case "description":
                            diff.setDescription(null);
                            break;
                        case "room":
                            diff.setRoom(null);
                            break;
                        case "addtools":
                            diff.setAddTools(null);
                            break;
                        case "rmtools":
                            diff.setRmTools(null);
                            break;
                    }
                    break;
                case "show":
                    System.out.println(diff.toString());
                    break;
                case "back":
                    return;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }
    }
    private static void editExitDiff(ExitDiff diff){

    }
    private static void editLocationDiff(LocationDiff diff){

    }
    private static void editNPCDiff(NPCDiff diff){

    }
    private static void editRoomDiff(RoomDiff diff){

    }
    private static void editToolDiff(ToolDiff diff){

    }

}