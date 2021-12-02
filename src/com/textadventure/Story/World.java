package com.textadventure.Story;

import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Item;
import com.textadventure.things.Tool;

import java.io.*;
import java.util.*;

public class World {
    public HashMap<String, Room> roomMap = new HashMap<>();
    public HashMap<String, Exit> exitMap = new HashMap<>();
    public HashMap<String, Location> locationMap = new HashMap<>();
    public HashMap<String, Tool> toolMap = new HashMap<>();
    public HashMap<String, Container> containerMap = new HashMap<>();
    public HashMap<String, NPC> npcMap = new HashMap<>();
    //TODO Add Events (in Rooms)
    public HashMap<String, Event> eventMap = new HashMap<>();

    public void load(String path){
        try {
            LoadStoreWorld.load(path, this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void store(String path){
        LoadStoreWorld.store(path,this);
    }
    public void worldEditor(String path) {
        System.out.println("Willkommen im Welten Editor");
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        boolean exit = false;
        while (!exit) {
            System.out.print(">> ");
            input = scanner.nextLine();
            input = input.toLowerCase();
            if (input.equals(""))
                continue;
            commands = new LinkedList<>(Arrays.asList(input.split("[ \n]")));
            commands.removeIf(s -> s.equals(""));
            switch (commands.get(0)) {
                case "new":
                    try {
                        newGameElement(commands);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Too few arguments");
                    }
                    break;
                case "edit":
                    try {
                        editGameElement(commands);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Too few arguments");
                    }
                    break;
                case "store":
                    store("0_Story/");
                    break;
                case "load":
                        load("0_Story/");
                    break;
                case "overview":
                case "ov":
                    //TODO Funktion which checks for inconsistencies. e.g. a Location contains a room, but the room does not reference the location
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }
    private void editGameElement(LinkedList<String> args) {

    }
    //TODO input den gonzn scheiß, der itz in die Objekte isch, Check schreib i
    private void newGameElement(LinkedList<String> args) {
        //Get GameElement Properties name, description and info
        GameElement element = new GameElement();
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
                    element.setName(args.get(2));
                } else {
                    inputName(element);
                }
                try { // If Element already exists somewhere
                    getElement(element.getName(), args.get(1)); // Throws Exception if Element exists
                    System.out.println("Element with this name Exists already. Do you want to Overwrite, Edit, or Abort? (o,e,a)");
                    String[] options = {"o", "a", "e"};
                    ret = Input.switchOptions(options);
                    switch (ret) {
                        case "a":
                            return;
                        case "o":
                            break;
                        case "e":
                            args.set(0, "edit");
                            args.set(1, element.getName());
                            editGameElement(args);
                            return;
                    }
                } catch (ElementNotFoundException e) {
                    //Everything's ok
                }
                inputDescription(element);
                break;
            case "element":
                break;
            default:
                System.out.println("Object does not exist");
                return;
        }


        //For Specific features
        //TODO Extra Options
        switch (args.get(1)) {
            case "npc":
                NPC npc = new NPC(element.getName(), element.getDescription());
                //Dialogs - Events Dialogs can cause
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
     * Checks if the name of a given GameElement type does already exist in their respective map
     *
     * @param name name of an Element
     * @param type type of an Element
     * @return GameElement if it was found
     * @throws ElementNotFoundException if Element is not found this Exception is being thrown
     */
    private GameElement getElement(String name, String type) throws ElementNotFoundException {
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
        throw new ElementNotFoundException(name);
    }


    private void addLocation(Location location) {
        locationMap.put(location.getName(), location);
    }

    private void addRoom(Room room) {
        roomMap.put(room.getName(), room);
    }

    private void addNPC(NPC npc) {
        npcMap.put(npc.getName(), npc);
    }
    private void addTool(Tool tool){
        toolMap.put(tool.getName(),tool);
    }
    private void addContainer(Container container){
        containerMap.put(container.getName(),container);
    }


    private void inputName(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("name: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        element.setName(input);
    }

    private void inputDescription(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("description: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        element.setDescription(input);
    }

    private String inputLocation() {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("Umgebung: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        return input;
    }

}
