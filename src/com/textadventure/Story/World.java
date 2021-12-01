package com.textadventure.Story;

import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Item;

import java.io.FileNotFoundException;
import java.util.*;

public class World {
    public HashMap<String, Room> roomMap = new HashMap<>();
    public HashMap<String, Exit> exitMap = new HashMap<>();
    public HashMap<String, Location> locationMap = new HashMap<>();
    public HashMap<String, Item> itemMap = new HashMap<>();
    public HashMap<String, NPC> npcMap = new HashMap<>();
    //TODO Add Events (in Rooms)
    public HashMap<String, Event> eventMap = new HashMap<>();


    private void loadEventMap(String path) {
        /*File directory = new File(path);
        File[] contents= directory.listFiles();
        String content="";
        for(File f: contents){
            try {
                content= Files.readString(Path.of(f.getAbsolutePath()));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(content);
        }*/
    }

    public void load(String path) throws FileNotFoundException {
        /*File directory = new File(path);
        if(!directory.exists()){
            throw new FileNotFoundException(path+" not found");
        }
        File[] contents = directory.listFiles();
        for ( File f : contents) {
            if(f.getName().equals("Events")){
                loadEventMap(f.getAbsolutePath());
            }
            System.out.println(f.getName());
        }
        Properties properties = new Properties();
        properties.setProperty("Type","Location");
        properties.setProperty("Name","Forestis ad Lusinam");
        properties.setProperty("Info","Dies ist ein kleines altes Dorf. Es trägt den Namen Forestis ad Lusinam. ");
        properties.setProperty("Description","Die Leute hier sind sehr freundlich. Ich habe mich in einem kleinen abgelegenen Gasthaus die Nacht verbracht. Es gibt einen kleinen Dorfplatz, wo man immer Leute antrifft. Davor steht eine für ein Dorf dieser Größe prächtige Kirche. Es gibt nicht viele Häuser. Hervor sticht aber eine kleine Hütte am Waldrand.");
        ArrayList<String> rooms=new ArrayList();
        rooms.add("Gasthaus");
        rooms.add("Dorfplatz");
        rooms.add("Kirche");
        rooms.add("Hütte");
        properties.setProperty("Rooms", rooms.toString());
        FileOutputStream out = new FileOutputStream(path);
        try {
            properties.storeToXML(out, "Lüsen");
        }catch(Exception e){

        }*/
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
            case "item":
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
                inputInfo(element);
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
                NPC npc = new NPC(element.getName(), element.getDescription(), element.getInfo());
                //Dialogs - Events Dialogs can cause
                //Location/Room
                npcMap.put(npc.getName(), npc);
                break;
            case "exit":
                Exit exit = new Exit(element.getName(), element.getDescription(), element.getInfo(), null);
                //Destination Location/Room
                //Location/Room
                exitMap.put(exit.getName(), exit);
                break;
            case "location":
                Location location = new Location(element.getName(), element.getDescription(), element.getInfo());
                //Rooms
                locationMap.put(location.getName(), location);
                break;
            case "room":
                Room room = new Room(element.getName(), element.getDescription(), element.getInfo(), null);
                //Location
                //Items
                //Exits
                //Npcs
                //Possibly Image Path for later on
                roomMap.put(room.getName(), room);
                break;
            case "item":
                Item item = new Item(element.getName(), element.getDescription(), element.getInfo(), null);
                //Room
                itemMap.put(item.getName(), item);
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
            case "item":
                if (itemMap.containsKey(name)) return itemMap.get(name);
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

    private void addItem(Item item) {
        itemMap.put(item.getName(), item);
    }

    private void inputName(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("name: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        if (input.equals("exit")) return;
        element.setName(input);
    }

    private void inputInfo(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("info: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        if (input.equals("exit")) return;
        element.setInfo(input);
    }

    private void inputDescription(GameElement element) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("description: ");
        while ((input = scanner.nextLine()).length() == 0) ;
        if (input.equals("exit")) return;
        element.setDescription(input);
    }

}
