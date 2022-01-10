package com.textadventure.Event;

import com.textadventure.gamemusic.MusicPlayer;
import com.textadventure.input.Input;
import com.textadventure.interfaces.Checkable;
import com.textadventure.interfaces.Loadable;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.exeptions.EventExistsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Event implements Serializable, Loadable, Checkable {
    private static final long serialVersionUID = 2552220530382600393L;
    private String name;
    private Collection<String> cmd;
    private final HashMap<String,Diff> differences=new HashMap<>();
    private Collection<String> dependent;
    private Collection<String> notdependent;
    private Collection<String> inventory;
    private boolean happened=false;
    private boolean once=false;
    private String music = null;

    public static void setRememberRoom(String rememberRoom) {
        Event.rememberRoom = rememberRoom;
    }

    private static String rememberRoom;


    public Collection<String> getInventory() {
        return inventory;
    }

    public void setInventory(Collection<String> inventory) {
        this.inventory = inventory;
    }

    public Collection<String> getNotdependent() {
        return notdependent;
    }

    public void setNotdependent(Collection<String> notdependent) {
        this.notdependent = notdependent;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

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
            if(rememberRoom==null){
                if(room!=null && !room.equals(World.player.getCurrentRoom().getName())){
                    return false;
                }
            }else{
                if(room!=null && !room.equals(rememberRoom)){
                    return false;
                }
            }

            if(happened&& once){
                return false;
            }
            if(dependent!=null) {
                for (String i : dependent) {
                    try {
                        if (!World.eventMap.get(World.eventKeyMap.get(i)).isHappened()) {
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(notdependent!=null){
            for (String i : notdependent) {
                try {
                    if (World.eventMap.get(World.eventKeyMap.get(i)).isHappened() ) {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }
            if(inventory!=null){
                for(String i:inventory){
                    try{
                        if(!World.player.getToolsContainer().getTools().contains(i)){
                            return false;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (NullPointerException ignore){}
        if(info!=null){
            System.out.println(info);
        }
        if (this.getMusic() != null) {
            World.player.getCurrentRoom().setMusic(this.getMusic(), true);
            World.musicPlayer.play();
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
            Event event = World.eventMap.get(hash);
            boolean ret= event.applyDiffsToWorld();
            rememberRoom=null;
            return ret;
        }
        rememberRoom=null;
        return false;
    }

    public static boolean execSingleEvent(String eventName){
        ArrayList<String> args = new ArrayList<>();
        try {
            args.addAll(World.eventMap.get(World.eventKeyMap.get(eventName)).getCmd());
        }catch(Exception e){
            return false;
        }
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

    @Override
    public boolean check(boolean fix){
        boolean ret=true;
        if(dependent!=null) {
            for (String i : dependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), stringForHash(cmd));
                    ret = false;
                }
            }
        }
        if(notdependent!=null) {
            for (String i : notdependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), stringForHash(cmd));
                    ret = false;
                }
            }
        }
        if(inventory!=null) {
            for (String i : inventory) {
                if (!World.toolMap.containsKey(i)) {
                    System.out.printf("Tool %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), stringForHash(cmd));
                    ret = false;
                }
            }
        }
        for (Diff i: differences.values()) {
            if(!i.check(fix)){
                ret=false;
            }
        }
        return ret;
    }

    /**
     * Gibt eines neues Diff zurück
     * @param name Name des Diffs
     * @param type Typ des Diffs
     * @return Das neue Diff
     */
    public static Diff newDiff(String name, String type){
        Diff diff;
        switch(type){
            case "container":
                diff=new ContainerDiff(name);
                break;
            case "exit":
                diff=new ExitDiff(name);
                break;
            case "location":
                diff=new LocationDiff(name);
                break;
            case "npc":
                diff=new NPCDiff(name);
                break;
            case "room":
                diff=new RoomDiff(name);
                break;
            case "tool":
                diff=new ToolDiff(name);
                break;
            case "event":
                diff=new EventDiff(name);
                break;
            case "player":
                diff=new PlayerDiff("player");
                break;
            default:
                throw new NullPointerException();
        }
        return diff;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Event Name: %s\n",name);
        string+=String.format("Info: %s\n",info);
        string+=String.format("Befehl: %s\n",cmd.toString());
        string+=String.format("Musik: %s\n",music);
        string+=String.format("Abhängig von: %s\n",dependent);
        string+=String.format("Nicht Abhängig von: %s\n",notdependent);
        string+=String.format("Inventar: %s\n",inventory);
        string+=String.format("Einmalig? %b\n",once);
        string+=String.format("Raum %s\n",room);
        string+="Diffs:";
        for (Diff i: differences.values()) {
            string+="\n";
            string+=i.toString();
        }
        return string;
    }

    public void loadFromHashMap(HashMap<String,String> map){
        if(map.containsKey("info")){
            this.info=map.get("info");
        }
        if(map.containsKey("cmd")){
            this.cmd= Input.splitInput(map.get("cmd"));
        }
        if(map.containsKey("music")){
            this.music=map.get("music");
            World.addMusic(music);
        }
        if(map.containsKey("dependent")){
            this.dependent=Input.splitInput(map.get("dependent"));
        }
        if(map.containsKey("once")){
            try {
                once = Boolean.parseBoolean(map.get("once"));
            }catch(Exception ignore){}
        }
        if(map.containsKey("notdependent")){
            this.notdependent=Input.splitInput(map.get("notdependent"));
        }
        if(map.containsKey("inventory")){
            this.inventory=Input.splitInput(map.get("inventory"));
        }
        if(map.containsKey("room")){
            room=map.get("room");
        }
        if(map.containsKey("diffs")) {
            HashMap<String,String > diffs=null;
            try {
                diffs = LoadStoreWorld.createMap(map.get("diffs"));
            } catch(Exception e){
                System.out.println(e.getMessage());
                return;
            }
            for (String i:diffs.keySet()){
                HashMap<String, String > diff;
                try {
                    diff=LoadStoreWorld.createMap(diffs.get(i));
                } catch(Exception e){
                    System.out.println(e.getMessage());
                    continue;
                }
                Diff newdiff=Event.newDiff(i,diff.get("type"));
                newdiff.loadFromHashMap(diff);
                addDiff(newdiff);
            }
        }
    }
}