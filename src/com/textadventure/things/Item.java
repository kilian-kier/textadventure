package com.textadventure.things;

import com.textadventure.GameElement;

import java.io.Serializable;

abstract public class Item  extends GameElement implements Serializable {
    private String currentContainer;


    public Item(String name, String description) {
        super(name);
        this.description = description;
    }


    public String getCurrentContainer() {
        return currentContainer;
    }

    public void setContainer(String container) {
        this.currentContainer = container;
    }

    @Override
    public void investigate() {
        //TODO: comparen
    }
}
