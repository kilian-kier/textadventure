package com.textadventure.Story;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class World {
    public HashMap<String,Object> objectMap ;
    public HashMap<String,Event> eventMap;

    public void load(String path) throws FileNotFoundException {
        /*File directory = new File(path);
        if(!directory.exists()){
            throw new FileNotFoundException(path+" not found");
        }
        File[] contents = directory.listFiles();
        for ( File f : contents) {
            if(f.getName().equals("Events")){
                loadEventMap(f.getAbsolutePath());
            }
            System.out.println(f.getName());
        }*/
        Properties properties = new Properties();
        properties.setProperty("Type","Location");
        properties.setProperty("Name","Forestis ad Lusinam");
        properties.setProperty("Info","Dies ist ein kleines altes Dorf. Es trägt den Namen Forestis ad Lusinam. ");
        properties.setProperty("Description","Die Leute hier sind sehr freundlich. Ich habe mich in einem kleinen abgelegenen Gasthaus die Nacht verbracht. Es gibt einen kleinen Dorfplatz, wo man immer Leute antrifft. Davor steht eine für ein Dorf dieser Größe prächtige Kirche. Es gibt nicht viele Häuser. Hervor sticht aber eine kleine Hütte am Waldrand.");
        ArrayList<String> rooms=new ArrayList();
        rooms.add("Gasthaus");
        rooms.add("Dorfplatz");
        rooms.add("Kirche");
        rooms.add("Hütte");
        properties.setProperty("Rooms", rooms.toString());
        FileOutputStream out = new FileOutputStream(path);
        try {
            properties.storeToXML(out, "Lüsen");
        }catch(Exception e){

        }
    }

    private void loadEventMap(String path) {
        File directory = new File(path);
        File[] contents= directory.listFiles();
        String content="";
        for(File f: contents){
            try {
                content= Files.readString(Path.of(f.getAbsolutePath()));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(content);
        }
    }
}
