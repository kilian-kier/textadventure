package com.textadventure.Event;

import com.textadventure.Story.World;
import com.textadventure.characters.Dialog;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;
import com.textadventure.input.Input;
import com.textadventure.locations.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class NPCDiff extends Diff implements Serializable {
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
    public boolean check() {
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
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Raum: %s\n",getRoom());
        string+="Dialog:\n";
        System.out.println(getDialog().toString());
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
    public void edit() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Diff " + getName()  + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "add":
                    switch(commands.get(1)){
                        case "description":
                            if (Input.getEditor() != null) {
                                setDescription(Input.edit(getDescription()));
                            } else {
                                setDescription(Input.input("Beschreibung"));
                            }
                            System.out.println("Beschreibung hinzugefügt");
                            break;
                        case "room":
                            if(commands.size()>2){
                                setRoom(commands.get(2));
                            }else {
                                setRoom(Input.input("Raum"));
                            }
                            System.out.println("Raum hinzugefügt");
                            break;
                        case "dialog":
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setDialog(null);
                            System.out.println("Dialog hinzugefügt");
                            break;
                        default:
                            System.out.println("Parameter ungültig");
                            break;
                    }
                    break;
                case "rm":
                    switch(commands.get(1)){
                        case "description":
                            setDescription(null);
                            System.out.println("Beschreibung entfernt");
                            break;
                        case "room":
                            setRoom(null);
                            System.out.println("Raum entfernt");
                            break;
                        case "dialog":
                            setDialog(null);
                            System.out.println("Dialog entfernt");
                            break;
                        default:
                            System.out.println("Parameter ungültig");
                            break;
                    }
                    break;
                case "show":
                    System.out.println(this.toString());
                    break;
                case "back":
                    return;
                case "help":
                    //TODO help
                default:
                    System.out.println("Befehl nicht gefunden");
                    break;
            }
        }

    }
}
