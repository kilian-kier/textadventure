package com.textadventure.exeptions;

public class BracketException extends Exception{
    @Override
    public String getMessage() {
        return "Ungültige Klammerfolgen";
    }
}
