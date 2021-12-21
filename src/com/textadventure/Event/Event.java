package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.exeptions.EventExistsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Event implements Serializable {
    private static final long serialVersionUID = 2552220530382600393L;
    private String name;
    private Collection<String> cmd;
    private final HashMap<String,Diff> differences=new HashMap<>();
    private Collection<String> dependent;
    private boolean happened=false;
    private boolean once=true;

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    //TODO events kennen oefter ausgfiehrt werden
    private String info=null;
    private String room;


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
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
    public Diff getDiff(String diffstring) {
            return differences.get(diffstring);
    }
    public void rmDiff(String diffstring) throws  ElementNotFoundException{
        Diff diff = differences.get(diffstring);
        if(diff==null){
            throw new ElementNotFoundException(diffstring, "Diff");
        }
        differences.remove(diffstring);
    }
    private boolean applyDiffsToWorld(){
        try {
            for (String i : dependent) {
                try {
                    if (!World.eventMap.get(i).isHappened()) {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {

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
            //Story
            System.out.println(World.eventMap.get(hash).getInfo());
            return World.eventMap.get(hash).applyDiffsToWorld();
        }
        return false;
    }

    public static boolean execSingleEvent(String eventName){
        ArrayList<String> args = new ArrayList<>();
        args.add(World.eventKeyMap.get("start"));
        return execEvent(args);
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean check(){
        boolean ret=true;
        if(dependent!=null) {
            for (String i : dependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Element %s nicht gefunden. In %s von %s\n", dependent, this.getClass().toString(), stringForHash(cmd));
                    ret = false;
                }
            }
        }
        for (Diff i: differences.values()) {
            if(!i.check()){
                ret=false;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("Event Name: %s\n",name);
        string+=String.format("Info: %s\n",info);
        string+=String.format("Befehl: %s\n",cmd.toString());
        string+=String.format("Abhängig von: %s\n",dependent);
        string+=String.format("Einmalig? %b\n",once);
        string+="Diffs:";
        for (Diff i: differences.values()) {
            string+="\n";
            string+=i.toString();
        }
        return string;
    }
}