package com.textadventure.exeptions;

public class ElementExistsException extends Exception{
    private final String element;

    public ElementExistsException(String command) {
        this.element = command;
    }

    @Override

    public String getMessage() {
        return "Das Element " + element + " existiert bereits";
    }

}
