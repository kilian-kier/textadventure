package com.textadventure.exeptions;

public class NoBackException extends Exception {
    private final String name;

    public NoBackException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Der Raum von " + this.name + " kann nicht rückgängig gemacht werden.";
    }
}
