package com.textadventure.story;

import com.textadventure.Event.Event;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.gamemusic.MusicPlayer;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Lädt und Speichert Welt
 */
public class LoadStoreWorld {
    /**
     * Lädt und erstellt die Musikdateien aus der Weltdatei und speichert die Dateipfäde in der Musikliste.
     * Die tempörären Dateien und Ordner werden beim Beenden des Programms gelöscht.
     * @param map HashMap mit den Weltinformationen
     */
    private static void createMusicFiles(HashMap<String, Object> map) {
        try {
            ArrayList<String> keys = (ArrayList<String>) map.get("musicKeys");
            if (keys != null) {
                Path tempDir = Files.createTempDirectory("tmp");
                tempDir.toFile().deleteOnExit();
                for (String key : keys) {
                    byte[] musicBytes = (byte[]) map.get(key);
                    File file = new File(tempDir.toString() + "/" + key + ".mp3");
                    if (file.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(musicBytes);
                        World.musicList.put(key, file.getAbsolutePath());
                        file.deleteOnExit();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
                    fileIn = LoadStoreWorld.class.getResourceAsStream("/world.world");
                else
                    fileIn = new FileInputStream("world.world");
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
    protected static void close() {
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
    protected static void store(String path) {
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
            map.put("musicKeys", new ArrayList<>(World.musicList.keySet()));
            for (String key : World.musicList.keySet()) {
                byte[] musicBytes = MusicPlayer.readFile(World.musicList.get(key));
                map.put(key, musicBytes);
            }

            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.println("Welt wurde gespeichert");

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
