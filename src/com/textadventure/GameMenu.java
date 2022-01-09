package com.textadventure;

import com.textadventure.help.Help;
import com.textadventure.input.Game;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;

import java.awt.*;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameMenu {


    public static void gameMenu() {
        int input;
        String path = null;
        Help.load();
        while (true) {
            switch (menu()) {
                case 1:
                    System.out.println("\033[2J");
                    System.out.println("\033[H");
                    System.out.println("1. Story spielen");
                    System.out.println("2. Eigene Welt spielen");
                    System.out.println("3. Zurück");
                    System.out.printf("\nEingabe: ");
                    do {
                            input = getInt();
                    } while (input > 3 || input < 1);
                    switch(input){
                        case 1:
                            path = "game.world";
                            break;
                        case 2:
                            path = getPath();
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Falsche Eingabe");
                    }
                    try{
                        LoadStoreWorld.load(path);
                    }catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    Game.start();
                    break;
                case 2:
                    path = getPath();
                    try {
                        LoadStoreWorld.load(path);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    Game.start();
                    break;
                case 3:
                    System.out.println("\033[2J");
                    System.out.println("\033[H");
                    System.out.println("1. Neue Welt erstellen");
                    System.out.println("2. Welt bearbeiten");
                    System.out.println("3. Zurück");
                    System.out.printf("\nEingabe: ");
                    do {
                        input = getInt();
                    } while (input > 3 || input < 1);
                    switch (input) {
                        case 1:
                            //Todo Neue Datei erstellen für Editor (path = )
                            break;
                        case 2:
                            path = getPath();
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Falsche Eingabe");
                    }
                    World.worldEditor(path);
                    break;
                case 4:
                    showCredits();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Falsche Eingabe");
            }
        }
    }


    private static String getPath() {
        World.setExplorer(true);
        FileDialog fd = new FileDialog(new Frame(), "Welt laden", FileDialog.LOAD);
        try {
            fd.setDirectory(Paths.get(World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString());
        } catch (Exception e) {
            System.out.println("Pfad nicht gefunden");
        }
        fd.setFile("*.world");
        fd.setVisible(true);
        return fd.getFile();
    }


    private static void showCredits() {
        //Todo Credits herschreibm
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mit Enter zum Menu zurückkehren");
        if (scanner.hasNextLine()) return;
    }


    private static int menu() {
        int retValue;
        System.out.println("\033[2J");
        System.out.println("\033[H");
        System.out.println("Herzlich Willkommen im Textadventure\n");
        System.out.println("1. Neues Spiel starten");
        System.out.println("2. Savegame laden");
        System.out.println("3. Welt Editor");
        System.out.println("4. Credits");
        System.out.println("5. Beenden");
        System.out.printf("\nEingabe: ");
        do {
            retValue = getInt();
        } while (retValue > 5 || retValue < 1);
        return retValue;
    }

    private static int getInt(){
        Scanner scanner = new Scanner(System.in);
        Integer x=null;
        while(x==null){
            try{
                x=scanner.nextInt();
            }catch(Exception e){
                scanner.next();
            }
        }
        return x;
    }
}
