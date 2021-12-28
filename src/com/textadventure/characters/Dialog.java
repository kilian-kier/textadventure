package com.textadventure.characters;

import com.textadventure.help.Help;
import com.textadventure.input.Input;

import java.io.Serializable;
import java.util.ArrayList;
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
            dialog.clear();
        }else {
            this.dialog = dialog;
        }
    }
    public void setQA(int index, String question,String answer, String event) throws IndexOutOfBoundsException{
        String[] arr= dialog.remove(index);
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
        dialog.set(index,arr);
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
                                setQA(Integer.parseInt(commands.get(1)),Input.input("Frage"),null,null);
                                break;
                            case "answer":
                                setQA(Integer.parseInt(commands.get(1)),null,Input.input("Antwort"),null);
                                break;
                            case "event":
                                setQA(Integer.parseInt(commands.get(1)),null,null,Input.input("Event"));
                                break;
                        }
                    }catch(IndexOutOfBoundsException e){
                        System.out.println("Zu wenig Parameter");
                    }catch(NumberFormatException e){
                        System.out.println("Ungültiger Parameter");
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
        qa[0]=Input.input("Frage");
        qa[1]=Input.input("Antwort");
        qa[2]=Input.input("Event"); //Eingabe von none wenn kein Event
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
        if(string.charAt(string.length()-1)=='\n') {
            string=string.substring(0,string.length()-1);
        }
        return string;
    }
}
