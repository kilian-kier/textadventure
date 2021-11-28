package com.textadventure.characters;

import com.textadventure.GameElement;

public class NPC extends GameElement {
    NPC(String name, String description, String info) {
        this.name = name;
        this.description = description;
        this.info = info;
    }
}
