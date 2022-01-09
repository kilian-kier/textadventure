package com.textadventure;

import com.textadventure.help.Help;
import com.textadventure.input.Game;
import com.textadventure.story.LoadStoreWorld;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMenu {


    public static void gameMenu(){
        Help.load();
        while(true){
             switch(menu()){
                 case 1:
                     try {
                         LoadStoreWorld.load("music.world");
                     }catch(FileNotFoundException e){
                         System.out.println(e.getMessage());
                     }
                     Game.start();
                     break;
                 case 2:
                     String y = getPath();
                     Scanner scanner = new Scanner(System.in);
                     System.out.println("Gib den Dateinamen ein:");
                     String file = scanner.nextLine();
                     try {
                         LoadStoreWorld.load(file);
                     }catch(FileNotFoundException e){
                         System.out.println(e.getMessage());
                     }
                     Game.start();
                     break;
                 case 3:
                     System.out.println(getPath());
                     //World.worldEditor();
                     break;
                 case 4:
                     showCredits();
                     break;
                 case 5: return;
                 default:
                     System.out.println("Falsche Eingabe");
            }
        }
    }


    private static String getPath() {
        FileDialog fd = new FileDialog(new Frame(), "Welt laden", FileDialog.LOAD);
        /*try {
            fd.setDirectory();
        }
        catch (Exception e){

        }*/
        fd.setFile("*.world");
        fd.setVisible(true);
        String x = fd.getFile();
        System.out.println(x);
        return x;
    }


        private static void showCredits(){
        //Todo Credits herschreibm
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mit Enter zum Menu zurückkehren");
        if(scanner.hasNextLine()) return;
    }


    private static int menu(){
       Scanner scanner = new Scanner(System.in);
       int retValue;
        System.out.println("\033[2J");
        System.out.println("\033[H");
        System.out.println("Herzlich Willkommen im Textadventure\n");
        System.out.println("1. Story spielen");
        System.out.println("2. Eigene Welt spielen");
        System.out.println("3. Welt Editor");
        System.out.println("4. Credits");
        System.out.println("5. Beenden");
        System.out.printf("\nEingabe: ");
        do{
            retValue = scanner.nextInt();
        }while (retValue > 5 || retValue < 1);
        return retValue;
    }
}
