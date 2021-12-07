package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.EventExistsException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class Event implements Serializable {
    //TODO Eingobe va Events und die Diffs de sebm drin san
    private String name;
    private Collection<String> cmd;
    private final HashMap<String,Diff> differences=new HashMap<>();
    private Collection<String> dependent;
    private boolean happened=false;
    public Event(String name) throws EventExistsException {
        setName(name);
    }
    public String getName(){
        return name;
    }
    public void setName( String name){
        String accessKey= World.eventKeyMap.get(this.name);
        World.eventKeyMap.remove(this.name);
        World.eventKeyMap.put(name,accessKey);
        this.name =name;
    }
    public Collection<String> getCmd() {
        return cmd;
    }

    public String storeEvent(Collection<String> cmd) {
        this.cmd = cmd;
        String accessKey = stringForHash(cmd);
        World.eventMap.remove(accessKey);
        World.eventMap.put(accessKey,this);
        World.eventKeyMap.put(name,accessKey);
        return accessKey;
    }
    public void rmEvent(){
        World.eventMap.remove(World.eventKeyMap.get(name));
        World.eventKeyMap.remove(name);
    }
    private static String stringForHash(Collection<String> strings){
        String temp;
        temp="";
        for (String i:strings) {
            temp+=i;
        }
        temp=temp.toLowerCase();
        return temp;
    }
    public void addDiff(Diff diff){
        differences.put(diff.getName(),diff);
    }
    public Diff getDiff(String diff){
        return differences.get(diff);
    }
    public void rmDiff(String diff){
        differences.remove(diff);
    }
    private boolean applyDiffsToWorld(){
        for (String i:dependent) {
            try{
                if(!World.eventMap.get(i).isHappened()){
                    return false;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        for (Diff diff:differences.values()) {
            try {
                diff.applyDiffToWorld();
            }catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        setHappened(true);
        return true;
    }

    public boolean exec() {
        return applyDiffsToWorld();
    }

    public static boolean execEvent(Collection<String> args){
        String hash = stringForHash(args);
        if(World.eventMap.containsKey(hash)){
            return World.eventMap.get(hash).applyDiffsToWorld();
        }
        return false;
    }


    public Collection<String> getDependent() {
        return dependent;
    }

    public void setDependent(Collection<String> dependent) {
        this.dependent = dependent;
    }

    public boolean isHappened() {
        return happened;
    }

    public void setHappened(boolean happened) {
        this.happened = happened;
    }
}