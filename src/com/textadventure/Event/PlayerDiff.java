package com.textadventure.Event;

import com.textadventure.characters.Player;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.story.World;
import com.textadventure.things.Container;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class PlayerDiff extends ElementDiff implements Serializable {
    private static final long serialVersionUID = -7301250516056665L;
    private String room=null;
    private Collection<String> addTools =null;
    private Collection<String> rmTools =null;

    public PlayerDiff(String name) {
        super(name);
    }

    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Player player = World.player ;

        if(description!=null) { //Description
            player.setDescription(description);
        }
        if(room!=null){ //Current Room
            try {
                player.setCurrentRoom(World.roomMap.get(room));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        try{
            if(addTools!=null){
                for (String i:addTools) {
                    World.toolMap.get(i).changeContainer("player");
                }
            }
            if(rmTools!=null){
                for (String i:rmTools) {
                    World.toolMap.get(i).setContainer(null);
                    player.getToolsContainer().removeTool(i);
                }
            }}

        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean check() {
        String stringTemp;
        Collection<String> collTemp;
        boolean ret=true;
        stringTemp=getRoom();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null){
                System.out.printf("Raum %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
            }
        }
        collTemp=getAddTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool (Add) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        collTemp=getRmTools();
        if(collTemp!=null){
            for (String i:collTemp) {
                if(World.toolMap.get(i)==null){
                    System.out.printf("Tool (Rm) %s nicht gefunden. In %s von %s\n",i,this.getClass().toString(),name);
                    ret=false;
                }
            }
        }
        return ret;
    }

    @Override
    public void edit() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Container Diff " + getName()  + ">> ");
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
                                setDescription(Input.input("Beschreibung",false));
                            }
                            System.out.println("Beschreibung hinzugefügt");
                            break;
                        case "room":
                            if(commands.size()>2){
                                setRoom(commands.get(2));
                            }else {
                                setRoom(Input.input("Raum",true));
                            }
                            System.out.println("Raum hinzugefügt");
                            break;
                        case "addtools":
                            commands.removeFirst();
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setAddTools(commands);
                            System.out.println("Addtools hinzugefügt");
                            break;
                        case "rmtools":
                            commands.removeFirst();
                            commands.removeFirst();
                            if(commands.isEmpty()){
                                System.out.println("Zu wenig Parameter");
                                break;
                            }
                            setRmTools(commands);
                            System.out.println("Rmtools hinzugefügt");
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
                        case "addtools":
                            setAddTools(null);
                            System.out.println("Addtools entfernt");
                            break;
                        case "rmtools":
                            setRmTools(null);
                            System.out.println("Rmtools entfernt");
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
                    try{
                        if(commands.size()>1) {
                            System.out.println(Help.help("PlayerDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("PlayerDiffEditor", null));
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

    @Override
    public void loadFromHashMap(HashMap<String, String> map) {
        if(map.containsKey("description")){
            description=map.get("description");
        }
        if(map.containsKey("room")){
            room=map.get("room");
        }
        if(map.containsKey("addtools")){
            addTools=Input.splitInput(map.get("addtools"));
        }
        if(map.containsKey("rmtools")){
            rmTools=Input.splitInput(map.get("rmtools"));
        }
    }

    @Override
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Raum: %s\n",getRoom());
        string+=String.format("AddTools: %s\n",getAddTools()!=null?getAddTools().toString():null);
        string+=String.format("RmTools: %s",getRmTools()!=null?getRmTools().toString():null);
        return string;
    }

    public void setRoom(String room)  {
        this.room=room;
    }
    public String getRoom()  {
        return room;
    }

    public void setAddTools(Collection<String> tools){
        this.addTools =tools;
    }
    public Collection<String> getAddTools(){
        return addTools;
    }

    public void setRmTools(Collection <String> tools){
        this.rmTools =tools;
    }
    public Collection<String> getRmTools(){
        return rmTools;
    }
}
