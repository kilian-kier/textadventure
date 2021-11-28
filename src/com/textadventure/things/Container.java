package com.textadventure.things;

import com.textadventure.interfaces.Containable;

public class Container extends Item {
    Container(String name, String description, String info, Containable currentIn) {
        super(name, description, info,currentIn);
    }
}
