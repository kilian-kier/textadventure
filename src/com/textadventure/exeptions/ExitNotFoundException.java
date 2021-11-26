package com.textadventure.exeptions;

public class ExitNotFoundException extends Exception{
    private final String exit;
    public ExitNotFoundException(String exit) {
        this.exit = exit;
    }
    @Override
    public String getMessage() {
        return "Welche Karte benutzt du, denn nach" + exit + " gibt es nichts!";
    }
}
