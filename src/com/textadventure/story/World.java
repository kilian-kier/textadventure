package com.textadventure.story;


import com.textadventure.Event.Event;
import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.gamemusic.MusicPlayer;
import com.textadventure.help.Help;
import com.textadventure.input.Game;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
import java.util.zip.ZipInputStream;

  class MyFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith( ".world" ) || name.endsWith( ".txt" );
        }
  }

/**
 * Die World Klasse hat 7 HashMaps und ein Objekt des Players und ein Objekt des MusicPlayers.
 * In der World Klasse findet man Methoden zum Erstellen und bearbeiten der Spielwelt.
 */
public class World {
    static public HashMap<String, Room> roomMap = new HashMap<>();
    static public HashMap<String, Exit> exitMap = new HashMap<>();
    static public HashMap<String, Location> locationMap = new HashMap<>();
    static public HashMap<String, Tool> toolMap = new HashMap<>();
    static public HashMap<String, Container> containerMap = new HashMap<>();
    static public HashMap<String, NPC> npcMap = new HashMap<>();
    static public HashMap<String, Event> eventMap = new HashMap<>();
    static public HashMap<String, String> eventKeyMap = new HashMap<>();
    static public Player player;
    static public MusicPlayer musicPlayer = new MusicPlayer();
    static public ArrayList<String> musicList = new ArrayList<>();
    static public String tempDir=".";

    static public boolean explorer = false;

    public static void setExplorer(boolean explorer) {
        World.explorer = explorer;
    }

    public static boolean isJar() {
        try {
            CodeSource src = Help.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                return zip.getNextEntry() != null;
            }
        } catch (IOException e) {
            //DO NOTHING
        }
        return false;
    }

    /**
     * Im weltEditor kann die Speilwelt gespeichert/geladen und bearbeitet werden.
     *
     * @param path Pfad zum einlesen der Spielewelt
     */
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
                case "bridge":
                    try {
                        createBridge(commands.get(1), commands.get(2));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    }
                    break;
                case "explorer":
                    explorer = !explorer;
                    System.out.printf("Explorer auf %b gesetzt\n", explorer);
                    break;
                case "mv":
                    try {
                        mvGameElement(commands.get(1), commands.get(2));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zu wenig Argumente");
                    } catch (ElementNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "rm":
                    try {
                        rmGameElement(commands.get(1));
                    } catch (ElementNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Zun wenig Argumente");
                    }
                    break;
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
                    if (!explorer) {
                        if (commands.size() > 1) {
                            LoadStoreWorld.store(commands.get(1));
                        } else {
                            LoadStoreWorld.store(path);
                        }
                    } else {
                        try {
                            FileDialog fd = new FileDialog(new Frame(), "Weltdatei speichern", FileDialog.SAVE);
                            fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
                            fd.setFile("*.world");
                            fd.setVisible(true);
                            String filename = fd.getFile();
                            if (filename != null)
                                LoadStoreWorld.store(fd.getDirectory() + filename);
                            else
                                System.out.println("Keine Datei ausgew??hlt");
                        } catch (URISyntaxException e) {
                            //DO NOTHING
                        }
                    }
                    break;
                case "load":

                    if (!explorer) {
                        try {
                            if(Input.getFileType(commands.get(1),true).equals("world")){
                                LoadStoreWorld.load(commands.get(1));
                            }else{
                                LoadStoreWorld.loadtxt(commands.get(1));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Zu wenig Parameter");
                        }catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Pfad nicht gefunden");
                        }
                    }else {
                    try {
                        if (commands.size() < 2) {
                            FileDialog fd = new FileDialog(new Frame(), "Weltdatei laden", FileDialog.LOAD);
                            fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
                            fd.setFile("*.world");
                            FilenameFilter filter = new MyFilenameFilter();
                            fd.setFilenameFilter(filter);
                            fd.setVisible(true);
                            String filename = fd.getFile();
                            if (filename != null){
                                    if(Input.getFileType(filename,true).equals("world")){
                                        LoadStoreWorld.load(fd.getDirectory() + filename);
                                    }else{
                                        LoadStoreWorld.loadtxt(fd.getDirectory() + filename);
                                    }
                            }
                            else
                                System.out.println("Keine Datei ausgew??hlt");
                        } else if (commands.get(1).equals("-s"))
                            LoadStoreWorld.load(null);
                    } catch (Exception e) {
                        System.out.println("Pfad nicht gefunden");
                        e.printStackTrace();
                    }
                    }
                    break;
                case "close":
                    LoadStoreWorld.close();
                    break;
                case "include":
                    try {
                        FileDialog fd = new FileDialog(new Frame(), "Weltdatei hinzuf??gen", FileDialog.LOAD);
                        fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
                        fd.setFile("*.world");
                        fd.setVisible(true);
                        String filename = fd.getFile();
                        if (filename != null)
                            LoadStoreWorld.include(fd.getDirectory() + filename);
                        else
                            System.out.println("Keine Datei ausgew??hlt");
                    } catch (URISyntaxException e) {
                        //DO NOTHING
                    }
                    break;
                case "check":
                    if (commands.size() > 1 && commands.get(1).equals("fix")) {
                        if (!Checker.check(true)) {
                            System.out.println("Es gibt Fehler in der Welt. Sie wurden wenn m??glich korrigiert");
                        } else {
                            System.out.println("Alles in Ordnung");
                        }
                    } else {
                        if (!Checker.check(false)) {
                            System.out.println("Es gibt Fehler in der Welt");
                        } else {
                            System.out.println("Alles in Ordnung");
                        }
                    }

                    break;
                case "help":
                    try {
                        if (commands.size() > 1) {
                            System.out.println(Help.help("WorldEditor", commands.get(1)));
                        } else {
                            System.out.println(Help.help("WorldEditor", null));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "execute":
                    try {
                        System.out.println("R??ckgabe: "+Event.execSingleEvent(commands.get(1)));
                    }catch(Exception e){
                        System.out.println("Zu wenig Argumente");
                    }
                    break;
                case "start":
                    if (!Checker.check(false)) {
                        System.out.println("Es gibt Fehler in der Welt");
                        break;
                    }
                    Game.start();
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

    /**
     * Eine Funktion zur vereinfachten erstellung von Exits, welche zwei R??ume veerbinden
     *
     * @param room1 Erster Raum
     * @param room2 Zweiter Raum
     */
    private static void createBridge(String room1, String room2) {
        if (!World.roomMap.containsKey(room1) || !World.roomMap.containsKey(room2)) {
            System.out.println("Mindestens einer der R??ume existiert nicht");
            return;
        }
        String exitname;
        try {
            exitname = room1.substring(0, 4) + room2.substring(0, 4);
        } catch (Exception e) {
            exitname = room1 + room2;
        }
        if (World.exitMap.containsKey(exitname)) {
            System.out.println("Exit " + exitname + " existiert bereits");
            return;
        }
        String description1 = Input.input(String.format("Beschreibung aus Sicht von %s", room2), false);
        String description2 = Input.input(String.format("Beschreibung aus Sicht von %s", room1), false);
        Exit exit = new Exit(exitname, description1 + "@" + description2);
        exit.setDestination1(room1);
        exit.setDestination2(room2);
        World.exitMap.put(exitname, exit);
        World.roomMap.get(room1).addExit(exitname);
        World.roomMap.get(room2).addExit(exitname);
        return;
    }

    /**
     * Die Funktion dient zum umbenennen eines Spiel elements
     *
     * @param name1 Urspr??nglicher Name
     * @param name2 Neuer Name
     * @throws ElementNotFoundException Falls ein Element nicht existiert
     */
    private static void mvGameElement(String name1, String name2) throws ElementNotFoundException {
        try {
            GameElement element = getElement(name1, null);
            element.setName(name2);
            if (element.getClass().equals(Exit.class)) {
                World.exitMap.remove(name1);
                World.exitMap.put(name2, (Exit) element);
            } else if (element.getClass().equals(Room.class)) {
                World.roomMap.remove(name1);
                World.roomMap.put(name2, (Room) element);
                ((Room) element).getToolsContainer().setName(name2);
                World.containerMap.remove(name1);
                World.containerMap.put(name2, ((Room) element).getToolsContainer());
            } else if (element.getClass().equals(Location.class)) {
                World.locationMap.remove(name1);
                World.locationMap.put(name2, (Location) element);
            } else if (element.getClass().equals(NPC.class)) {
                World.npcMap.remove(name1);
                World.npcMap.put(name2, (NPC) element);
            } else if (element.getClass().equals(Container.class)) {
                World.containerMap.remove(name1);
                World.containerMap.put(name2, (Container) element);
            } else if (element.getClass().equals(Tool.class)) {
                World.toolMap.remove(name1);
                World.toolMap.put(name2, (Tool) element);
            }
            System.out.println("Element erfolgreich unbenannt");
        } catch (ElementNotFoundException e) {
            if (eventKeyMap.get(name1) != null) {
                String event = eventKeyMap.get(name1);
                eventMap.get(event).setName(name2);
                eventKeyMap.remove(name1);
                eventKeyMap.put(name2, event);
                System.out.println("Element erfolgreich unbenannt");
            } else {
                throw e;
            }
        }
    }

    /**
     * Funktion entfernt ein Spiel Element
     *
     * @param name Name des Elements
     * @throws ElementNotFoundException Wird geworfen, wenn ein Element nicht existiert
     */
    private static void rmGameElement(String name) throws ElementNotFoundException {

        if (World.eventMap.remove(World.eventKeyMap.get(name)) != null) {
            World.eventKeyMap.remove(name);
            return;
        }
        if (World.roomMap.remove(name) != null) {
            World.containerMap.remove(name);
            return;
        }
        if (World.containerMap.remove(name) != null) return;
        if (World.locationMap.remove(name) != null) return;
        if (World.npcMap.remove(name) != null) return;
        if (World.toolMap.remove(name) != null) return;
        if (World.exitMap.remove(name) != null) return;
        throw new ElementNotFoundException("Element", name);
    }


    /**
     * F??gt Musik zur Welt hinzu
     *
     * @param path Pfad der Musikdatei
     */
    public static void addMusic(String path){
        File musicFile = new File(path);
        if (musicFile.exists()) {
            World.musicList.add(path);
        } else
            System.out.println("Datei nicht gefunden");
    }


    /**
     * Methode sucht zu bearbeitendes Element und ruft die zugeh??rige Methode auf
     *
     * @param args Befehl, der vom Benutzer eingegeben wurde
     * @throws ElementNotFoundException wenn das zu bearbeitende Element nicht existiert
     */
    static private void editGameElement(LinkedList<String> args) throws ElementNotFoundException {
        //Trys m??ssen alle einzeln sein, da sonst die anderen ??berpr??fungen ??bersprungen werden !!!
        try {
            Tool temp = (Tool) getElement(args.get(1),"tool");
            if(temp.edit()) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            Room temp = (Room) getElement(args.get(1),"room");
            if(temp.edit()) return;  //Muss oben bleiben wegen container
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            Container temp = (Container) getElement(args.get(1),"container");
            if(temp.edit()) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            Exit temp = (Exit) getElement(args.get(1),"exit");
            if(temp.edit()) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            NPC temp = (NPC) getElement(args.get(1),"npc");
            if(temp.edit()) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        try {
            Location temp = (Location) getElement(args.get(1),"location");
            if(temp.edit()) return;
        } catch (ElementNotFoundException e) {
            //Tutto bene
        }
        if (World.player != null && Objects.equals(World.player.getName(), args.get(1)))
            if (World.player.edit()) return;
        if (eventKeyMap.containsKey(args.get(1))) if (EventEditor.edit(args.get(1))) return;
        throw new ElementNotFoundException(args.get(1), "Game Element");
    }

    /**
     * Erstellt ein neues Spiel Element
     *
     * @param args Die Argumente. Das erste Argument enth??lt meistens new, das zweite den Typ des Elements, das Dritte einen Optionalen Namen
     */
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
                    element = new GameElement(Input.input("name", true));
                }
                try { // If Element already exists somewhere
                    getElement(element.getName(), args.get(1)); // Throws Exception if Element exists
                    System.out.println("Element existiert bereits. M??chtest du ??berschreiben, Bearbeiten, oder Abbrechen? (??,b,a)");
                    String[] options = {"o", "a", "e", "??", "b"};
                    ret = Input.switchOptions(options);
                    switch (ret) {
                        case "a":
                            return;
                        case "o":
                        case "??":
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
                element.setDescription(Input.input("description", false));
                break;
            case "event":
                if (args.size() > 2) {
                    EventEditor.edit(args.get(2));
                } else {
                    EventEditor.edit(null);
                }
                return;
            case "player":
                if (World.player != null) {
                    System.out.println("Ein Spieler existiert bereits");
                    return;
                } else {
                    element = new GameElement(Input.input("name", true));
                    element.setDescription(Input.input("description", false));
                }
                break;
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
            case "player":
                World.player = new Player(element.getName(), element.getDescription(), null);
                break;
        }
    }


    /**
     * Kontrolliert ob ein GameElement existiert
     *
     * @param name Name des Elements
     * @param type Typ des Elements
     * @return Falls das Element gefunden wird, wird es zur??ckgegeben
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
