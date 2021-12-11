package com.textadventure.exeptions;

public class ElementNotFoundException extends Exception {
    private final String element;
    private final String type;

    public ElementNotFoundException(String command, String type) {
        this.element = command;
        this.type = type;
    }

    @Override

    public String getMessage() {
        return type + " " + element + " gibt es nicht";
    }
}
