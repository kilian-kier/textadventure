package com.textadventure.characters;

import com.textadventure.help.Help;
import com.textadventure.input.Input;
import com.textadventure.story.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Dialog implements Serializable {
    private static final long serialVersionUID = -7386853779740424554L;
    private ArrayList<String[]> dialog=new ArrayList<>();

    public ArrayList<String[]> getDialog() {
        return dialog;
    }

    public void setDialog(ArrayList<String[]> dialog) {
        if(dialog==null){
            this.dialog.clear();
        }else {
            this.dialog = dialog;
        }
    }
    public void setQA(int index, String question,String answer, String event) throws IndexOutOfBoundsException{
        String[]arr=new String[3];
        if(index<dialog.size()){
            arr= dialog.remove(index);
        }
        if(question!=null){
            arr[0]=question;
        }
        if(answer!=null){
            arr[1]=answer;
        }
        if(event!=null){
            if(event.equals("none")){
                arr[2]=null;
            }else{
                arr[2]=event;
            }
        }
        if(index>=dialog.size()){
            dialog.add(arr);
        }else{
            dialog.set(index, arr);
        }
    }

    public boolean check(boolean fix){
        boolean ret = true;
        for (String[] i :dialog
             ) {
            if(!World.eventKeyMap.containsKey(i[2]) && i[2]!=null){
                System.out.printf("Event %s von Dialog existiert nicht\n",i[2]);
            }
            ret=false;
        }
        return ret;
    }

    public void edit(){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> commands;
        String input;
        while(!exit) {
            System.out.print("Dialog " + ">> ");
            input = scanner.nextLine();
            commands = Input.splitInput(input);
            if (commands == null) continue;
            switch (commands.get(0)) {
                case "set":
                    try{
                        switch(commands.get(2)){
                            case "question":
                                setQA(Integer.parseInt(commands.get(1)),Input.input("Frage",false),null,null);
                                break;
                            case "answer":
                                setQA(Integer.parseInt(commands.get(1)),null,Input.input("Antwort",false),null);
                                break;
                            case "event":
                                setQA(Integer.parseInt(commands.get(1)),null,null,Input.input("Event",true));
                                break;
                        }
                    }/*catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }catch(NumberFormatException e){
                        System.out.println("Ungültiger Parameter");
                    }*/
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "add":
                    if(commands.size()>1){
                        try {
                            dialog.add(Integer.parseInt(commands.get(1)),inputQA());
                        }catch(Exception e){
                            System.out.println("Ungültiger Parameter");
                        }
                        System.out.println("Dialog bei Index " + Integer.valueOf(commands.get(1)) + " hinzugefügt");
                    }else{
                        dialog.add(inputQA());
                        System.out.println("Dialog hinzugefügt");
                    }
                    break;
                case "rm":
                    if(commands.size()>1){
                        try {
                            dialog.remove(Integer.parseInt(commands.get(1)));
                        }catch(Exception e){
                            System.out.println("Ungültiger Parameter");
                        }
                        System.out.println("Dialog bei Index " + Integer.valueOf(commands.get(1)) + " entfernt");
                    }else{
                        System.out.println("Zu wenig Parameter");
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
                            System.out.println(Help.help("DialogEditor", commands.get(1)));
                        }else{
                            System.out.println(Help.help("DialogEditor", null));
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
    public String[] inputQA(){
        String[] qa = new String[3];
        qa[0]=Input.input("Frage",false);
        qa[1]=Input.input("Antwort",false);
        qa[2]=Input.input("Event",true); //Eingabe von none wenn kein Event
        if(qa[2].equals("none")){
            qa[2]=null;
        }
        return qa;
    }
    @Override
    public String toString(){
        String string="";
        for (String[] i: dialog) {
            try {
                string += String.format("Frage: %s; Antwort: %s; Event: %s\n", i[0], i[1], i[2]);
            }catch(IndexOutOfBoundsException e){
                System.err.println("Ungültiger Dialog\n");
                e.printStackTrace();
            }
        }
        if(string.length()>0){
        if(string.charAt(string.length()-1)=='\n') {
            string=string.substring(0,string.length()-1);
        }}
        return string;
    }

    public void loadFromHashMap(HashMap<String, String> map) {
        for(int i=1;i<Integer.MAX_VALUE;i++){
            if(map.containsKey("question"+i) && map.containsKey("answer"+i)){
                setQA(i-1, map.get("question" + i), map.get("answer" + i), map.get("event" + i));
            }else{
                break;
            }
        }
    }
}
