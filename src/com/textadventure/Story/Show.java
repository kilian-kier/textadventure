package com.textadventure.Story;

import com.textadventure.GameElement;
import com.textadventure.exeptions.ElementNotFoundException;

/**
 * Zur Ausgabe von Eigenschaften verschiedener Elemente im Spiel
 */
public class Show {
    private static void showRooms(){
        if(!World.roomMap.isEmpty()){
            System.out.println("Räume:");
            for (String i:World.roomMap.keySet() ) {
                System.out.println("\t"+i);
            }
        }else{
            System.out.println("Keine Räume");
        }
    }
    private static void showExits(){
        if(!World.exitMap.isEmpty()) {
            System.out.println("Exits:");
            for (String i : World.exitMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine Exits");
        }
    }
    private static void showLocations(){
        if(!World.locationMap.isEmpty()) {
            System.out.println("Locations:");
            for (String i : World.locationMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine Locations");
        }
    }
    private static void showTools(){
        if(!World.toolMap.isEmpty()) {
            System.out.println("Tools:");
            for (String i : World.toolMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine Tools");
        }
    }
    private static void showContainer(){
        if(!World.containerMap.isEmpty()) {
            System.out.println("Container:");
            for (String i : World.containerMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine Container");
        }
    }
    private static void showNPCs(){
        if(!World.npcMap.isEmpty()) {
            System.out.println("NPCs:");
            for (String i : World.npcMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine NPCs");
        }
    }
    private static void showEvents(){
        if(!World.eventKeyMap.isEmpty()) {
            System.out.println("Events:");
            for (String i : World.eventKeyMap.keySet()) {
                System.out.println("\t" + i);
            }
        }else{
            System.out.println("Keine Events");
        }
    }

    /**
     * Show Funktion gibt Eigenschaften aus:
     * Wenn kein Name und Typ übergeben werden, werden alle Elemente der Welt ausgegeben
     * Wenn nur ein Name übergeben wird ein passendes Element ausgegeben
     * Wenn nur ein Typ angegeben, werden alle Elemente eines bestimmten Typs ausgegeben
     * Wenn Name und typ angegeben werden, wird ein Element des richtigen Typs gesucht
     * @param name Name des Elements
     * @param type Typ des Elements
     * @throws ElementNotFoundException Wenn kein passendes Element gefunden wurde, kommt es zu dieser Ausnahme
     */
    protected static void show(String name, String type) throws ElementNotFoundException {
        if(name==null && type==null){
            showRooms();
            showExits();
            showLocations();
            showTools();
            showContainer();
            showNPCs();
            showEvents();
            return;
        }
        GameElement element;
        if(type==null){
            try {
                element=World.getElement(name, null);
                System.out.println(element.toString());
            } catch (ElementNotFoundException e ) {
                try {
                    System.out.println((World.eventMap.get(World.eventKeyMap.get(name))).toString());
                }catch(NullPointerException ex ){
                    throw new ElementNotFoundException(name, "Name");
                }
            }
        }else if(name==null){
            switch(type){
                case "events":
                    showEvents();
                    break;
                case "rooms":
                    showRooms();
                    break;
                case "npcs":
                    showNPCs();
                    break;
                case "container":
                    showContainer();
                    break;
                case "exits":
                    showExits();
                    break;
                case "locations":
                    showLocations();
                    break;
                case "tools":
                    showTools();
                    break;
                default:
                    throw new ElementNotFoundException(type, "Type");
            }
        }else{
            if(type.equals("event")){
                try {
                    System.out.println((World.eventMap.get(World.eventKeyMap.get(name))).toString());
                }catch(Exception e){
                    throw new ElementNotFoundException(name, "Name");
                }
                return;
            }
            element=World.getElement(name, type);
            System.out.println(element.toString());
        }
    }

}
