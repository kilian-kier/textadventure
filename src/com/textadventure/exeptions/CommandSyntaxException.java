package com.textadventure.exeptions;

public class CommandSyntaxException extends Exception {
    private final String command;
    CommandSyntaxException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        //TODO: Syntax des Befehls angeben
        return super.getMessage();
    }
}
