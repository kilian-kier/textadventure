package com.textadventure.Event;

import com.textadventure.Story.Help;
import com.textadventure.Story.World;
import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.input.Input;
import com.textadventure.locations.Room;
import com.textadventure.things.Tool;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class ToolDiff extends Diff implements Serializable {
    private static final long serialVersionUID = 9672883399970462L;
    public ToolDiff(String name){
        super(name);
    }
    private String container;
    @Override
    public void applyDiffToWorld() throws GameElementNotFoundException {
        Tool tool ;
        try {
            tool= World.toolMap.get(name);
        }catch(Exception e){
            throw new GameElementNotFoundException(name,"tool");
        }
        if(description!=null){
            tool.setDescription(getDescription());
        }
        if(container!=null){
            try {
                tool.changeContainer(getContainer());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean check() {
        String stringTemp;
        boolean ret=true;
        stringTemp=getContainer();
        if(stringTemp!=null){
            if(World.roomMap.get(stringTemp)==null && World.containerMap.get(stringTemp)==null){
                System.out.printf("Raum/Container %s nicht gefunden. In %s von %s\n",stringTemp,this.getClass().toString(),name);
                ret=false;
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
            System.out.print("Tool Diff " + getName()  + ">> ");
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
                        case "container":
                            if(commands.size()>2){
                                setContainer(commands.get(2));
                            }else {
                                setContainer(Input.input("Container"));
                            }
                            System.out.println("Container hinzugefügt");
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
                        case "container":
                            setContainer(null);
                            System.out.println("Container entfernt");
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
                            System.out.println(Help.help("ToolDiffEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("ToolDiffEditor", null));
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
    public String toString() {
        String string="";
        string+=String.format("Diff von %s\n",name);
        string+=String.format("Beschreibung: %s\n",getDescription());
        string+=String.format("Container/Raum: %s",getContainer());
        return string;
    }
    public void setContainer(String container)  {
        this.container=container;
    }
    public String getContainer()  {
        return container;
    }
}
