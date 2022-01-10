package com.textadventure.things;

import com.textadventure.exeptions.NoHelpFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Editable;
import com.textadventure.story.World;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Eine Art Item ohne Möglichkeit weitere Items zu speichern
 */
public class Tool extends Item implements Serializable, Editable {
    private static final long serialVersionUID = 9672883399970462L;

    public Tool(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean check(boolean fix) {
        boolean ret = true;
        if (World.containerMap.get(currentContainer) == null) {
            System.out.printf("Raum %s von Tool %s existiert nicht\n", currentContainer, name);
            ret = false;
        } else {
            if (!World.containerMap.get(currentContainer).getTools().contains(name)) {
                System.out.printf("Raum %s wird von Tool %s referenziert aber nicht umgekehrt\n", currentContainer, name);
                if(fix){
                    World.containerMap.get(currentContainer).addTool(name);
                    System.out.println("Fehler ausgebessert");
                }
                ret = false;
            }
        }
        return ret;
    }


    @Override
    public String toString() {
        String string = "";
        string += String.format("Tool %s\n", name);
        string += String.format("Beschreibung: %s\n", description);
        string+=String.format("Interactable: %b\n",interactable);
        string += String.format("Container/Raum: %s\n", currentContainer);
        return string;
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        super.loadFromHashMap(map);
        if(map.containsKey("container")){
            currentContainer=map.get("container");
        }
    }

    /**
     * Mit dieser Methode können Beschreibung und Raum eines Tools geändert werden
     *
     * @return Gibt true zurück, wenn der command "back" eingegeben wurde
     */
    @Override
    public boolean edit() {
        while (true) {
            System.out.print("Tool " + this.name + ">>");
            LinkedList<String> command = Input.getCommand();
            try {
                switch (command.get(0)) {
                    case "help":
                        try {
                            if (command.size() > 1) {
                                System.out.println(Help.help("ToolEditor", command.get(1)));
                            } else {
                                System.out.println(Help.help("ToolEditor", null));
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
                            case "container" -> {
                                if (World.containerMap.containsKey(command.get(2))) this.currentContainer = command.get(2);
                                else System.out.println(command.get(2) + "nicht gefunden");
                            }
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
                    System.out.println(Help.help("ToolEditor", command.get(0)));
                } catch (NoHelpFoundException ignored) {
                }
            }
        }
    }

}
