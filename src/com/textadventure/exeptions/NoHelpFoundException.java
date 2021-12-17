package com.textadventure.exeptions;

public class NoHelpFoundException extends Exception{
    String type;
    String param;
    public NoHelpFoundException(String type, String param){
        this.type=type;
        this.param=param;
    }
    @Override
    public String getMessage() {
        return String.format("Keine Hilfe mit dem Typ %s mit den Namen %s gefunden",type,param);
    }
}
