package com.textadventure.Story;

import com.textadventure.Event.Event;
import com.textadventure.characters.NPC;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Lädt und Speichert Welt
 */
public class LoadStoreWorld {
    /**
     * Lädt Welt von Festplatte
     * @param path Datei mit gespeicherter Welt
     * @throws FileNotFoundException Wenn die Datei nicht gefunden wird
     */
    protected static void load(String path) throws FileNotFoundException {
        try {
            FileInputStream fileIn;
            ObjectInputStream in;

            fileIn = new FileInputStream( path);
            in = new ObjectInputStream(fileIn);
            HashMap<String, Object> map= (HashMap<String, Object>) in.readObject();
            in.close();

            World.roomMap=(HashMap<String, Room>) map.get("rooms");
            World.locationMap=(HashMap<String, Location>) map.get("locations");
            World.toolMap=(HashMap<String, Tool>) map.get("tools");
            World.exitMap=(HashMap<String, Exit>) map.get("exits");
            World.npcMap=(HashMap<String, NPC>) map.get("npcs");
            World.containerMap=(HashMap<String, Container>) map.get("container");
            World.eventMap=(HashMap<String, Event>) map.get("events");
            World.eventKeyMap=(HashMap<String, String>) map.get("eventkeys");

            System.out.println("Welt wurde geladen");
            fileIn.close();


        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Klassen nicht gefunden");
            c.printStackTrace();
        }
    }

    /**
     * Speichert die HashMaps der Welt mit Serializable in eine Datei
     * @param path Datei zum Speichern
     */
    protected static void store(String path) {
        try {
            FileOutputStream fileOut;
            ObjectOutputStream out;
            fileOut = new FileOutputStream(path );
            out = new ObjectOutputStream(fileOut);

            HashMap<String, Object> map= new HashMap<>();
            map.put("rooms",World.roomMap);
            map.put("locations",World.locationMap);
            map.put("tools",World.toolMap);
            map.put("exits",World.exitMap);
            map.put("npcs",World.npcMap);
            map.put("container",World.containerMap);
            map.put("events",World.eventMap);
            map.put("eventkeys",World.eventKeyMap);
            //map.put("player",World.player); ????

            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.println("Welt wurde gespeichert");

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
