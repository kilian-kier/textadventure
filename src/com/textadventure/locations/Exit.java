package com.textadventure.locations;

import com.textadventure.characters.Player;
import com.textadventure.interfaces.Interactable;

public class Exit implements Interactable {
    private java.lang.String name;
    private Room destination;
    private java.lang.String description;

    public Exit(java.lang.String name, Room destination, java.lang.String description) {
        this.name = name;
        this.destination = destination;
        this.description = description;
    }


    public Room getDestination() {
        return this.destination;
    }

    @Override
    public void look() {
        System.out.println("Da ist ein Ausgang, der nach " + this.destination.getName() + " führt.");
    }

    @Override
    public void use(Player player) {
        player.changeRoom(this.name);
    }

    @Override
    public void talk() {
        System.out.println("Du bist ja lustig im Kopf, erwartest du dir eine Antwort von einem Ausgang?");
    }

    @Override
    public void investigate() {
        System.out.println(this.description);
    }

    public java.lang.String getName() {
        return this.name;
    }
}
