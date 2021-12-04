package com.textadventure.Event;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class Event implements Serializable {
    private String name;
    private Collection<String> cmd;
    private Collection<Diff> differences=new LinkedList<>();
    private String fastaccess="";

    public String getName(){
        return name;
    }
    public void setName( String name){
        this.name =name;
    }
    public Collection<String> getCmd() {
        return cmd;
    }

    public String setCmd(Collection<String> cmd) {
        this.cmd = cmd;
        return stringForHash(cmd);
    }
    private String stringForHash(Collection<String> strings){
        String temp;
        temp="";
        for (String i:strings) {
            temp=temp+i;
        }
        temp=temp.toLowerCase();
        return temp;
    }
    public void addDiff(Diff diff){
        differences.add(diff);
    }
    public void applyDiff(Diff diff){

    }
    public static boolean execEvent(Collection<String> args){

        return false;
    }
}