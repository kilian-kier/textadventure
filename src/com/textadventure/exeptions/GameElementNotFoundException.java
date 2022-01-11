package com.textadventure.exeptions;

public class GameElementNotFoundException extends Exception {
    private final String element;
    private final String type;
    public GameElementNotFoundException(String element,String type) {
        this.element=element;
        this.type=type;
    }

    @Override
    public String getMessage() {
        return element + " in " +type+" Map nicht gefunden";
    }
}
