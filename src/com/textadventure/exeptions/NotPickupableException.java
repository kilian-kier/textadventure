package com.textadventure.exeptions;

public class NotPickupableException extends Exception {
    private final String thing;

    public NotPickupableException(String thing) {
        this.thing = thing;
    }

    @Override
    public String getMessage() {
        return "Du kannst " + thing + " nicht aufheben.";
    }
}