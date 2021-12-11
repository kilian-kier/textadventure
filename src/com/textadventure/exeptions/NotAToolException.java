package com.textadventure.exeptions;

public class NotAToolException extends Exception {
    private final String element;
    public NotAToolException(String element) {
        this.element = element;
    }
    @Override
    public String getMessage() {
        return this.element + "muss ein Tool sein";
    }
}
