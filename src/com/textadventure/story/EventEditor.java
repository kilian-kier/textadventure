package com.textadventure.story;

import com.textadventure.Event.*;
import com.textadventure.Event.Event;
import com.textadventure.exeptions.ElementExistsException;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Event Editor zum bearbeiten von Events
 */
public class EventEditor {
    /**
     * Event Editor zum editieren oder erstellen von Events
     *
     * @param name Der name des Events. Existiert er bereits, wird das Event editiert, ansonsten ein neues erstellt.
     * @return Gibt false zurück, wenn nicht erfolgreich
     */
    public static boolean edit(String name) {
        Event event=null;
        if (name == null) {
            name = Input.input("Name",true);
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
            String access = Input.input("Auslöser",true);
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
                case "once":
                    if(event.isOnce()){
                        event.setOnce(!event.isOnce());
                        System.out.println("Event wird nun oefter ausgefuehrt");
                    }else{
                        event.setOnce(!event.isOnce());
                        System.out.println("Event wird nun einmalig ausgefuehrt");
                    }
                    break;
                case "music":
                    try{
                        if (commands.size() > 1) {
                            if(commands.get(1).equals("none")){
                                event.setMusic(null);
                            }else{
                                event.setMusic(commands.get(1));
                                World.musicList.add(commands.get(1));
                                System.out.printf("Neuer Musikpfad: %s\n",commands.get(1));
                            }
                        } else {
                            if (World.explorer) {
                                FileDialog fd = new FileDialog(new Frame(), "Musikdatei laden", FileDialog.LOAD);
                                fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
                                fd.setFile("*.mp3");
                                fd.setVisible(true);
                                String filename = fd.getFile();
                                if (filename != null) {
                                    event.setMusic(fd.getDirectory() + filename);
                                    World.musicList.add(filename);
                                    System.out.printf("Neuer Musikpfad: %s\n", fd.getDirectory() + filename);
                                } else
                                    System.out.println("Keine Datei ausgewählt");
                            } else {
                                String filename = Input.input("Musikdatei", false);
                                if (filename != null) {
                                    File musicFile = new File(filename);
                                    if (musicFile.exists()) {
                                        event.setMusic(musicFile.getAbsolutePath());
                                        World.musicList.add(musicFile.getName());
                                        System.out.printf("Neuer Musikpfad: %s\n", musicFile.getAbsolutePath());
                                    } else
                                        System.out.println("Datei nicht gefunden");
                                }
                            }
                        }
                    }catch(IndexOutOfBoundsException | URISyntaxException e){
                        System.out.println("Zu wenig Parameter");
                    }
                    break;
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
                case "inventory": // Inventar Abhängigkeiten
                    commands.removeFirst();
                    event.setInventory(commands.isEmpty()?null:commands);
                    System.out.println("Inventar Abhängigkeiten gesetzt");
                    break;
                case "!needs":
                case "notneeds":
                    commands.removeFirst();
                    event.setNotdependent(commands.isEmpty()?null:commands);
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
                        info = Input.input("Info",false);
                    }
                    event.setInfo(info);
                    System.out.println("Info hinzugefügt");
                    break;
                case "room": //Raum in dem das Event funktioniert
                    String input2;
                    try{
                         input2=commands.get(1);
                    }catch(IndexOutOfBoundsException e){
                         input2= Input.input("Raum",true);
                    }
                    if(input2.equals("none")){
                        input2=null;
                    }
                    event.setRoom(input2);
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
                case "help":
                    try{
                        if(commands.size()>1) {
                            System.out.println(Help.help("EventEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("EventEditor", null));
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
        return false;
    }
    public static Diff newDiff(LinkedList<String> args,Event event) throws IndexOutOfBoundsException, ElementExistsException {
        String name ;
        args.get(1);
        if(args.size()>2){
            name = args.get(2);
        }else{
            name=Input.input("Name",true);
        }
        if(event.getDiff(name)!=null){
            throw new ElementExistsException(name);
        }

        Diff diff=null;
        diff=Event.newDiff(name,args.get(1));
        event.addDiff(diff);
        diff.edit();
        return diff;
    }


}