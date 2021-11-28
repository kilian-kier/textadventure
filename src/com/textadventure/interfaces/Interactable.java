package com.textadventure.interfaces;

import com.textadventure.characters.Player;

public interface Interactable {
    void look();
    void use(Player player);
    void talk();
    void investigate();
}