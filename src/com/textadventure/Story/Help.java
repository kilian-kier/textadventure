package com.textadventure.Story;

import com.textadventure.exeptions.NoHelpFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Hilfe Klasse, welche hp Dateien lädt und Hilfen ausgeben kann
 */
public class Help {
    private static HashMap<String, Properties> hilfe= new HashMap<>();

    private static String getFileType(String file,boolean type){
        for(int i=file.length()-1;i>=0;i--){
            if(file.charAt(i)=='.'){
                if(type) {
                    return file.substring(i + 1, file.length());
                }else{
                    return file.substring(0,i);
                }
            }
        }
        if(type){
            return "";
        }else{
            return file;
        }
    }

    /**
     * Lädt alle hp Dateien in einem Verzeichnis
     * @param path Pfad zu Ordner, welcher die Help Dateien enthält
     */
    public static void load(String path){
        if(path.charAt(path.length()-1)!='/'){
            path+="/";
        }
        File dir = new File(path);
        String contents[]=dir.list();
        InputStream input;
        for (String i :contents) {
            if(getFileType(i,true).equals("hp")){
                try {
                        input = new FileInputStream(path + i);
                        hilfe.put(getFileType(i,false), new Properties());
                        hilfe.get(getFileType(i,false)).load(input);
                    } catch (Exception e) {
                        //Ignore
                    }
                }
         }
    }

    /**
     * Die Funktion gibt einen String mit einer Hilfe zu einem bestimmten Thema zurück
     * @param type Name der Help Datei in der gesucht werden soll (Ohne Dateiendung)
     * @param parameter Der String nach dem gesucht werden soll, ist der Parameter null wird nach std (Standard Ausgabe) gesucht
     * @return Gibt String mit Hilfe zurück
     * @throws NoHelpFoundException Wird keine Hilfe gefunden wird dies Exception geworfen
     */
    public static String help(String type, String parameter) throws NoHelpFoundException {
        if(parameter==null){
            parameter="std";
        }
        try {
            String temp;
            temp=hilfe.get(type).getProperty(parameter);
            if (temp == null) {
                throw new NoHelpFoundException(type,parameter);
            }
            return temp;
        }catch (Exception e){
            throw new NoHelpFoundException(type,parameter);
        }
    }
}
