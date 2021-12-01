package com.textadventure.locations;

import com.textadventure.GameElement;

import java.io.Serializable;

public class Exit extends GameElement implements Serializable {
    private Room destination1;
    private Room destination2;
    private Room room;

    public Exit(String name, String description) {
        this.name = name;
        this.description = description; //Beschreibung 1 isch links vor an @ und die Beschreibung 2 ich rechts noch an @
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getDestination2() {
        return destination2;
    }

    public void setDestination2(Room destination2) {
        this.destination2 = destination2;
    }

    public Room getDestination1() {
        return destination1;
    }

    public void setDestination1(Room destination1) {
        this.destination1 = destination1;
    }

    /*@Override
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
        */
    public java.lang.String getName() {
        return this.name;
    }
}
