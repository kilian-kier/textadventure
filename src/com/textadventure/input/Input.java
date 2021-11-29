package com.textadventure.input;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Input {
    private static String editor = "gnome-text-editor";
    /**
     * Method creates List of words from String
     * @param string String of which a List is created
     * @return List of Words
     */
    public static ArrayList<String> StringToList(String string){
        ArrayList<String> list = new ArrayList<>();
        char c;
        int wordStart=0;
        int wordEnd=0;
        boolean wordFinished=true;
        for(int i=0;i<string.length();i++){
            c = string.charAt(i);
            switch(c){
                case ' ':
                    if(!wordFinished){
                        wordFinished=true;
                        list.add(string.substring(wordStart,wordEnd+1));
                        wordStart=i;
                        wordEnd=i;
                    }
                    break;
                default:
                    if(wordFinished){
                        wordFinished=false;
                        wordStart=i;
                        wordEnd=i;
                    }else{
                        wordEnd++;
                }
            }
        }
        if(wordStart!=wordEnd){
            list.add(string.substring(wordStart,wordEnd+1));
        }
        return list;
    }

    /**
     * Opens String in default Editor so that the user can edit it.
     * @param string String to edit
     * @return Edited String
     */
    public static String edit(String string){
        Runtime runtime = Runtime.getRuntime();
        Random random = new Random();
        String filename;
        filename = "temp" + String.valueOf(random.nextInt());
        File file = new File(filename);
        while(file.exists()){
            filename = "temp" + String.valueOf(random.nextInt());
            file = new File(filename);
        }
        String[] cmd = {editor,filename};
        try {
            Files.write(Path.of(filename),string.getBytes());
            Process process = new ProcessBuilder(cmd).start();
            process.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
        String string2="";
        try {
            string2 = new String(Files.readAllBytes(Paths.get(filename)));
        }catch(Exception e){
            e.printStackTrace();
        }
        file.delete();
        return string2;
    }

    /**
     * Set default editor
     * @param editor
     */
    public static void setEditor(String editor){
        Input.editor=editor;
    }


    public static String switchOptions(String[] options){
        Scanner scanner = new Scanner(System.in);
        String input="";
        boolean exit = false;
        while(!exit) {
            System.out.print("sw> ");

            input = scanner.nextLine();
            input = input.toLowerCase();
            for (String s:options) {
                if(s.equals(input)) exit=true;
            }
            if(!exit){
                System.out.println("Input not Valid");
            }
        }
        return input;
    }

}
