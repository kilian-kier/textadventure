package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Enthält eine Liste mit Ausgängen, Tools, Containern, NPCs und Orten.
 */
public class Room extends GameElement implements Serializable, Editable {
    private static final long serialVersionUID = -4799777339562754102L;

    private final ArrayList<String> exits = new ArrayList<>();
    private final Container tools = new Container(this.name, "Dinge, die sich in diesem Raum (" + this.name + ") befinden");
    private final ArrayList<String> container = new ArrayList<>();
    private final ArrayList<String> npcs = new ArrayList<>();
    private String location;
    private String previousMusic;
    private boolean eventMusic;
    private int musicFrame = 0;
    private String music;

    public String getMusic() {
        return music;
    }

    public String getPreviousMusic() {
        return previousMusic;
    }

    public int getMusicFrame() {
        return musicFrame;
    }

    public void setMusicFrame(int frame) {
        this.musicFrame = frame;
    }

    public boolean isEventMusic() {
        return eventMusic;
    }

    public void setMusic(String music, boolean event) {
        eventMusic = event;
        if (event) {
            this.previousMusic = this.music;
        }
        else {
            this.previousMusic = null;

        }
        this.music = music;
    }

    public Room(String name, String description) {
        super(name);
        this.description = description;
        World.containerMap.put(this.name, tools);
    }

    public java.lang.String getName() {
        return name;
    }

    public boolean removeNpc(String npc) {
        return npcs.remove(npc);
    }

    public boolean removeExit(String exit) {
        return exits.remove(exit);
    }


    public void addExit(String exit) {
        exits.add(exit);
    }

    public ArrayList<String> getExits() {
        return this.exits;
    }

    public boolean removeToolsKey(String name) {
        return tools.removeTool(name);
    }

    /**
     * @return die Liste der Tools, die sich in diesem Raum befinden
     */
    public ArrayList<String> getTools() {
        return this.tools.getTools();
    }

    /**
     * @return das Objekt des Containers, in dem die Tools von diesem Raum sind
     */
    public Container getToolsContainer() {
        return this.tools;
    }

    /**
     * @param name der Name des gesuchten Tools
     * @return das Objekt des Tools
     * @throws ItemNotFoundException wenn sich das Tool nicht in diesem Raum befindet
     */
    public Tool getTool(String name) throws ItemNotFoundException {
        if (tools.getTools().contains(name)) {
            return tools.getTool(name);
        } else
            throw new ItemNotFoundException(name);
    }

    /**
     * speichert das Tool in kleingeschiebener Form in diesem Raum
     *
     * @param tool der Name des zu speichernden Tools
     */
    public void addTool(String tool) {
        String str = tool.toLowerCase();
        if (!tools.getTools().contains(str)) {
            tools.addTool(str);
        }
    }

    /**
     * @return die Liste der Containern zurück, die sich in diesem Raum befinden
     */
    public ArrayList<String> getContainers() {
        return this.container;
    }

    /**
     * @param name der Name des gesuchten Containers
     * @return das Objekt des Containers
     * @throws ItemNotFoundException wenn der Container sich nicht in diesem Raum befindet oder es ihn nicht gibt
     */
    public Container getContainer(String name) throws ItemNotFoundException {
        if (!container.contains(name))
            throw new ItemNotFoundException(name);
        Container ret = World.containerMap.get(name);
        if (ret == null)
            throw new ItemNotFoundException(name);
        return ret;
    }

    public boolean removeContainer(String string) {
        return container.remove(string);
    }

    public void addNpcs(String item) {
        if (!npcs.contains(item)) {
            npcs.add(item);
        }
    }

    public ArrayList<String> getNpcs() {
        return this.npcs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public void investigate() {
        System.out.println("Ausgänge:");
        for (String exit : exits) {
            System.out.println(exit);
        }
    }

    public void addContainer(String name) {
        String tmp = name.toLowerCase();
        if (!container.contains(tmp)) {
            container.add(tmp);
        }
    }

    /**
     * Ändert den Ort des Raumes
     *
     * @param newLocationString Neuer Ort
     * @throws ItemNotFoundException Wenn neuer Ort nicht existiert
     * @throws NullPointerException  Wenn ungültige Parameter übergeben werden
     */
    public void changeLocation(String newLocationString) throws ItemNotFoundException, NullPointerException {
        Location newLocation = World.locationMap.get(newLocationString);
        if (this.location != null) {
            Location oldLocation = World.locationMap.get(location);
            if (oldLocation.getRooms().contains(this.name)) {
                oldLocation.getRooms().remove(name);
                newLocation.addRoom(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        } else {
            newLocation.addRoom(this.name);
        }
        this.name = newLocationString;
    }

    public boolean check(boolean fix) {
        boolean ret = true;
        try {
            if (World.locationMap.get(location) == null) {
                System.out.printf("Location %s von Raum %s existiert nicht oder ist noch nicht gesetzt\n", location, name);
                ret = false;
            } else {
                if (!World.locationMap.get(location).getRooms().contains(name)) {
                    System.out.printf("Location %s wird von Raum %s referenziert aber nicht umgekehrt\n", location, name);
                    if(fix){
                        World.locationMap.get(location).addRoom(name);
                        System.out.println("Fehler ausgebessert");
                    }
                    ret = false;
                }
            }
        } catch (NullPointerException ignored) {
        }
        for (String tool : tools.getTools()) {
            if (World.toolMap.get(tool) == null) {
                System.out.printf("Tool %s von Raum %s existiert nicht\n", tool, name);
                ret = false;
            } else {
                if (!World.toolMap.get(tool).getCurrentContainer().equals(name)) {
                    System.out.printf("Tool %s wird von Raum %s referenziert aber nicht umgekehrt\n", tool, name);
                    if(fix){
                        World.toolMap.get(tool).setContainer(name);
                        System.out.println("Fehler ausgebessert");
                    }
                    ret = false;
                }
            }
        }
        for (String cont : container) {
            if (World.containerMap.get(cont) == null) {
                System.out.printf("Container %s von Raum %s existiert nicht\n", cont, name);
                ret = false;
            } else {
                if (!World.containerMap.get(cont).getCurrentContainer().equals(name)) {
                    System.out.printf("Container %s wird von Raum %s referenziert aber nicht umgekehrt\n", cont, name);
                    if(fix){
                        World.containerMap.get(cont).setContainer(name);
                        System.out.println("Fehler ausgebessert");
                    }
                    ret = false;
                }
            }
        }
        for (String npc : npcs) {
            if (World.npcMap.get(npc) == null) {
                System.out.printf("NPC %s von Raum %s existiert nicht\n", npc, name);
                ret = false;
            } else {
                if (!World.npcMap.get(npc).getRoom().equals(name)) {
                    System.out.printf("NPC %s wird von Raum %s referenziert aber nicht umgekehrt\n", npc, name);
                    if(fix){
                        World.npcMap.get(npc).setRoom(name);
                        System.out.println("Fehler ausgebessert");
                    }
                    ret = false;
                }
            }
        }
        try {
            for (String exit : exits) {
                if (World.exitMap.get(exit) == null) {
                    System.out.printf("Exit %s von Raum %s existiert nicht\n", exit, name);
                    ret = false;
                } else {
                    if (!World.exitMap.get(exit).getDestination1().equals(name) && !World.exitMap.get(exit).getDestination2().equals(name)) {
                        System.out.printf("Exit %s wird von Raum %s referenziert aber nicht umgekehrt\n", exit, name);
                        ret = false;
                    }
                }
            }
        }catch(Exception ignore){}
        return ret;
    }


    @Override
    public String toString() {
        String string = "";
        string += String.format("Raum %s\n", name);
        string += String.format("Beschreibung: %s\n", getDescription());
        string+=String.format("Interactable: %b\n",interactable);
        string += String.format("Location: %s\n", getLocation());
        string += String.format("Musik: %s\n", music);
        string += String.format("Tools: %s\n", tools.getTools() != null ? tools.getTools().toString() : null);
        string += String.format("Npcs: %s\n", npcs);
        string += String.format("Container: %s\n", container);
        string += String.format("Exits: %s", exits);
        return string;
    }


    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
        if(map.containsKey("music")){
            this.music=map.get("music");
            World.addMusic(music);
        }
        if(map.containsKey("location")){
            this.location=map.get("location");
        }
    }

    /**
     * Mit dieser Methode können Beschreibung und Location eines Rooms geändert werden. Zudem können Container, Tools, NOCs und Exits ninzugefügt bzw. entfernt werden.
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Room " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("RoomEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("RoomEditor", null));
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "back":
                        return true;
                    case "show":
                        System.out.println(this);
                        break;
                    case "set":
                        switch (command.get(1)) {
                            case "description" -> this.description = Input.input("description", false);
                            case "location" -> {
                                if (World.locationMap.containsKey(command.get(2))) this.location = command.get(2);
                                else System.out.println(command.get(2) + " nicht gefunden");
                            }
                            case "interactable" -> {
                                this.setInteractable(!this.isInteractable());
                                System.out.printf("Interactable wurde auf %b gesetzt\n", this.isInteractable());
                            }
                            case "music" -> {
                                if (command.size() > 2) {
                                    this.setMusic(command.get(2), false);
                                    World.addMusic(command.get(2));
                                } else {
                                    if (World.explorer) {
                                        FileDialog fd = new FileDialog(new Frame(), "Musikdatei laden", FileDialog.LOAD);
                                        fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
                                        fd.setFile("*.mp3");
                                        fd.setVisible(true);
                                        String filename = fd.getFile();
                                        if (filename != null) {
                                            this.setMusic(filename, false);
                                            World.addMusic(filename);
                                        } else
                                            System.out.println("Keine Datei ausgewählt");
                                    } else {
                                        String filename = Input.input("Musikdatei", false);
                                        if (filename != null) {
                                            File musicFile = new File(filename);
                                            if (musicFile.exists()) {
                                                this.setMusic(musicFile.getName(), false);
                                                World.addMusic(musicFile.getName());
                                            } else
                                                System.out.println("Datei nicht gefunden");
                                        }
                                    }
                                }
                            }
                            default -> System.out.println("command not found");
                        }
                        break;
                    case "add":
                        command.removeFirst();
                        switch (command.get(0)) {
                            case "exit":
                                command.removeFirst();
                                for (String x : command) {
                                    if (World.exitMap.containsKey(x)) this.addExit(x);
                                    else System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "npc":
                                command.removeFirst();
                                for (String x : command) {
                                    if (World.npcMap.containsKey(x)) this.addNpcs(x);
                                    else System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "tool":
                                command.removeFirst();
                                for (String x : command) {
                                    if (World.toolMap.containsKey(x)) this.addTool(x);
                                    else System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "container":
                                command.removeFirst();
                                for (String x : command) {
                                    if (World.containerMap.containsKey(x)) this.addContainer(x);
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
                                    if (!this.removeExit(x)) System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "npc":
                                command.removeFirst();
                                for (String x : command) {
                                    if (!this.removeNpc(x)) System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "tool":
                                command.removeFirst();
                                for (String x : command) {
                                    if (!this.removeToolsKey(x)) System.out.println(x + " nicht gefunden");
                                }
                                break;
                            case "container":
                                command.removeFirst();
                                for (String x : command) {
                                    if (!this.removeContainer(x)) System.out.println(x + " nicht gefunden");
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
            } catch (IndexOutOfBoundsException | URISyntaxException e) {
                try {
                    Help.help("RoomEditor", command.get(0));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }

}
