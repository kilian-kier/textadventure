package com.textadventure.exeptions;

public class ElementNotFoundException extends Exception {
    private final String element;

    public ElementNotFoundException(String command) {
        this.element = command;
    }

    @Override

    public String getMessage() {
        return "Das Element " + element + " gibt es nicht";
    }
}
