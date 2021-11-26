package com.textadventure.exeptions;

public class NotFightableException extends Exception{
    private final String toFight;
    NotFightableException(String toFight){
        this.toFight = toFight;
    }

    @Override
    public String getMessage() {
        return "Warum willst du " + toFight + " angreifen?";
    }
}
