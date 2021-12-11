package com.textadventure.Story;


import com.textadventure.Event.Event;
import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import javax.swing.text.Element;
import java.util.*;

public class World {
    static public HashMap<String, Room> roomMap = new HashMap<>();
    static public HashMap<String, Exit> exitMap = new HashMap<>();
    static public HashMap<String, Location> locationMap = new HashMap<>();
    static public HashMap<String, Tool> toolMap = new HashMap<>();
    static public HashMap<String, Container> containerMap = new HashMap<>();
    static public HashMap<String, NPC> npcMap = new HashMap<>();
    static public HashMap<String, Event> eventMap = new HashMap<>();
    static public HashMap<String, String> eventKeyMap = new HashMap<>();
    //TODO new Player
    static public Player player;
    static public HashMap<String,HashMap> editMap = new HashMap<>();


    static public void worldEditor(String path) {
        editMap.put("room", roomMap);
        editMap.put("exit", exitMap);
        editMap.put("location", locationMap);
        editMap.put("tool", toolMap);
        editMap.put("container", containerMap);
        editMap.put("npc", npcMap);
        editMap.put("event", eventMap);

        System.out.println("Willkommen im Welten Editor");
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        boolean exit = false;
        while (!exit) {
            System.out.print(">> ");
            input = scanner.nextLine();
            commands = splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "new":
                    try {
                        newGameElement(commands);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    }
                    break;
                case "edit":
                    try {
                        editGameElement(commands);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    }
                    break;
                case "show":
                    try{
                        if(commands.size()>2){
                            Show.show(commands.get(2),commands.get(1));
                        }else if(commands.size()==2){
                            try {
                                Show.show(null,commands.get(1));
                            }catch(ElementNotFoundException e){
                                Show.show(commands.get(1), null);
                            }
                        }else{
                            Show.show(null, null);
                        }
                    }catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    }catch(ElementNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "store":
                    LoadStoreWorld.store(path);
                    break;
                case "load":
                    try {
                        LoadStoreWorld.load(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "check":
                    if(!Checker.check()){
                        System.out.println("Es gibt Fehler in der Welt");
                    }else{
                        System.out.println("Alles in Ordnung");
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }
    }







    static private void editElement(LinkedList<String> args) {
        GameElement temp;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> command;
        String input;
            System.out.print(args.get(1) +" "+ args.get(2) + ": ");
            input = scanner.nextLine();
            command = splitInput(input);
            temp = (GameElement) editMap.get(args.get(1)).get(args.get(2)); //TODO: Element not found
            switch (command.get(0)) {
                case "exit":
                    return;
                case "add":
                    break;
                case "changet":
                    break;
                case "remove":
                    break;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;

        }
    }
    static private void editEvent(LinkedList<String> args){

    }
    static private void editGameElement(LinkedList<String> args) {

    }
    //TODO Input
    static private void newGameElement(LinkedList<String> args) {
        //Get GameElement Properties name, description and info
        GameElement element = null;
        GameElement temp = null;
        String ret;

        switch (args.get(1)) {
            case "npc":
            case "exit":
            case "location":
            case "room":
            case "tool":
            case "container":
                if (args.size() > 2) { //Check if name parameter exists
                    element = new GameElement(args.get(2));
                } else {
                    element = new GameElement(inputName());
                }
                try { // If Element already exists somewhere
                    getElement(element.getName(), args.get(1)); // Throws Exception if Element exists
                    System.out.println("Element existiert bereits. Möchtest du Überschreiben, Bearbeiten, oder Abbrechen? (ü,b,a)");
                    String[] options = {"o", "a", "e","ü","b"};
                    ret = Input.switchOptions(options);
                    switch (ret) {
                        case "a":
                            return;
                        case "o":
                        case "ü":
                            break;
                        case "e":
                        case "b":
                            args.set(0, "edit");
                            args.set(1, element.getName());
                            editGameElement(args);
                            return;
                    }
                } catch (ElementNotFoundException e) {
                    //Alles OK
                }
                inputDescription(element);
                break;
            case "event":
                if(args.size()>2) {
                    EventEditor.edit(args.get(2));
                }else{
                    EventEditor.edit(null);
                }
                return;
            default:
                System.out.println("Object does not exist");
                return;
        }

        //sist hot er do untn ongst dass element null isch
        Objects.requireNonNull(element);


        //For Specific features
        //TODO Extra Options
        switch (args.get(1)) {
            case "npc":
                NPC npc = new NPC(element.getName(), element.getDescription());
                //Dialogs -  Dialogs can cause Events
                //Location/Room
                npcMap.put(npc.getName(), npc);
                break;
            case "exit":
                Exit exit = new Exit(element.getName(), element.getDescription());
                //Destination Location/Room
                //Location/Room
                exitMap.put(exit.getName(), exit);
                break;
            case "location":
                Location location = new Location(element.getName(), element.getDescription());
                //Rooms
                locationMap.put(location.getName(), location);
                break;
            case "room":
                Room room = new Room(element.getName(), element.getDescription());
                //Location
                //Items
                //Exits
                //Npcs
                //Possibly Image Path for later on
                roomMap.put(room.getName(), room);
                break;
            case "tool":
                Tool tool = new Tool(element.getName(), element.getDescription());
                //Room
                toolMap.put(tool.getName(), tool);
                break;
            case "container":
                Container container = new Container(element.getName(), element.getDescription());
                //Room
                //Items
                containerMap.put(container.getName(), container);
                break;
            case "event":
                //TODO Add Event
                //Which Action, Which Items, in Which Room cause Which Changes to a GameElement
                break;
        }
    }


    /**
     * Kontrolliert ob ein GameElement existiert
     *
     * @param name Name des Elements
     * @param type Typ des Elements
     * @return Falls das Element gefunden wird, wird es zurückgegeben
     * @throws ElementNotFoundException Wenn das Element nicht gefunden wurde, entsteht diese Exception
     */
    static protected GameElement getElement(String name, String type) throws ElementNotFoundException {
        if(type!=null) {
            switch (type) {
                case "room":
                    if (roomMap.containsKey(name)) return roomMap.get(name);
                    break;
                case "location":
                    if (locationMap.containsKey(name)) return locationMap.get(name);
                    break;
                case "tool":
                    if (toolMap.containsKey(name)) return toolMap.get(name);
                    break;
                case "container":
                    if (containerMap.containsKey(name)) return containerMap.get(name);
                    break;
                case "npc":
                    if (npcMap.containsKey(name)) return npcMap.get(name);
                    break;
                case "exit":
                    if (exitMap.containsKey(name)) return exitMap.get(name);
                    break;
            }
        }else{
            if (roomMap.containsKey(name)) return roomMap.get(name);
            if (locationMap.containsKey(name)) return locationMap.get(name);
            if (toolMap.containsKey(name)) return toolMap.get(name);
            if (containerMap.containsKey(name)) return containerMap.get(name);
            if (npcMap.containsKey(name)) return npcMap.get(name);
            if (exitMap.containsKey(name)) return exitMap.get(name);
        }
        throw new ElementNotFoundException(name, "Name");
    }

    //TODO: entweder de löschen oder die Maps private mochen, wos schiana war - evt. später wenn wo olla zeit hobm an refactor van projekt mochn

    static private String inputName() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        System.out.print("name: ");
        while (input.length() == 0)
            input = scanner.nextLine();
        return input;
    }

    static private void inputDescription(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        System.out.print("description: ");
        while (input.length() == 0)
            input = scanner.nextLine();
        element.setDescription(input);
    }
    static public LinkedList<String> splitInput (String input){
        input = input.toLowerCase();
        if (input.equals("")) return null;
        LinkedList<String> command = new LinkedList<>(Arrays.asList(input.split("[ \n]")));
        command.removeIf(s -> s.equals(""));
        return command;
    }
}

