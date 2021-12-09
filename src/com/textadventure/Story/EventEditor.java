package com.textadventure.Story;

import com.textadventure.Event.Diff;
import com.textadventure.Event.Event;
import com.textadventure.input.Input;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Event Editor zum bearbeiten von Events
 */
public class EventEditor {
    public static void edit(String name) {
        Event event=null;
        if (name == null) {
            String access = Input.input("Name");
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
                    String info= event.getInfo();
                    if(info==null){
                        info="";
                    }
                    if(Input.getEditor()!=null){
                        info=Input.edit(info);
                    }else{
                        info=Input.input("Info");
                    }
                    System.out.println(info);
                    event.setInfo(info);
                case "add": //Diff hinzufügen und editieren, wenn es existiert
                case "edit":
                    break;
                case "remove": //Diff entfernen
                    break;
                case "exit":
                    return;
            }
        }
        return;
    }
    private static void editDiff(Diff diff){

    }
}