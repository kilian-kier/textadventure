package com.textadventure.exeptions;

public class EventExistsException extends Exception{
    private final String name;
    public EventExistsException(String name){
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("Event mit dem Namen %s existiert bereits",name);
    }
}
