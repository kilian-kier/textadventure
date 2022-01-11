package com.textadventure.Event;

import com.textadventure.help.Help;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;
import com.textadventure.characters.Dialog;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.input.Input;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class NPCDiff extends ElementDiff implements Serializable {
    private static final long serialVersionUID = -7301250524416056665L;
    public NPCDiff(String name) {
        super(name);
    }
    private Dialog dialog=new Dialog();
    private String room=null;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        NPC npc ;
        try {
            npc= World.npcMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"npc");
        }
        if(description!=null){//Description
            npc.setDescription(getDescription());
        }
        if(interactable!=null){
            npc.setInteractable(interactable);
        }
        try {
            if (room != null) {
                    npc.changeContainer(room);
            }
            if (dialog != null) {
                    npc.setDialog(getDialog());

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean check(boolean fix) {
        String stringTemp;
        boolean ret=true;
        stringTemp=getRoom();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        return ret;
    }
    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("room")){
            room=map.get("room");
        }
        if(map.containsKey("dialog")){
            dialog=new Dialog();
            try {
                dialog.loadFromHashMap(LoadStoreWorld.createMap(map.get("dialog")));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        if(map.containsKey("interactable")){
            try {
                interactable = Boolean.parseBoolean(map.get("interactable"));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Raum: %s\n",getRoom());
        string+="Dialog:\n";
        string+=((getDialog().toString()));
        return string;
    }
    public void setRoom(String room)  {
        this.room=room;
    }
    public String getRoom()  {
        return room;
    }
    public void setDialog(Dialog dialog){
        this.dialog=dialog;
    }
    public Dialog getDialog(){
       return dialog;
    }

    @Override
    public boolean edit() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(true) {
            System.out.print("NPC Diff " + getName()  + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "add":
                    try {
                        switch (commands.get(1)) {
                            case "description":
                                if (Input.getEditor() != null) {
                                    setDescription(Input.edit(getDescription()));
                                } else {
                                    setDescription(Input.input("Beschreibung",false));
                                }
                                System.out.println("Beschreibung hinzugefügt");
                                break;
                            case "room":
                                if (commands.size() > 2) {
                                    setRoom(commands.get(2));
                                } else {
                                    setRoom(Input.input("Raum",true));
                                }
                                System.out.println("Raum hinzugefügt");
                                break;
                            case "dialog":
                                commands.removeFirst();
                                commands.removeFirst();
                                if (commands.isEmpty()) {
                                    System.out.println("Zu wenig Parameter");
                                    break;
                                }
                                if(dialog==null){
                                    dialog = new Dialog();
                                }
                                dialog.edit();
                                System.out.println("Dialog hinzugefügt");
                                break;
                            default:
                                System.out.println("Parameter ungültig");
                                break;
                        }
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Prameter");
                    }
                    break;
                case "rm":
                    try {
                        switch (commands.get(1)) {
                            case "description":
                                setDescription(null);
                                System.out.println("Beschreibung entfernt");
                                break;
                            case "room":
                                setRoom(null);
                                System.out.println("Raum entfernt");
                                break;
                            case "dialog":
                                dialog = null;
                                System.out.println("Dialog entfernt");
                                break;
                            default:
                                System.out.println("Parameter ungültig");
                                break;
                        }
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }
                    break;
                case "show":
                    System.out.println(this);
                    break;
                case "back":
                    return true;
                case "help":
                    try{
                        if(commands.size()>1) {
                            System.out.println(Help.help("NPCDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("NPCDiffEditor", null));
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }

    }
}
