package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.story.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Enthält eine Liste mit Räumen, die sich an diesem Ort befinden
 */
public class Location extends GameElement implements Serializable, Editable {
    private static final long serialVersionUID = -6064497007961119893L;
    private ArrayList<String> rooms = new ArrayList<>();

    public Location(String name, String description) {
        super(name);
        this.description = description;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(String room) {
        if (!rooms.contains(room)) {
            rooms.add(room);
        }
    }

    public String getRoomIndex(int index) throws IndexOutOfBoundsException {
        return rooms.get(index);
    }

    public boolean removeRoom(String name) {
        return rooms.remove(name);
    }

    public void removeAllRooms() {
        rooms.clear();
    }

    public void removeRoomIndex(int index) throws IndexOutOfBoundsException {
        rooms.remove(index);
    }

    public boolean check() {
        boolean ret = true;
        for (String room : rooms) {
            if (World.roomMap.get(room) == null) {
                System.out.printf("Raum %s von Location %s existiert nicht\n", room, name);
                ret = false;
            } else {
                if (!World.roomMap.get(room).getLocation().equals(name)) {
                    System.out.printf("Raum %s wird von Location %s referenziert aber nicht umgekehrt\n", room, name);
                    ret = false;
                }
            }
        }
        return ret;
    }


    @Override
    public String toString() {
        String string = "";
        string += String.format("Location %s\n", name);
        string += String.format("Beschreibung: %s\n", getDescription());
        string+=String.format("Interactable: %b\n",interactable);
        string += String.format("Räume: %s", rooms.toString());
        return string;
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
    }

    /**
     * Mit dieser Methode kann die Beschreibung einer Location geändert werden. Zudem können Objekte der Klasse Room zur Location hinzugefügt bzw. entfernt werden
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Location " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("LocationEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("LocationEditor", null));
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
                    case "add":
                        command.removeFirst();
                        for (String x : command) {
                            if (World.roomMap.containsKey(x)) this.rooms.add(x);
                            else System.out.println(x + " nicht gefunden");
                        }
                        break;
                    case "rm":
                        command.removeFirst();
                        for (String x : command) {
                            if (!this.rooms.remove(x)) System.out.println(x + " nicht gefunden!");
                        }
                        break;

                    case "set":
                        switch (command.get(1)) {
                            case "description" -> this.description = Input.input("description", false);
                            case "interactable" -> {
                                this.setInteractable(!this.isInteractable());
                                System.out.printf("Interactable wurde auf %b gesetzt\n", this.isInteractable());
                            }
                            default -> System.out.println("command not found");
                        }

                        break;
                    default:
                        System.out.println("command not found");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    Help.help("LocationEditor", command.get(0));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }
}
