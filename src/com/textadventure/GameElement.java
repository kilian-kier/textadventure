package com.textadventure;

import com.textadventure.interfaces.Interactable;

import java.io.Serializable;

/**
 * Ein GameElement ist ein Element welches eine Beschreibung und einen Namen besitzt. Röume, Orte, Items, Exits und NPCs sind Spiel Elemente
 */
public class GameElement implements Serializable, Interactable {
    protected String description;
    protected String name;

    public GameElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void look() {
        //TODO
    }

    @Override
    public void investigate() {
        //TODO
    }

    public static String[] elementTypes={"npc","exit","location","room","container","tool"};

}
