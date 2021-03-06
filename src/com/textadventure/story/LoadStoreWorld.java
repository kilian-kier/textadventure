package com.textadventure.story;

import com.textadventure.Event.Event;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.BracketException;
import com.textadventure.gamemusic.MusicPlayer;
import com.textadventure.input.Input;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Lädt und Speichert Welt
 */
public class LoadStoreWorld {
    private static String oldTempDir=null;
    /**
     * Lädt und erstellt die Musikdateien aus der Weltdatei und speichert die Dateipfäde in der Musikliste.
     * Die tempörären Dateien und Ordner werden beim Beenden des Programms gelöscht.
     *
     * @param map HashMap mit den Weltinformationen
     */
    private static void createMusicFiles(HashMap<String, Object> map) {
       try {
           ArrayList<String> keys = (ArrayList<String>) map.get("musicKeys");
           World.musicList=keys;
           Converter c = new Converter();
            if (keys != null) {
                Path tempDir = Files.createTempDirectory("tmp");
                tempDir.toFile().deleteOnExit();
                World.tempDir = tempDir.toString();
                if(oldTempDir!=null){
                    try {
                        File file = new File(oldTempDir);
                        File[] contents = file.listFiles();
                        if (contents != null) {
                            for (File f : contents) {
                                f.delete();
                            }
                        }
                        file.delete();
                    }catch(Exception ignore){

                    }
                    }
                oldTempDir=World.tempDir;
                for (String key : keys) {
                    File getName = new File(key);
                    key=getName.getName();
                    byte[] musicBytes = (byte[]) map.get(key);
                    File file = new File(tempDir + "/" + key);

                    if (file.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(musicBytes);
                        if(Input.getFileType(file.getName(),true).equals("mp3")){
                            c.convert(file.getAbsolutePath(), World.tempDir + "/" + Input.getFileType(key,false) + ".wav");
                        }
                            file.deleteOnExit();
                        file = new File(World.tempDir + "/" + Input.getFileType(key,false) + ".wav");
                        file.deleteOnExit();

                        if (World.roomMap.containsKey(key)) {
                            World.roomMap.get(key).setMusic(file.getName(), false);
                        }
                        if (World.eventKeyMap.containsKey(key)) {
                            World.eventMap.get(World.eventKeyMap.get(key)).setMusic(file.getName());
                        }
                    }
                }
            }
       } catch (IOException  | JavaLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lädt Welt von Festplatte
     *
     * @param path Datei mit gespeicherter Welt
     * @throws FileNotFoundException Wenn die Datei nicht gefunden wird
     */
    public static void load(String path) throws FileNotFoundException {
        try {
            InputStream fileIn;
            ObjectInputStream in;
            if (path == null) {
                if (World.isJar())
                    fileIn = LoadStoreWorld.class.getResourceAsStream("/game.world");
                else
                    fileIn = new FileInputStream("game.world");
            } else
                fileIn = new FileInputStream(path);

            in = new ObjectInputStream(fileIn);
            HashMap<String, Object> map = (HashMap<String, Object>) in.readObject();
            in.close();

            World.roomMap = (HashMap<String, Room>) map.get("rooms");
            World.locationMap = (HashMap<String, Location>) map.get("locations");
            World.toolMap = (HashMap<String, Tool>) map.get("tools");
            World.exitMap = (HashMap<String, Exit>) map.get("exits");
            World.npcMap = (HashMap<String, NPC>) map.get("npcs");
            World.containerMap = (HashMap<String, Container>) map.get("container");
            World.eventMap = (HashMap<String, Event>) map.get("events");
            World.eventKeyMap = (HashMap<String, String>) map.get("eventkeys");
            World.player = (Player) map.get("player");
           createMusicFiles(map);

            System.out.println("Welt wurde geladen");
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Speicherstand nicht kompatibel mit aktueller Version");
            System.out.println(i.getMessage());
        } catch (ClassNotFoundException c) {
            System.out.println("Klassen nicht gefunden");
            c.printStackTrace();
        }
    }

    /**
     * Fügt alle Elemente eines Speicherstand in die aktuelle Welt hinzu
     *
     * @param path Datei mit gespeicherter Welt
     */
    protected static void include(String path) {
        try {
            InputStream fileIn;
            ObjectInputStream in;

            fileIn = new FileInputStream(path);
            in = new ObjectInputStream(fileIn);
            HashMap<String, Object> map = (HashMap<String, Object>) in.readObject();
            in.close();

            World.roomMap.putAll((HashMap<String, Room>) map.get("rooms"));
            World.locationMap.putAll((HashMap<String, Location>) map.get("locations"));
            World.toolMap.putAll((HashMap<String, Tool>) map.get("tools"));
            World.exitMap.putAll((HashMap<String, Exit>) map.get("exits"));
            World.npcMap.putAll((HashMap<String, NPC>) map.get("npcs"));
            World.containerMap.putAll((HashMap<String, Container>) map.get("container"));
            World.eventMap.putAll((HashMap<String, Event>) map.get("events"));
            World.eventKeyMap.putAll((HashMap<String, String>) map.get("eventkeys"));
            World.player = (Player) map.get("player");
            createMusicFiles(map);

            System.out.println("Welt wurde hinzugefügt");
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Speicherstand nicht kompatibel mit aktueller Version");
        } catch (ClassNotFoundException c) {
            System.out.println("Klassen nicht gefunden");
            c.printStackTrace();
        }
    }

    /**
     * Schließt alle Elemente der Welt
     */
    public static void close() {
        World.roomMap.clear();
        World.locationMap.clear();
        World.toolMap.clear();
        World.exitMap.clear();
        World.npcMap.clear();
        World.containerMap.clear();
        World.eventKeyMap.clear();
        World.eventMap.clear();
        World.musicList.clear();
        System.out.println("Welt wurde geschlossen");
    }

    /**
     * Speichert die HashMaps der Welt mit Serializable in eine Datei
     *
     * @param path Datei zum Speichern
     */
    public static void store(String path) {
        try {
            FileOutputStream fileOut;
            ObjectOutputStream out;
            fileOut = new FileOutputStream(path);
            out = new ObjectOutputStream(fileOut);

            HashMap<String, Object> map = new HashMap<>();
            map.put("rooms", World.roomMap);
            map.put("locations", World.locationMap);
            map.put("tools", World.toolMap);
            map.put("exits", World.exitMap);
            map.put("npcs", World.npcMap);
            map.put("container", World.containerMap);
            map.put("events", World.eventMap);
            map.put("eventkeys", World.eventKeyMap);
            map.put("player", World.player);
            map.put("musicKeys", World.musicList);
            for (String key : World.musicList) {
                File file = new File(key);
                String path2=key;
                if (!file.exists()) {
                    System.out.println(file.getName());
                    path2=World.tempDir + "/" + file.getName();
                    file = new File(path2);
                    if (!file.exists()) {
                        System.out.println("File " + file.getAbsolutePath() + " does not exist");
                        continue;
                    }
                }
                byte[] musicBytes = MusicPlayer.readFile(path2);
                map.put(file.getName(), musicBytes);
            }

            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.println("Welt wurde gespeichert");

        } catch (IOException i) {
            System.out.println(i.getMessage());
            i.printStackTrace();
        }
    }

    /**
     * Lädt Welt von Txt Datei
     * @param path Pfad zur Datei
     */
    public static void loadtxt(String path) {
        String input = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            input = scanner.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        input=input.replace("\t","");
        input=input.replace("\\t","\t");
        input=input.replaceAll("(?s)/\\*(.)*?\\*/","");
        input=input.replaceAll("(?s)//(.)*?\n","");

        HashMap<String, String> split;
        try {
            split = splittxt(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        HashMap<String, HashMap<String, String>> splitMap = new HashMap<>();

        for (String i : split.keySet()) {
            try {
                splitMap.put(i, createMap(split.get(i)));
            } catch (Exception f) {
                System.out.println(f.getMessage());
            }
        }

        String type;
        String name;
        String playername = null;
        for (String i : splitMap.keySet()) {
            HashMap<String, String> object = splitMap.get(i);
            name = i;
            type = object.get("type");
            switch (type) {
                case "location":
                    Location location = new Location(name, null);
                    location.loadFromHashMap(splitMap.get(name));
                    World.locationMap.put(name, location);
                    break;
                case "room":
                    Room room = new Room(name, null);
                    room.loadFromHashMap(splitMap.get(name));
                    World.roomMap.put(name, room);
                    break;
                case "npc":
                    NPC npc = new NPC(name, null);
                    npc.loadFromHashMap(splitMap.get(name));
                    World.npcMap.put(name, npc);
                    break;
                case "tool":
                    Tool tool = new Tool(name, null);
                    tool.loadFromHashMap(splitMap.get(name));
                    World.toolMap.put(name, tool);
                    break;
                case "event":
                    Event event = null;
                    try {
                        event = new Event(name);
                    } catch (Exception e) {
                        break;
                    }
                    event.loadFromHashMap(splitMap.get(name));
                    event.storeEvent(event.getCmd());
                    break;
                case "exit":
                    Exit exit = new Exit(name, null);
                    exit.loadFromHashMap(splitMap.get(name));
                    World.exitMap.put(name, exit);
                    break;
                case "container":
                    Container container = new Container(name, null);
                    container.loadFromHashMap(splitMap.get(name));
                    World.containerMap.put(name, container);
                    break;
                case "player":
                    playername = name;
                    break;
                default:
                    if (type.equals("")) {
                        System.out.println("Kein Typ Angegeben bei Element " + name);
                    } else {
                        System.out.println("Ungültiger Typ");
                    }
                    break;
            }
        }
        if (playername != null) {
            World.player = new Player(playername, null, null);
            World.player.loadFromHashMap(splitMap.get(playername));
        }

        Checker.check(true);
        try {
            store(Input.getFileType(path,false)+".world");
            close();
            load(Input.getFileType(path,false)+".world");
        }catch(Exception ignore){

        }

    }

    private static int findprevious(String string, int index, char c) {
        try {
            for (int i = index; i >= 0; i--) {
                if (string.charAt(i) == c) {
                    return i;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
        return 0;
    }

    private static int getBrackets(String string, int index) throws BracketException {
        int opened = 0;
        String ret = "";
        char c;
        for (int i = index; i < string.length(); i++) {
            c = string.charAt(i);
            if (c == '{') {
                opened++;
            } else if (c == '}') {
                if (opened == 1) {
                    return i;
                }
                opened--;
            } else if (opened > 0) {
                string += String.valueOf(c);
            }
            if (opened < 0) {
                throw new BracketException();
            }
        }
        return -1;
    }

    private static String cleanString(String string) {
        int start = 0;
        int end = string.length();
        for (int i = 0; i < end; i++) {
            if (string.charAt(i) == '\n' || string.charAt(i) == ' ') {
                start++;
            } else {
                break;
            }
        }
        for (int i = end-1 ; i > start; i--) {
            if (string.charAt(i) == '\n' || string.charAt(i) == ' ') {
                end--;
            } else {
                break;
            }
        }
        string = string.substring(start, end);
        return string;
    }

    public static HashMap<String, String> createMap(String input) throws BracketException {
        HashMap<String, String> map = new HashMap<>();
        char c;
        String key = "";
        String value = "";
        boolean tokey = true;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (tokey && c == '\n') {
                key = "";
            } else if (tokey && c != '=') {
                key = key + c;
            } else if (c == '=' && tokey) {
                tokey = false;
            } else if (c != '\n' && c != '{' && i < input.length()-1) { //!tokey
                value = value + c;
            } else if (c == '{') {
                int temp = i;

                i = getBrackets(input, i);
                value = input.substring(temp + 1, i);
                value = cleanString(value);
                if (!value.equals("")) {
                    map.put(cleanString(key.toLowerCase()), value);
                }
                key = "";
                value = "";
                tokey = true;
            } else {
                if(i==input.length()-1){
                    value = value + c;
                }
                value = cleanString(value);
                if (!value.equals("")) {
                    map.put(cleanString(key.toLowerCase()), value);
                }
                key = "";
                value = "";
                tokey = true;
            }
        }
        return map;
    }

    private static HashMap<String, String> splittxt(String input) throws BracketException {
        HashMap<String, String> split = new HashMap<>();
        String string = "";
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == '{') {
                int temp = i;
                i = getBrackets(input, i);
                string = input.substring(temp + 1, i);
                String key = input.substring(findprevious(input, temp, '\n'), temp);
                key = cleanString(key);
                if (!key.equals("")) {
                    split.put(key.toLowerCase(), string);
                }
            }
        }
        return split;
    }
}
