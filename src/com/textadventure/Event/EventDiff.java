package com.textadventure.Event;

import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class EventDiff extends Diff {
    private Collection<String> cmd = null;
    private Collection<String> rmdiffs = null;
    private HashMap<String, Diff> adddiffs = null;
    private Collection<String> adddependent = null;
    private Collection<String> rmdependent = null;
    private Collection<String> addnotdependent = null;
    private Collection<String> rmnotdependent = null;
    private Collection<String> addinventory = null;
    private Collection<String> rminventory = null;
    private Boolean happened = null;
    private Boolean once = null;
    private String music = null;
    private String info = null;
    private String room = null;

    public EventDiff(String name) {
        super(name);
    }

    public void addAddDiff(Diff diff) {
        if(adddiffs==null){
            adddiffs=new HashMap<>();
        }
        adddiffs.put(diff.getName(), diff);
    }

    public Diff getAddDiff(String diffstring) {
        return adddiffs.get(diffstring);
    }

    public void rmAddDiff(String diffstring) throws ElementNotFoundException {
        Diff diff = adddiffs.get(diffstring);
        if (diff == null) {
            throw new ElementNotFoundException(diffstring, "Diff");
        }
        adddiffs.remove(diffstring);
    }

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Event event;
        try {
            event = World.eventMap.get(World.eventKeyMap.get(name));
        } catch (Exception e) {
            throw new GameElementNotFoundException(name, "event");
        }
        if (cmd != null) {
            event.storeEvent(cmd);
        }
        if (rmdiffs != null) {
            for (String i : rmdiffs) {
                try {
                    event.rmDiff(i);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (adddiffs != null) {
            for (Diff i : adddiffs.values()) {
                event.addDiff(i);
            }
        }
        if (adddependent != null) {
            event.getDependent().addAll(adddependent);
        }
        if (rmdependent != null) {
            event.getDependent().removeAll(rmdependent);
        }
        if (rmnotdependent != null) {
            event.getNotdependent().removeAll(rmnotdependent);
        }
        if (addnotdependent != null) {
            event.getNotdependent().addAll(addnotdependent);
        }
        if (addinventory != null) {
            event.getInventory().addAll(addinventory);
        }
        if (rminventory != null) {
            event.getInventory().removeAll(rminventory);
        }
        if (once != null) {
            event.setOnce(once);
        }
        if (happened != null) {
            event.setHappened(happened);
        }
        if (music != null) {
            event.setMusic(music);
        }
        if (info != null) {
            event.setInfo(info);
        }
        if (room != null) {
            event.setRoom(room);
        }
    }

    @Override
    public boolean check(boolean fix) {
        boolean ret = true;
        if (adddependent != null) {
            for (String i : adddependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (addnotdependent != null) {
            for (String i : addnotdependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (addinventory != null) {
            for (String i : addinventory) {
                if (!World.toolMap.containsKey(i)) {
                    System.out.printf("Tool %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (adddiffs != null) {
            for (Diff i : adddiffs.values()) {
                if (!i.check(fix)) {
                    ret = false;
                }
            }
        }
        if (rmdependent != null) {
            for (String i : rmdependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (rmnotdependent != null) {
            for (String i : rmnotdependent) {
                if (!World.eventKeyMap.containsKey(i)) {
                    System.out.printf("Event %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (rminventory != null) {
            for (String i : rminventory) {
                if (!World.toolMap.containsKey(i)) {
                    System.out.printf("Tool %s nicht gefunden. In %s von %s\n", i, this.getClass().toString(), name);
                    ret = false;
                }
            }
        }
        if (room != null) {
            if (!World.roomMap.containsKey(room)) {
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n", room, this.getClass().toString(), name);

                ret = false;
            }
        }
        return ret;
    }

    @Override
    public boolean edit() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while (true) {
            System.out.print("Event Diff " + getName() + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            try {
                switch (commands.get(0)) {
                    case "add":
                        switch (commands.get(1)) {
                            case "cmd":
                                commands.removeFirst();
                                commands.removeFirst();
                                cmd = commands;
                                System.out.println("Befehl hinzugefügt");
                                break;
                            case "room":
                                room = commands.get(2);
                                System.out.println("Raum hinzugefügt");
                                break;
                            case "happened":
                                happened = Boolean.parseBoolean(commands.get(2));
                                System.out.printf("Happened wurde auf %b gesetzt", happened);
                                break;
                            case "once":
                                once = Boolean.parseBoolean(commands.get(2));
                                System.out.printf("Once wurde auf %b gesetzt", happened);
                                break;
                            case "info":
                                info = Input.input("Info:", false);
                                System.out.println("Info wurde gesetzt");
                                break;
                            case "music":
                                music = Input.input("Musik Pfad:", false);
                                System.out.println("Musik wurde gesetzt");
                                break;
                            case "rmdiffs":
                                commands.removeFirst();
                                commands.removeFirst();
                                rmdiffs = commands;
                                System.out.println("Rmdiffs gesetzt");
                                break;
                            case "adddependent":
                                commands.removeFirst();
                                commands.removeFirst();
                                adddependent = commands;
                                System.out.println("Adddependent gesetzt");
                                break;
                            case "rmdependent":
                                commands.removeFirst();
                                commands.removeFirst();
                                rmdependent = commands;
                                System.out.println("Rmdependent gesetzt");
                                break;
                            case "rmnotdependent":
                                commands.removeFirst();
                                commands.removeFirst();
                                rmnotdependent = commands;
                                System.out.println("Rmnotdependent gesetzt");
                                break;
                            case "addnotdependent":
                                commands.removeFirst();
                                commands.removeFirst();
                                addnotdependent = commands;
                                System.out.println("Addnotdependent gesetzt");
                                break;
                            case "addinventory":
                                commands.removeFirst();
                                commands.removeFirst();
                                addinventory = commands;
                                System.out.println("Addinventory gesetzt");
                                break;
                            case "rminventory":
                                commands.removeFirst();
                                commands.removeFirst();
                                rminventory = commands;
                                System.out.println("Rminventory gesetzt");
                                break;
                            default:
                                System.out.println("Parameter ungültig");
                                break;
                        }
                        break;
                    case "adddiff":
                        Diff diff = null;
                        diff = Event.newDiff(name, commands.get(1));
                        if (adddiffs == null) {
                            adddiffs = new HashMap<>();
                        }
                        addAddDiff(diff);
                        diff.edit();
                        break;
                    case "rmdiff":
                        adddiffs.remove(commands.get(1));
                        if (adddiffs.isEmpty()) {
                            adddiffs = null;
                        }
                        break;
                    case "rm":
                        switch (commands.get(1)) {
                            case "cmd":
                                cmd = null;
                                System.out.println("Befehl entfernt");
                                break;
                            case "room":
                                room = (null);
                                System.out.println("Raum entfernt");
                                break;
                            case "happened":
                                happened = null;
                                System.out.println("Happened entfernt");
                                break;
                            case "once":
                                once = null;
                                System.out.println("Once entfernt");
                                break;
                            case "music":
                                music = null;
                                System.out.println("Musik entfernt");
                                break;
                            case "rmdiffs":
                                rmdiffs = null;
                                System.out.println("Rmdiffs entfernt");
                                break;
                            case "adddiffs":
                                adddiffs = null;
                                System.out.println("Adddiffs entfernt");
                                break;
                            case "adddependent":
                                adddependent = null;
                                System.out.println("Adddependent entfernt");
                                break;
                            case "rmdependent":
                                rmdependent = null;
                                System.out.println("Rmdependent entfernt");
                                break;
                            case "addnotdependent":
                                addnotdependent = null;
                                System.out.println("Addnotdependent entfernt");
                                break;
                            case "rmnotdependent":
                                rmnotdependent = null;
                                System.out.println("Rmnotdependent entfernt");
                                break;
                            case "addinventory":
                                addinventory = null;
                                System.out.println("Addinventory entfernt");
                                break;
                            case "rminventory":
                                rminventory = null;
                                System.out.println("Rminventory entfernt");
                                break;
                            default:
                                System.out.println("Parameter ungültig");
                                break;
                        }
                        break;
                    case "show":
                        System.out.println(this);
                        break;
                    case "back":
                        return true;
                    case "help":
                        try {
                            if (commands.size() > 1) {
                                System.out.println(Help.help("EventDiffEditor", commands.get(1)));
                            } else {
                                System.out.println(Help.help("EventDiffEditor", null));
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Befehl nicht gefunden");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Zu wenig Argumente");
            }
        }
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("Event Name: %s\n", name);
        string += String.format("Info: %s\n", info);
        string += String.format("Befehl: %s\n", cmd);
        string += String.format("Musik: %s\n", music);
        string += String.format("Passiert: %b\n", happened);
        string += String.format("Hinzufügen zu Inventar Abhängigkeiten: %s\n", addinventory);
        string += String.format("Entfernen von Inventar Abhängigkeiten: %s\n", rminventory);
        string += String.format("Hinzufügen folgender Abhängigkeiten: %s\n", adddependent);
        string += String.format("Entfernnen folgender Abhängigkeiten: %s\n", rmdependent);
        string += String.format("Hinzufügen folgender Abhängigkeiten, welche nicht erfüllt sein müssen: %s\n", addnotdependent);
        string += String.format("Entfernen folgender Abhängigkeiten, welche nicht erfüllt sein müssen: %s\n", rmnotdependent);
        string += String.format("Einmalig? %b\n", once);
        string += String.format("Raum %s\n", room);
        string += String.format("Diffs zum Entfernen: %s\n", rmdiffs);
        string += "Diffs zum Hinzufügen:";
        if (adddiffs != null) {
            for (Diff i : adddiffs.values()) {
                string += "\n";
                string += i.toString();
            }
        }
        return string;
    }

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if (map.containsKey("info")) {
            String info = map.get("info");
            if (info.equals("rminfo"))
                this.info = "null";
            else
                this.info = info;
        }
        if (map.containsKey("cmd")) {
            this.cmd = Input.splitInput(map.get("cmd"));
        }
        if (map.containsKey("happened")) {
            try {
                this.happened = Boolean.parseBoolean(map.get("happened"));
            }catch(Exception ignore){}
        }
        if (map.containsKey("music")) {
            this.music = map.get("music");
            World.addMusic(music);
        }
        if (map.containsKey("adddependent")) {
            this.adddependent = Input.splitInput(map.get("adddependent"));
        }
        if (map.containsKey("rmdependent")) {
            this.rmdependent = Input.splitInput(map.get("rmdependent"));
        }
        if (map.containsKey("once")) {
            try {
                once = Boolean.parseBoolean(map.get("once"));
            } catch (Exception ignore) {
            }
        }
        if (map.containsKey("addnotdependent")) {
            this.addnotdependent = Input.splitInput(map.get("addnotdependent"));
        }
        if (map.containsKey("rmnotdependent")) {
            this.rmnotdependent = Input.splitInput(map.get("rmnotdependent"));
        }
        if (map.containsKey("addinventory")) {
            this.addinventory = Input.splitInput(map.get("addinventory"));
        }
        if (map.containsKey("rminventory")) {
            this.rminventory = Input.splitInput(map.get("rminventory"));
        }
        if (map.containsKey("room")) {
            this.room = map.get("room");
        }
        if (map.containsKey("rmdiffs")) {
            try {
                rmdiffs = Input.splitInput(map.get("rmdiffs"));
            } catch (Exception ignore) {
            }
        }
        if (map.containsKey("adddiffs")) {
            HashMap<String, String> diffs = null;
            try {
                diffs = LoadStoreWorld.createMap(map.get("adddiffs"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            for (String i : diffs.keySet()) {
                HashMap<String, String> diff;
                try {
                    diff = LoadStoreWorld.createMap(diffs.get(i));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                Diff newdiff = Event.newDiff(i, diff.get("type"));
                newdiff.loadFromHashMap(diff);
                addAddDiff(newdiff);
            }
        }
    }
}
