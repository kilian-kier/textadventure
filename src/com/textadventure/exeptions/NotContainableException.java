package com.textadventure.exeptions;

public class NotContainableException extends Exception{
    private final String container;
    private final String thing;
    NotContainableException(String container, String thing) {
        this.container = container;
        this.thing = thing;
    }

    @Override
    public String getMessage() {
        return container + " kann " + thing + " nicht aufnehmen";
    }
}
