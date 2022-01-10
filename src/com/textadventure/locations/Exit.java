package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.story.World;
import com.textadventure.exeptions.ItemNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Ein Ausgang enthält zwei Räume (destination1/2) zwischen denen er sich befinded. Es gibt eine Doppelte Beschreibung welche in der Mitte mit einem
 * @ getrennt wird.
 */
public class Exit extends GameElement implements Serializable, Editable {
    private static final long serialVersionUID = -5624726393622315238L;
    private String destination1;
    private String destination2;

    public Exit(String name, String description) {
        super(name);
        this.description = description; //Beschreibung 1 isch links vor an @ und die Beschreibung 2 ich rechts noch an @
    }

    /**
     * Ändert einen der Räume
     * @param newRoomString neuer Raum
     * @param oneOrTwo Raum bei destination 1 (false), oder 2 (true)
     * @throws ItemNotFoundException Wenn der neue Raum nicht existiert
     * @throws NullPointerException Wenn ungültige Parameter übergeben werden
     */
    public void changeDestination(String newRoomString, boolean oneOrTwo) throws ItemNotFoundException, NullPointerException{
        Room newRoom= World.roomMap.get(newRoomString);
        String destination=destination2;
        if(!oneOrTwo){
            destination=destination1;
        }
        if(destination!=null) {
            Room oldRoom = World.roomMap.get(destination);
            if (oldRoom.getExits().contains(this.name)) {
                oldRoom.getExits().remove(name);
                newRoom.addExit(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newRoom.addExit(this.name);
        }
        if(!oneOrTwo){
            destination1=newRoomString;
        }else{
            destination2=newRoomString;
        }
    }

    public void setDescription1(String description1){
        description = description1+"@"+getDescription2();
    }
    public void setDescription2(String description2){
        description = getDescription1()+"@"+description2;
    }
    public String getDescription1(){
        try {
            return description.split("[@]")[0];
        }catch(Exception e){
            return description;
        }
    }
    public String getDescription2(){
        try {
            return description.split("[@]")[1];
        }catch(Exception e){
            return description;
        }
    }
    public String getDestination2() {
        return destination2;
    }

    public void setDestination2(String destination2) {
        this.destination2 = destination2;
    }

    public String getDestination1() {
        return destination1;
    }

    public void setDestination1(String destination1) {
        this.destination1 = destination1;
    }

    @Override
    public void look() {
        if(World.player.getCurrentRoom().getName().equals(destination1)){
            System.out.println("Da ist ein Ausgang, der nach " + destination2 + " führt.");
        }else{
            System.out.println("Da ist ein Ausgang, der nach " + destination1 + " führt.");
        }
    }

    @Override
    public void investigate() {
        System.out.println(this.description);
    }

    public java.lang.String getName() {
        return this.name;
    }

    public boolean check(boolean fix) {
        boolean ret = true;
        if (World.roomMap.get(destination1) == null) {
            System.out.printf("Destination 1 %s von Exit %s existiert nicht\n", destination1, name);
            ret = false;
        } else {
            if (!World.roomMap.get(destination1).getExits().contains(name)) {
                System.out.printf("Raum %s wird von Exit %s als Destination 1 referenziert aber nicht umgekehrt\n", destination1, name);
                if (fix) {
                    World.roomMap.get(destination1).addExit(name);
                    System.out.println("Fehler ausgebessert");
                }
                ret = false;
            }
            if (World.roomMap.get(destination2) == null) {
                System.out.printf("Destination 2 %s von Exit %s existiert nicht\n", destination2, name);
                ret = false;
            } else {
                if (!World.roomMap.get(destination2).getExits().contains(name)) {
                    System.out.printf("Raum %s wird von Exit %s als Destination 2 referenziert aber nicht umgekehrt\n", destination2, name);
                    if (fix) {
                        World.roomMap.get(destination2).addExit(name);
                        System.out.println("Fehler ausgebessert");
                    }
                    ret = false;
                }
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Exit %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Interactable: %b\n",interactable);
        string+=String.format("Destination1: %s\n",destination1);
        string+=String.format("Destination2: %s",destination2);
        return string;
    }


    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
        if(map.containsKey("destination1")){
            destination1=map.get("destination1");
        }
        if(map.containsKey("destination2")){
            destination2=map.get("destination2");
        }
        if(map.containsKey("description1")){
            setDescription1(map.get("description1"));
        }
        if(map.containsKey("description2")){
            setDescription2(map.get("description2"));
        }
    }

    /**
     * Mit dieser Methode kann die Beschreibung eines Exits geändert werden. Zudem können die beiden Rooms des Exits gesetzt werden.
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Exit " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("ExitEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("ExitEditor", null));
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
                            case "destination1" -> {
                                if (World.roomMap.containsKey(command.get(2))) this.destination1 = command.get(2);
                                else System.out.println(command.get(2) + " nicht gefunden");
                            }
                            case "destination2" -> {
                                if (World.roomMap.containsKey(command.get(2))) this.destination2 = command.get(2);
                                else System.out.println(command.get(2) + " nicht gefunden");
                            }
                            case "interactable" -> {
                                this.setInteractable(!this.isInteractable());
                                System.out.printf("Interactable wurde auf %b gesetzt\n", this.isInteractable());
                            }
                            case "description" -> this.description = Input.input("description", false);
                            default -> System.out.println("command not found");
                        }
                        break;
                    default:
                        System.out.println("command not found");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    Help.help("ExitEditor", command.get(0));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }
}
