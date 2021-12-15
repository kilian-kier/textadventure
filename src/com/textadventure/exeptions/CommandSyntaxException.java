package com.textadventure.exeptions;

public class CommandSyntaxException extends Exception {
    private final String command;

    public CommandSyntaxException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        //TODO: Syntax des Befehls angeben
        return "Syntax falsch";
    }
}
