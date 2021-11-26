package com.textadventure.exeptions;

public class CommandNotFoundException extends Exception {
    private final String command;
    CommandNotFoundException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        return "Den Befehl " + command + " gibt es nicht";
    }
}
