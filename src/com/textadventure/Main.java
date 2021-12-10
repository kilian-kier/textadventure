package com.textadventure;

import com.textadventure.Event.ContainerDiff;
import com.textadventure.Story.World;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Main {

    public static void main(java.lang.String[] args) {
       /*
       world.worldEditor("/home/kilian/IdeaProjects/textadventure/1_Story");*/
        /*ContainerDiff diff = new ContainerDiff("nix");
        diff.setRoom("meinRaum");
        diff.checkValidity();
        System.out.println(diff);*/



        World.worldEditor("world.world");
        Location village = new Location("Dorf", "Ein Dorf in Lusina");
        Room house = new Room("Haus", "Ein kleines Haus mit Garten");
        Room yard = new Room("Garten", "Ein Garten mit hinter dem Haus");
        Exit door = new Exit("Tür", "Hier geht es zum Garten");
        Container chest = new Container("Truhe", "Eine alte Trueh in der Ecke");
        Tool hammer = new Tool("Hammer", "Ein alter rostiger Hammer");
        hammer.setContainer("Haus");
        house.addTool("Hammer");
        Tool apple = new Tool("Apfel", "Ein reifer roter Apfel");

        World.locationMap.put(village.getName(), village);
        World.roomMap.put(house.getName(), house);
        World.roomMap.put(yard.getName(), yard);
        World.exitMap.put(door.getName(), door);
        World.containerMap.put(chest.getName(), chest);
        World.toolMap.put(hammer.getName(), hammer);
        World.toolMap.put(apple.getName(), apple);

        door.setDestination1(house.name);
        door.setDestination2(yard.name);

        house.addExit(door.getName());
        house.addContainer(chest.getName());
        chest.addTool(hammer.getName());
        yard.addExit(door.getName());
        yard.addTool(apple.getName());

        village.addRoom(house.name);
        village.addRoom(yard.name);

        //Github Copilot hot mo de description vorgschlog, obo de isch et letz
        World.player = new Player("Stefe", "Manchmal frage ich mich, wer bin ich überhaupt?", house);

        System.out.println(World.player.getCurrentRoom().getName() + " : " + World.player.getPreviousRoom());

        try {
            World.toolMap.get("Hammer").changeContainer("player");
            System.out.println("Du hast nun einen " + World.player.getTools().toString() + " in deinem Rucksack\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        try {
            World.player.changeRoom("Tür");
        } catch (ExitNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(World.player.getCurrentRoom().getName() + " : " + World.player.getPreviousRoom().getName());

        try {
            World.toolMap.get("Hammer").changeContainer("Garten");
            System.out.println("Es befinden sich nun " + World.player.getCurrentRoom().getTools().toString() + " im Garten");
            World.toolMap.get("Apfel").changeContainer("Garten");
            System.out.println("Du hast nun einen " + World.player.getTools().toString() + " aufgehoben\n");
        } catch (ItemNotFoundException  e) {
            e.printStackTrace();
        }

        try {
            World.player.goBack();
        } catch (NoBackException e) {
            e.printStackTrace();
        }
        System.out.println(World.player.getCurrentRoom().getName() + " : " + World.player.getPreviousRoom().getName());

        try {
            World.toolMap.get("Apfel").changeContainer("Truhe");
            System.out.println("Du hast nun den " + World.containerMap.get("Truhe").getTools().toString() + " in die Truhe gelegt");
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }

        /*
        Location village = new Location("Dorf", "Ein Dorf in Lusina","Ein Dorf in Lusina");
        Room house = new Room("Haus", "Ein kleines Haus mit Garten","Ein kleines Haus mit Garten","Dorf");
        Room yard = new Room("Garten", "Ein Garten mit hinter dem Haus","Ein Garten mit hinter dem Haus","Dorf");
        Exit houseToYard = new Exit("Gartentür",  "Hier geht es zum Garten","Hier geht es zum Garten",yard);
        Exit YardToHouse = new Exit("Hintereingang","Hier geht es ins Haus","Hier geht es ins Haus",house);
        house.addExit(houseToYard);
        yard.addExit(YardToHouse);
        village.addRoom(house);
        village.addRoom(yard);
        world.addLocation(village);

        Player player = new Player("Stefe", "Manchmal frage ich mich, wer bin ich überhaupt?","Ich bin Stefe" ,house);
        try {
            player.goBack();
        } catch (NoBackException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.currentRoom.getName());
        player.changeRoom("Gartentür");
        System.out.println(player.currentRoom.getName());
        player.changeRoom("Hintereingang");
        System.out.println(player.currentRoom.getName());
        try {
            player.goBack();
        } catch (NoBackException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.currentRoom.getName());*/
    }
}
