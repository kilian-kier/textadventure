package com.textadventure.Story;


import com.textadventure.Event.Event;
import com.textadventure.GameElement;
import com.textadventure.characters.Dialog;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import javax.print.attribute.standard.Destination;
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

    public static void worldEditor(String path) {

        System.out.println("Willkommen im Welten Editor");
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        boolean exit = false;
        while (!exit) {
            System.out.print(">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
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
                    } catch (ElementNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "show":
                    try {
                        if (commands.size() > 2) {
                            Show.show(commands.get(2), commands.get(1));
                        } else if (commands.size() == 2) {
                            try {
                                Show.show(null, commands.get(1));
                            } catch (ElementNotFoundException e) {
                                Show.show(commands.get(1), null);
                            }
                        } else {
                            Show.show(null, null);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    } catch (ElementNotFoundException e) {
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
                case "close":
                    LoadStoreWorld.close();
                    break;
                case "include":
                    try {
                        LoadStoreWorld.include(commands.get(1));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Parameter");
                    }
                    break;
                case "check":
                    if (!Checker.check()) {
                        System.out.println("Es gibt Fehler in der Welt");
                    } else {
                        System.out.println("Alles in Ordnung");
                    }
                    break;
                case "help":
                    try{
                        if(commands.size()>1) {
                            System.out.println(Help.help("WorldEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("WorldEditor", null));
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
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

    static private boolean editTool(Tool temp) {
        while (true) {
            System.out.print("Tool " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "set":
                    switch (command.get(1)) {
                        case "description" -> temp.setDescription(Input.input("description"));
                        case "room" -> {
                            if (roomMap.containsKey(command.get(2))) temp.setContainer(command.get(2));
                            else System.out.println(command.get(2) + "nicht gefunden");
                        }
                        default -> System.out.println("command not found");
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }

    static private boolean editContainer(Container temp) {
        while (true) {
            System.out.print("Container " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "show":
                    System.out.println(temp.toString());
                    break;
                case "add":
                    command.removeFirst();
                    for (String x : command) {
                        if (toolMap.containsKey(x)) temp.addTool(x);
                        else System.out.println(x + " nicht gefunden");
                    }
                    break;
                case "rm":
                    command.removeFirst();
                    for (String x : command) {
                        if (!temp.removeTool(x)) System.out.println(" nicht gefunden!");
                    }
                    break;
                case "set":
                    switch (command.get(1)) {
                        case "description" -> temp.setDescription(Input.input("description"));
                        case "room" -> {
                            if (roomMap.containsKey(command.get(2))) temp.setContainer(command.get(2));
                            else System.out.println(command.get(2) + "nicht gefunden");
                        }
                        default -> System.out.println("command not found");
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }

    static private boolean editLocation(Location temp) {
        while (true) {
            System.out.print("Location " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "show":
                    System.out.println(temp.toString());
                    break;
                case "add":
                    command.removeFirst();
                    for (String x : command) {
                        if (roomMap.containsKey(x)) temp.addRoom(x);
                        else System.out.println(x + " nicht gefunden");
                    }
                    break;
                case "rm":
                    command.removeFirst();
                    for (String x : command) {
                        if (!temp.removeRoom(x)) System.out.println(x + " nicht gefunden!");
                    }
                    break;

                case "set":
                    if ("description".equals(command.get(1))) {
                        temp.setDescription(Input.input("description"));
                    } else {
                        System.out.println("command not found");
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }

    private static boolean editExit(Exit temp) {
        while (true) {
            System.out.print("Exit " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "show":
                    System.out.println(temp.toString());
                    break;
                case "set":
                    switch (command.get(1)) {
                        case "destination1" -> {
                            if (roomMap.containsKey(command.get(2))) temp.setDestination1(command.get(2));
                            else System.out.println(command.get(2) + " nicht gefunden");
                        }
                        case "destination2" -> {
                            if (roomMap.containsKey(command.get(2))) temp.setDestination2(command.get(2));
                            else System.out.println(command.get(2) + " nicht gefunden");
                        }
                        case "description" -> temp.setDescription(Input.input("description"));
                        default -> System.out.println("command not found");
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }

    private static boolean editNpc(NPC temp) {
        while (true) {
            System.out.print("NPC " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "show":
                    System.out.println(temp.toString());
                    break;
                case "add":
                case "rm":
                    temp.getDialog().edit();
                    break;
                case "set":
                    switch (command.get(1)) {
                        case "description" -> temp.setDescription(Input.input("description"));
                        case "room" -> {
                            if (roomMap.containsKey(command.get(2))) temp.setRoom(command.get(2));
                            else System.out.println(command.get(2) + " nicht gefunden");
                        }
                        default -> System.out.println("command not found");
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }

    private static boolean editRoom(Room temp) {
        while (true) {
            System.out.print("Room " + temp.getName() + ">>");
            LinkedList<String> command = Input.getCommand();
            switch (command.get(0)) {
                case "back":
                    return true;
                case "show":
                    System.out.println(temp.toString());
                    break;
                case "set":
                    switch (command.get(1)) {
                        case "description" -> temp.setDescription(Input.input("description"));
                        case "location" -> {
                            if (locationMap.containsKey(command.get(2))) temp.setLocation(command.get(2));
                            else System.out.println(command.get(2) + " nicht gefunden");
                        }
                        default -> System.out.println("command not found");
                    }
                case "add":
                    command.removeFirst();
                    switch (command.get(0)) {
                        case "exit":
                            command.removeFirst();
                            for (String x : command) {
                                if (exitMap.containsKey(x)) temp.addExit(x);
                                else System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "npc":
                            command.removeFirst();
                            for (String x : command) {
                                if (npcMap.containsKey(x)) temp.addNpcs(x);
                                else System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "tool":
                            command.removeFirst();
                            for (String x : command) {
                                if (toolMap.containsKey(x)) temp.addTool(x);
                                else System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "container":
                            command.removeFirst();
                            for (String x : command) {
                                if (containerMap.containsKey(x)) temp.addContainer(x);
                                else System.out.println(x + " nicht gefunden");
                            }
                            break;
                        default:
                            System.out.println("command not found");
                            break;
                    }
                    break;
                case "rm":
                    command.removeFirst();
                    switch (command.get(0)) {
                        case "exit":
                            command.removeFirst();
                            for (String x : command) {
                                if (!temp.removeExit(x)) System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "npc":
                            command.removeFirst();
                            for (String x : command) {
                                if (!temp.removeNpc(x)) System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "tool":
                            command.removeFirst();
                            for (String x : command) {
                                if (!temp.removeToolsKey(x)) System.out.println(x + " nicht gefunden");
                            }
                            break;
                        case "container":
                            command.removeFirst();
                            for (String x : command) {
                                if (!temp.removeContainer(x)) System.out.println(x + " nicht gefunden");
                            }
                            break;
                        default:
                            System.out.println("command not found");
                            break;
                    }
                    break;
                default:
                    System.out.println("command not found");
                    break;
            }
        }
    }


    static private void editGameElement(LinkedList<String> args) throws ElementNotFoundException {
        try {
            if (editTool((Tool) getElement(args.get(1), "tool"))) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            if (editRoom((Room) getElement(args.get(1), "room"))) return; //Muss oben bleiben wegen container
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            if (editContainer((Container) getElement(args.get(1), "container"))) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            if (editExit((Exit) getElement(args.get(1), "exit"))) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            if (editNpc((NPC) getElement(args.get(1), "npc"))) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            if (editLocation((Location) getElement(args.get(1), "location"))) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        if (EventEditor.edit(args.get(1))) return;
        throw new ElementNotFoundException(args.get(1), "Game Element");
    }


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
                    element = new GameElement(Input.input("name"));
                }
                try { // If Element already exists somewhere
                    getElement(element.getName(), args.get(1)); // Throws Exception if Element exists
                    System.out.println("Element existiert bereits. Möchtest du Überschreiben, Bearbeiten, oder Abbrechen? (ü,b,a)");
                    String[] options = {"o", "a", "e", "ü", "b"};
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
                element.setDescription(Input.input("description"));
                break;
            case "event":
                if (args.size() > 2) {
                    EventEditor.edit(args.get(2));
                } else {
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
        switch (args.get(1)) {
            case "npc":
                NPC npc = new NPC(element.getName(), element.getDescription());
                npcMap.put(npc.getName(), npc);
                break;
            case "exit":
                Exit exit = new Exit(element.getName(), element.getDescription());
                exitMap.put(exit.getName(), exit);
                break;
            case "location":
                Location location = new Location(element.getName(), element.getDescription());
                locationMap.put(location.getName(), location);
                break;
            case "room":
                Room room = new Room(element.getName(), element.getDescription());
                roomMap.put(room.getName(), room);
                break;
            case "tool":
                Tool tool = new Tool(element.getName(), element.getDescription());
                toolMap.put(tool.getName(), tool);
                break;
            case "container":
                Container container = new Container(element.getName(), element.getDescription());
                containerMap.put(container.getName(), container);
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
        if (type != null) {
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
        } else {
            if (roomMap.containsKey(name)) return roomMap.get(name);
            if (locationMap.containsKey(name)) return locationMap.get(name);
            if (toolMap.containsKey(name)) return toolMap.get(name);
            if (containerMap.containsKey(name)) return containerMap.get(name);
            if (npcMap.containsKey(name)) return npcMap.get(name);
            if (exitMap.containsKey(name)) return exitMap.get(name);
        }
        throw new ElementNotFoundException(name, "Name");
    }
}

//TODO: entweder de löschen oder die Maps private mochen, wos schiana war - evt. später wenn wo olla zeit hobm an refactor van projekt mochn
