package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.characters.Player;
import com.textadventure.interfaces.Interactable;

public class Exit extends GameElement implements Interactable {
    private Room destination;

    public Exit(String name, String description, String info, Room destination) {
        this.name = name;
        this.destination = destination;
        this.info = info;
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
