package com.textadventure;

import com.textadventure.interfaces.Checkable;
import com.textadventure.interfaces.Editable;
import com.textadventure.interfaces.Interactable;
import com.textadventure.interfaces.Loadable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

/**
 * Ein GameElement ist ein Element welches eine Beschreibung und einen Namen besitzt. Röume, Orte, Items, Exits und NPCs sind Spiel Elemente
 */
public class GameElement implements Serializable, Interactable, Checkable, Loadable {
    private static final long serialVersionUID = 7977434406346025468L;
    protected String description;
    protected String name;
    protected boolean interactable=true;

    //TODO Interactable in toString
    public boolean isInteractable() {
        return interactable;
    }
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    public GameElement(String name) {
        this.name = name.toLowerCase();
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

    public void loadFromHashMap(HashMap<String,String> map){
        if(map.containsKey("description")){
            this.description=map.get("description");
        }
        if(map.containsKey("interactable")){
            try {
                this.interactable = Boolean.parseBoolean(map.get("interactable"));
            }catch(Exception ignore){}
        }
    }

    @Override
    public boolean check(boolean fix) {
        System.out.println("Not yet Implemented");
        return false;
    }
}
