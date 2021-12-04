package com.textadventure.Story;

import com.textadventure.characters.NPC;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.*;
import java.util.HashMap;

public class LoadStoreWorld {
    public static void load(String path) throws FileNotFoundException {
        try {
            FileInputStream fileIn;
            ObjectInputStream in;

            fileIn = new FileInputStream(path + "rooms.tadv");
            in = new ObjectInputStream(fileIn);
            World.roomMap = (HashMap<String, Room>) in.readObject();
            in.close();
            System.out.println("Rooms wurden geladen");
            fileIn.close();

            fileIn = new FileInputStream(path + "exits.tadv");
            in = new ObjectInputStream(fileIn);
            World.exitMap = (HashMap<String, Exit>) in.readObject();
            in.close();
            System.out.println("Exits wurden geladen");
            fileIn.close();

            fileIn = new FileInputStream(path + "locations.tadv");
            in = new ObjectInputStream(fileIn);
            World.locationMap = (HashMap<String, Location>) in.readObject();
            in.close();
            System.out.println("Locations wurden geladen");
            fileIn.close();

            fileIn = new FileInputStream(path + "tools.tadv");
            in = new ObjectInputStream(fileIn);
            World.toolMap = (HashMap<String, Tool>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Tools wurden geladen");

            fileIn = new FileInputStream(path + "container.tadv");
            in = new ObjectInputStream(fileIn);
            World.containerMap = (HashMap<String, Container>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Container wurden geladen");

            fileIn = new FileInputStream(path + "npcs.tadv");
            in = new ObjectInputStream(fileIn);
            World.npcMap = (HashMap<String, NPC>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("NPCs wurden geladen");

            fileIn = new FileInputStream(path + "events.tadv");
            in = new ObjectInputStream(fileIn);
            World.eventMap = (HashMap<String, Event>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Events wurden geladen");

        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Klassen nicht gefunden");
            c.printStackTrace();
        }
    }

    public static void store(String path) {
        try {
            FileOutputStream fileOut;
            ObjectOutputStream out;
            fileOut = new FileOutputStream(path + "rooms.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.roomMap);
            out.close();
            fileOut.close();
            System.out.println("Rooms wurden gespeichert");

            fileOut = new FileOutputStream(path + "exits.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.exitMap);
            out.close();
            fileOut.close();
            System.out.println("Exits wurden gespeichert");

            fileOut = new FileOutputStream(path + "locations.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.locationMap);
            out.close();
            fileOut.close();
            System.out.println("Locations wurden gespeichert");

            fileOut = new FileOutputStream(path + "tools.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.toolMap);
            out.close();
            fileOut.close();
            System.out.println("Tools wurden gespeichert");

            fileOut = new FileOutputStream(path + "container.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.containerMap);
            out.close();
            fileOut.close();
            System.out.println("Container wurden gespeichert");

            fileOut = new FileOutputStream(path + "npcs.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.npcMap);
            out.close();
            fileOut.close();
            System.out.println("NPCs wurden gespeichert");

            fileOut = new FileOutputStream(path + "events.tadv");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(World.eventMap);
            out.close();
            fileOut.close();
            System.out.println("Events wurden gespeichert");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
