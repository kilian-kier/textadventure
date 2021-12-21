package com.textadventure.things;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;

import java.io.Serializable;

/**
 * Eine Art Item ohne Möglichkeit weitere Items zu speichern
 */
public class Tool extends Item implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;

    public Tool(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean check() {
        boolean ret = true;
        if (World.roomMap.get(currentContainer) == null) {
            System.out.printf("Raum %s von Tool %s existiert nicht\n", currentContainer, name);
            ret = false;
        } else {
            if (!World.roomMap.get(currentContainer).getTools().contains(name)) {
                System.out.printf("Raum %s wird von Tool %s referenziert aber nicht umgekehrt\n", currentContainer, name);
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
        string += String.format("Container/Raum: %s", currentContainer);
        return string;
    }
}
