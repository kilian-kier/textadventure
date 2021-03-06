package com.textadventure.characters;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.interfaces.Loadable;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;
import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.interfaces.RoomChangeable;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Player extends GameElement implements RoomChangeable, Editable, Loadable {
    private static final long serialVersionUID = 524105953116492598L;
    private final Container inventory = new Container("Rucksack", "Das ist dein Rucksack. Hier kannst du alle Dinge finden, die du besitzt.");
    private Room currentRoom;
    private Room previousRoom;

    /**
     * Ein Spieler, der sich in der Welt befindet
     *
     * @param name        Name des Spielers
     * @param description Beschreibung des Spielers
     * @param currentRoom Raum, in dem der Spieler startet
     */
    public Player(String name, String description, Room currentRoom) {
        super(name);
        this.description = description;
        this.currentRoom = currentRoom;
        this.previousRoom = null;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Sucht den Ausgang und falls der Spieler sich in einen der Zielräume befindet, wird er in den anderen Raum gewechselt
     *
     * @param exit der Name des Ausgangs
     * @throws ExitNotFoundException, wenn es den Ausgang nicht gibt
     */
    @Override
    public void changeRoom(String exit) throws ExitNotFoundException {
        if (!currentRoom.getExits().contains(exit))
            throw new ExitNotFoundException(exit);
        Exit temp = World.exitMap.get(exit);
        if (temp == null)
            throw new ExitNotFoundException(exit);
        if (temp.getDestination1().equals(currentRoom.getName())) {
            Room tmp = currentRoom;
            currentRoom = World.roomMap.get(temp.getDestination2());
            previousRoom = tmp;
            return;
        } else if (temp.getDestination2().equals(currentRoom.getName())) {
            Room tmp = currentRoom;
            currentRoom = World.roomMap.get(temp.getDestination1());
            previousRoom = tmp;
            return;
        }
        throw new ExitNotFoundException(exit);
    }

    /**
     * Ändert den Raum des Spielers, in dem er sich zuvor befand
     *
     * @throws NoBackException, wenn der Spieler zuvor in keinem Raum war
     */
    @Override
    public void goBack() throws NoBackException {
        if (previousRoom != null) {
            Room temp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = temp;
        } else {
            throw new NoBackException(this.name);
        }
    }

    @Override
    public void investigate() {
        System.out.println("Rucksack:");
        for (String item : inventory.getTools()) {
            System.out.println(item);
        }
    }

    /**
     * @return das Invantar/Rucksack des Spielers als ArrayList
     */
    public ArrayList<String> getTools() {
        return this.inventory.getTools();
    }

    public Tool getTool(String name) throws ItemNotFoundException {
        if (inventory.getTools().contains(name)) {
            return inventory.getTool(name);
        } else
            throw new ItemNotFoundException(name);
    }

    public Container getToolsContainer() {
        return this.inventory;
    }

    public void addTool(String tool) {
        inventory.addTool(tool);
    }

    public void removeTool(String tool) {
        inventory.removeTool(tool);
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("Name %s\n", this.name);
        string += String.format("Beschreibung: %s\n", this.description);
        if (this.currentRoom == null) string += String.format("Raum: null");
        else string += String.format("Raum: %s", this.currentRoom.getName());
        return string;
    }
    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("room")){
            currentRoom=World.roomMap.get(map.get("room"));
        }
        if(map.containsKey("inventory")){
            try {
                inventory.getTools().addAll(Input.splitInput(map.get("inventory")));
            }catch(Exception ignore){}
        }
    }

    /**
     * Mit dieser Methode kann der Player bearbeitet werden
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Player " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("PlayerEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("PlayerEditor", null));
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
                            case "room" :
                                if (World.roomMap.containsKey(command.get(2)))
                                    this.currentRoom = World.roomMap.get(command.get(2));
                                else System.out.println(command.get(2) + " nicht gefunden");
                            break;
                            case "interactable":
                                this.setInteractable(!this.isInteractable());
                                System.out.printf("Interactable wurde auf %b gesetzt\n", this.isInteractable());
                            break;
                            case "description": this.description = Input.input("description", false); break;
                            default: System.out.println("command not found");
                        }
                        break;
                    default:
                        System.out.println("command not found");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    Help.help("PlayerEditor", command.get(0));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }
}
