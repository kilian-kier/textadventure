package com.textadventure.Story;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class WorldLoad {
    public static World load(String path) throws FileNotFoundException {
        World world = new World();
        File directory = new File(path);
        if(!directory.exists()){
            throw new FileNotFoundException(path+" not found");
        }
        File[] contents = directory.listFiles();
        for ( File f : contents) {
            if(f.getName().equals("Events")){
                world.eventMap=new HashMap<>();
                world.eventMap=loadEventMap(f.getAbsolutePath());
            }
            System.out.println(f.getName());
        }
        return world;
    }

    private static HashMap<String, Event> loadEventMap(String path) {
        HashMap<String,Event> eventMap=new HashMap<>();
        File directory = new File(path);
        File[] contents = directory.listFiles();
        String content="";
        for(File f: contents){
            try {
                content=Files.readString(Path.of(f.getAbsolutePath()));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(content);
        }
        return eventMap;
    }
}
