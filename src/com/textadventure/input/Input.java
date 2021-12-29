package com.textadventure.input;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Sammlung verschiedener Eingabe Funktionen
 */
public class Input {
    private static String editor = "gnome-text-editor";

    /**
     * Öffnet einen String in einem Editor
     *
     * @param string String zum bearbeiten
     * @return Bearbeiteter String
     */
    public static String edit(String string) {
        if(string==null){
            string="";
        }
        Runtime runtime = Runtime.getRuntime();
        Random random = new Random();
        String filename;
        filename = "temp" + random.nextInt();
        File file = new File(filename);
        while (file.exists()) {
            filename = "temp" + random.nextInt();
            file = new File(filename);
        }
        String[] cmd = {editor, filename};
        try {
            Files.write(Path.of(filename), string.getBytes());
            Process process = new ProcessBuilder(cmd).start();
           process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String string2 = "";
        try {
            string2 = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!file.delete())
            throw new RuntimeException("Failed to delete temporary file: " + file.getAbsolutePath()); //TODO: vielleicht eigene Exception schreiben
        if(string2.charAt(string2.length()-1)=='\n') {
            string2=string2.substring(0,string2.length()-1);
        }
        return string2;
    }

    /**
     * Setzt Editor
     *
     * @param editor Dateipfad zum Editor
     */
    public static void setEditor(String editor) {
        Input.editor = editor;
    }

    public static String getEditor(){
        return editor;
    }

    /**
     * Erhällt ein Array von gültigen eingaben und lässt den Nutzer zwischen einer davon wählen.
     * @param options Array mit gültigen Eingaben
     * @return Eingabe vom Nutzer, welche garantiert eine der übergebenen ist.
     */
    public static String switchOptions(String[] options) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean exit = false;
        while (!exit) {
            System.out.print("swO> ");

            input = scanner.nextLine();
            input = input.toLowerCase();
            for (String s : options) {
                if (s.equals(input)) {
                    exit = true;
                    break;
                }
            }
            if (!exit) {
                System.out.println("Eingabe nicht gültig");
            }
        }
        return input;
    }

    static public LinkedList<String> getCommand() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> command;
        String input;
        do {
            input = scanner.nextLine();
            command = splitInput(input);
        } while (command == null);
        return command;
    }

    static public String input(String string,boolean lowercase) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        System.out.print(string+": ");
        while (input.length() == 0)
            input = scanner.nextLine();
        if(lowercase) {
            return input.toLowerCase();
        }else{
            return input;
        }
    }

    static public LinkedList<String> splitInput (String input){
        input = input.toLowerCase();
        if (input.equals("")) return null;
        LinkedList<String> command = new LinkedList<>(Arrays.asList(input.split("[ \n]")));
        command.removeIf(s -> s.equals(""));
        return command;
    }
}
