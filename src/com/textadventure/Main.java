package com.textadventure;

import com.textadventure.help.Help;
import com.textadventure.story.*;

public class Main {

    public static void main(java.lang.String[] args) {
       /*
       world.worldEditor("/home/kilian/IdeaProjects/textadventure/1_Story");*/
        /*ContainerDiff diff = new ContainerDiff("nix");
        diff.setRoom("meinRaum");
        diff.checkValidity();
        System.out.println(diff);*/

        Help.load();
        World.worldEditor("music.world");

        /*World.worldEditor("world.world");
        Map map = World.containerMap;

        Location village = new Location("Dorf", "Ein Dorf in Lusina");
        Room house = new Room("Haus", "Ein kleines Haus mit Garten");
        Room yard = new Room("Garten", "Ein Garten mit hinter dem Haus");
        Exit door = new Exit("Tür", "Hier geht es zum Garten");
        Container chest = new Container("Truhe", "Eine alte Truhe in der Ecke");
        chest.setContainer("Haus");
        house.addContainer("Truhe");
        Tool hammer = new Tool("Hammer", "Ein alter rostiger Hammer");
        hammer.setContainer("Truhe");
        chest.addTool("Hammer");
        Tool apple = new Tool("Apfel", "Ein reifer roter Apfel");
        apple.setContainer("Garten");
        yard.addTool("Apfel");

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
        house.setLocation(village.getName());
        yard.setLocation(village.getName());

        World.player = new Player("Stefe", "Ein junger Mann", house);*/

        //Game.start("world.world");
        /*
        //Github Copilot hot mo de description vorgschlog, obo de isch et letz
        World.player = new Player("Stefe", "Manchmal frage ich mich, wer bin ich überhaupt?", house);

        System.out.println(World.player.getCurrentRoom().getName() + " : " + World.player.getPreviousRoom());

        try {
            World.toolMap.get("Hammer").changeContainer("player");
            System.out.println("Du hast nun einen " + World.player.getTools().toString() + " in deinem Rucksack\n");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
