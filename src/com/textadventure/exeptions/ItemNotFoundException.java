package com.textadventure.exeptions;

public class ItemNotFoundException extends Exception {
    private final String item;
    ItemNotFoundException(String item) {
        this.item = item;
    }

    @Override
    public String getMessage() {
        return "Es gibt hier kein/e " + item;
    }
}
