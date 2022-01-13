package com.textadventure.exeptions;

public class CommandSyntaxException extends Exception {
    private final String command;

    public CommandSyntaxException(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        return "Syntax falsch";
    }
}
