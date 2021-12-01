package com.textadventure.Story;

import java.util.LinkedList;

public class Event {
    private String name;
    private LinkedList<String> cmd;
    private String fastaccess="";
    public String getName(){
        return name;
    }
    public void setName( String name){
        this.name =name;
    }

    public LinkedList<String> getCmd() {
        return cmd;
    }

    public String setCmd(LinkedList<String> cmd) {
        cmd.sort(String.CASE_INSENSITIVE_ORDER);
        this.cmd = cmd;
        fastaccess="";
        for (String i:cmd) {
            fastaccess=fastaccess+i;
        }
        return fastaccess;
    }
}
