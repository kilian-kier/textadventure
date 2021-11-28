package com.textadventure.things;

import com.textadventure.interfaces.Containable;

public class Container extends Item {
    Container(String name, String description, Containable currentIn) {
        super(name, description, currentIn);
    }
}
