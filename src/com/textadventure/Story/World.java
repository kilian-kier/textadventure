package com.textadventure.Story;

import com.textadventure.GameElement;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.ElementNotFoundException;
import com.textadventure.input.Input;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Item;

import javax.swing.text.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;

public class World {
    public HashMap<String, Room> roomMap ;
    public HashMap<String,Location> locationMap;
    public HashMap<String, Item> itemMap;
    public HashMap<String, NPC> npcMap;
    //TODO Add Events (in Rooms)
    public HashMap<String,Event> eventMap;


    private void addLocation(Location location) {
        locationMap.put(location.getName(),location);
    }
    private void addRoom(Room room) {
        roomMap.put(room.getName(), room);
    }
    private void addNPC(NPC npc){
        npcMap.put(npc.getName(), npc);
    }
    private void addItem(Item item){
        itemMap.put(item.getName(),item);
    }

    private void inputGameElement(GameElement element){
        inputName(element);
        inputInfo(element);
        inputDescription(element);
    }
    private void inputName(GameElement element){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Name");
        while((input= scanner.nextLine()).length()==0){}
        if(input.equals("exit")) return;
        element.setName(input);
    }
    private void inputInfo(GameElement element){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Info");
        while((input= scanner.nextLine()).length()==0){}
        if(input.equals("exit")) return;
        element.setInfo(input);
    }
    private void inputDescription(GameElement element){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Info");
        while((input= scanner.nextLine()).length()==0){}
        if(input.equals("exit")) return;
        element.setDescription(input);
    }
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
        }
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

        }*/
    }

    public void worldEditor(String path){
        System.out.println("Willkommen im Welten Editor\n");
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> commands = new ArrayList();
        String input;
        boolean exit = false;
        while(!exit) {
            input = scanner.nextLine();
            input=input.toLowerCase();
            commands = Input.StringToList(input);
            try {
                switch (commands.get(0)) {
                    case "new":
                        try {
                            newGameElement(commands.get(1));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("Zu wenig Parameter");
                        }
                        break;
                    case "edit":
                        try {
                            editGameElement(commands.get(1));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("Zu wenig Parameter");
                        }
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        System.err.println("Befehl nicht verfügbar");
                        break;
                }
        }catch(IndexOutOfBoundsException e){}}
    }

    private void editGameElement(String name) {

    }

    private void newGameElement(String type){
        switch(type){
            case "npc":
                NPC npc = new NPC(" "," "," ");
                inputGameElement(npc);
                break;
            case "exit":
                break;
            case "location":
                break;
            case "room":
                break;
            case "item":
                break;
            case "event":
                //TODO Add Event
                break;
            default:
                System.err.println("Element nicht Verfügbar");
        }
    }





    private GameElement getElement(String name) throws ElementNotFoundException {
        if(roomMap.containsKey(name))return roomMap.get(name);
        if(locationMap.containsKey(name))return locationMap.get(name);
        if(itemMap.containsKey(name))return itemMap.get(name);
        if(npcMap.containsKey(name))return npcMap.get(name);
        throw new ElementNotFoundException(name);
    }

    private void loadEventMap(String path) {
        /*File directory = new File(path);
        File[] contents= directory.listFiles();
        String content="";
        for(File f: contents){
            try {
                content= Files.readString(Path.of(f.getAbsolutePath()));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(content);
        }*/
    }

}
