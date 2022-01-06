package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.characters.NPC;
import com.textadventure.characters.Player;
import com.textadventure.exeptions.*;
import com.textadventure.help.Help;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.story.World;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.util.LinkedList;
import java.util.Scanner;

public class Game {

    /**
     * Der erste Buchstabe eines Wortes wird zu einem Großbuchstaben
     *
     * @param s das zu veränderndes Wort
     * @return das großgeschriebene Wort
     */
    private static String firstCap(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Startet das Spiel
     */
    public static void start() {
        if (World.player == null)
            World.player = new Player("Stefe", "der junge Bursch", World.roomMap.get(World.roomMap.keySet().iterator().next())); //TODO: Player wird nicht richtig initialisiert
        Event.execSingleEvent("start");
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> cmd;
        String input;

        while (true) {
            System.out.printf("%s@%s >>> ", firstCap(World.player.getName()), firstCap(World.player.getCurrentRoom().getName()));
            input = scanner.nextLine();
            cmd = Input.splitInput(input);
            if (cmd == null)
                continue;
            if (cmd.get(0).equals("exit")) {
                World.musicPlayer.stop(false);
                break;
            }
            Tool tool;
            Exit exit;
            Room room;
            String error = null;
            switch (cmd.get(0)) {
                case "nehme":
                    if (cmd.size() != 2 && cmd.size() != 3) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    tool = World.toolMap.get(cmd.get(1));
                    if (tool == null) {
                        System.out.println(new ElementNotFoundException(cmd.get(1), "Tool").getMessage());
                        continue;
                    }
                    if (tool.isInteractable()) {
                        if (cmd.size() == 2) {
                            if (tool.getCurrentContainer() != null) {
                                if (!tool.getCurrentContainer().equals(World.player.getCurrentRoom().getName())) {
                                    System.out.println(new ItemNotFoundException(tool.getName()).getMessage());
                                    continue;
                                }
                                try {
                                    tool.changeContainer("player");
                                } catch (ItemNotFoundException e) {
                                    System.out.println(e.getMessage());
                                    continue;
                                }
                            } else {
                                //TODO: Exception
                                continue;
                            }
                        } else {
                            if (!World.player.getCurrentRoom().getContainers().contains(tool.getCurrentContainer())) {
                                System.out.println(new ItemNotFoundException(tool.getName()).getMessage());
                                continue;
                            }
                            try {
                                tool.changeContainer("player");
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                        System.out.println(firstCap(World.player.getName()) + " hat nun folgendes in seinem Rucksack: " + firstCap(tool.getName()));
                    }
                    break;
                case "gehe":
                    if (cmd.size() != 2) {
                        System.out.println(new CommandSyntaxException(cmd.get(0)).getMessage());
                        continue;
                    }
                    exit = World.exitMap.get(cmd.get(1));
                    room = World.roomMap.get(cmd.get(1));
                    if (exit == null) {
                        try {
                            throw new ExitNotFoundException(cmd.get(1));
                        } catch (ExitNotFoundException e) {
                            if (room == null) {
                                System.out.println(e.getMessage());
                                continue;
                            } else {
                                boolean found = false;
                                if (World.player.getCurrentRoom().getName().equals(room.getName())) {
                                    System.out.println(firstCap(World.player.getName()) + " ist bereits hier");
                                    break;
                                }
                                for (String ex : World.player.getCurrentRoom().getExits()) {
                                    Exit roomExit = World.exitMap.get(ex);
                                    if (roomExit.getDestination1().equals(room.getName()) || roomExit.getDestination2().equals(room.getName())) {
                                        try {
                                            if (roomExit.isInteractable()) {
                                                World.player.changeRoom(ex);
                                                World.musicPlayer.play();
                                                System.out.println(firstCap(World.player.getName()) + " geht zu " + firstCap(World.player.getCurrentRoom().getName()));
                                            }
                                            break;
                                        } catch (ExitNotFoundException enf) {
                                            System.out.println(enf.getMessage());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        try {
                            if (exit.isInteractable()) {
                                World.player.changeRoom(exit.getName());
                                World.musicPlayer.play();
                                System.out.println(firstCap(World.player.getName()) + " geht zu " + firstCap(World.player.getCurrentRoom().getName()));
                            }
                        } catch (ExitNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    break;
                case "lege":
                    // NO BREAK
                case "gebe":
                    /*World.containerMap.get(cmd.get(2)).addTool(cmd.get(1));
                    World.containerMap.get(World.toolMap.get(cmd.get(1)).getCurrentContainer()).removeTool(cmd.get(1));
                    World.toolMap.get(cmd.get(1)).setContainer(cmd.get(2));

                    break;*/
                    if (cmd.size() != 2 && cmd.size() != 3) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            error = e.getMessage();
                            break;
                        }
                    }

                    tool = World.toolMap.get(cmd.get(1));
                    if (cmd.size() == 2) {
                        if (tool == null) {
                            System.out.println(new ElementNotFoundException(cmd.get(1), "Tool").getMessage());
                            break;
                        }
                        try {
                            tool.changeContainer(World.player.getCurrentRoom().getName());
                            System.out.println(firstCap(World.player.getName()) + " hat folgendes auf den Boden gelegt: " + firstCap(tool.getName()));
                        } catch (ItemNotFoundException e) {
                            error = e.getMessage();
                            continue;
                        }
                    } else {
                        if (!World.containerMap.containsKey(cmd.get(2))) {
                            try {
                                throw new ElementNotFoundException(cmd.get(2), "Container");
                            } catch (ElementNotFoundException e) {
                                error = e.getMessage();
                                break;
                            }
                        }
                        try {
                            tool.changeContainer(cmd.get(2));
                            System.out.println(firstCap(World.player.getName()) + " hat folgendes in " + firstCap(cmd.get(2)) + " gegeben: " + firstCap(tool.getName()));
                        } catch (ItemNotFoundException e) {
                            error = e.getMessage();
                            break;
                        }
                    }
                case "spreche":
                    //TODO Eingabe von keiner Zahl, Keine Fragen
                    if (cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (!World.npcMap.containsKey(cmd.get(1))) {
                        System.out.printf("Es gibt hier niemanden mit dem Namen %s\n", firstCap(cmd.get(1)));
                        continue;
                    } else {
                        NPC n = World.npcMap.get(cmd.get(1));
                        if (!n.getRoom().equals(World.player.getCurrentRoom().getName())) {
                            System.out.printf("Es gibt hier niemanden mit dem Namen %s\n", firstCap(n.getName()));
                            break;
                        }
                        if (n.getDialog().getDialog().size() != 0) {
                            System.out.print("Wähle eine Frage: \n");
                            for (int i = 0; i < n.getDialog().getDialog().size(); i++) {
                                System.out.printf("[%d] ", i + 1);
                                System.out.println(n.getDialog().getDialog().get(i)[0]);
                            }
                            int x;
                            do {
                                x = scanner.nextInt();
                            } while (x <= 0 || x > n.getDialog().getDialog().size());
                            System.out.println(n.getDialog().getDialog().get(x - 1)[1]);
                            if (n.getDialog().getDialog().get(x - 1)[2] != null) {
                                Event.execSingleEvent(n.getDialog().getDialog().get(x - 1)[2]);
                            }
                            scanner.nextLine();
                        } else {
                            System.out.printf("%s hat zurzeit keine Zeit mit %s zu sprechen\n", firstCap(n.getName()),firstCap(World.player.getName()));
                        }
                    }
                    break;
                case "schaue":
                    if (cmd.size() != 1 && cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (cmd.size() == 1 || cmd.get(1).equals(World.player.getCurrentRoom().getName())) {
                        if (World.player.getCurrentRoom().getTools().size() != 0 || World.player.getCurrentRoom().getContainers().size() != 0) {
                            System.out.print("Es befinden sich hier folgende Dinge: ");
                            for (String t : World.player.getCurrentRoom().getTools())
                                System.out.print(firstCap(t) + " ");
                            for (String c : World.player.getCurrentRoom().getContainers())
                                System.out.print(firstCap(c) + " ");
                            System.out.println();
                        }

                        if (World.player.getCurrentRoom().getNpcs().size() != 0) {
                            System.out.print("Hier befinden sich folgende NPCs: ");
                            for (String n : World.player.getCurrentRoom().getNpcs())
                                System.out.print(firstCap(n) + " ");
                            System.out.println();
                        }

                        System.out.printf("%s befindet sich an folgendem Ort: %s\n", firstCap(World.player.getName()), firstCap(World.player.getCurrentRoom().getName()));
                        System.out.print("Der Ort befindet sich in " + firstCap(World.player.getCurrentRoom().getLocation()));
                        if (World.player.getCurrentRoom().getExits().size() != 0) {
                            System.out.println();
                            for (String e : World.player.getCurrentRoom().getExits())
                                try {
                                    System.out.println();
                                    if ((World.exitMap.get(e).getDestination1()).equals(World.player.getCurrentRoom().getName())) {
                                        System.out.print(firstCap(World.exitMap.get(e).getDescription2()));
                                    } else {
                                        System.out.print(firstCap(World.exitMap.get(e).getDescription1()));
                                    }
                                } catch (Exception f) {
                                    System.out.println("Ausnahme Fehler in Schaue");
                                }
                            System.out.println();
                        } else {
                            System.out.println("\nEs gibt keinen Ausweg");
                        }

                    } else {
                        if (World.containerMap.containsKey(cmd.get(1))) {
                            Container container;
                            try {
                                container = World.player.getCurrentRoom().getContainer(cmd.get(1));
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                            assert container != null;
                            if (container.getTools().size() != 0) {
                                System.out.print("In diesem Container befinden sich folgende Dinge: ");
                                for (String t : container.getTools())
                                    System.out.print(firstCap(t) + " ");
                                System.out.println();
                            } else
                                System.out.println("Dieser Container ist leer.");
                        } else if (cmd.get(1).equals("inventar") || cmd.get(1).equals(World.player.getName()) || cmd.get(1).equals("rucksack")) {
                            if (World.player.getTools().size() != 0) {
                                System.out.print("In seinem Rucksack befinden sich folgende Dinge: ");
                                for (String t : World.player.getTools())
                                    System.out.print(firstCap(t) + " ");
                                System.out.println();
                            } else
                                System.out.println("Sein Rucksack ist leer.");
                        } else if (World.player.getCurrentRoom().getExits().contains(cmd.get(1))) {
                            Exit e = World.exitMap.get(cmd.get(1));
                            if (e != null) {
                                if (World.player.getCurrentRoom().getName().equals(e.getDestination1()))
                                    System.out.println("Dieser Ausgang führt nach: " + firstCap(e.getDestination2()));
                                else if (World.player.getCurrentRoom().getName().equals(e.getDestination2()))
                                    System.out.println("Dieser Ausgang führt nach: " + firstCap(e.getDestination1()));
                            } else {
                                try {
                                    throw new ExitNotFoundException(cmd.get(1));
                                } catch (ExitNotFoundException e1) {
                                    System.out.println(e1.getMessage());
                                    continue;
                                }
                            }

                        } else if (World.toolMap.containsKey(cmd.get(1))) {
                            Tool t = World.toolMap.get(cmd.get(1));
                            System.out.println(firstCap(t.getName()) + ": " + t.getDescription());
                        } else if (World.npcMap.containsKey(cmd.get(1))) {
                            //TODO: NPCs
                            try {
                                throw new CommandSyntaxException(cmd.get(0));
                            } catch (CommandSyntaxException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                    }
                    break;
                case "info":
                    //NO BREAK
                case "untersuche":
                    if (cmd.size() != 1 && cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (cmd.size() == 1 || cmd.get(1).equals(World.player.getCurrentRoom().getName())) {
                        System.out.println(World.player.getCurrentRoom().getDescription());
                        System.out.println("Der Ort befindet sich in " + firstCap(World.player.getCurrentRoom().getLocation()));
                    } else {
                        if (cmd.get(1).equals(World.player.getName())) {
                            System.out.println(World.player.getDescription());
                        } else if (cmd.get(1).equals("inventar") || cmd.get(1).equals("rucksack")) {
                            System.out.println(World.player.getToolsContainer().getDescription());
                        } else if (World.player.getCurrentRoom().getContainers().contains(cmd.get(1))) {
                            Container c = World.containerMap.get(cmd.get(1));
                            System.out.println(c.getDescription());
                        } else if (World.player.getTools().contains(cmd.get(1))) {
                            Tool t = World.toolMap.get(cmd.get(1));
                            System.out.println(t.getDescription());
                        } else if (World.player.getCurrentRoom().getExits().contains(cmd.get(1))) {
                            Exit e = World.exitMap.get(cmd.get(1));
                            System.out.println(e.getDescription());
                        } else if (World.player.getCurrentRoom().getNpcs().contains(cmd.get(1))) {
                            NPC n = World.npcMap.get(cmd.get(1));
                            System.out.println(n.getDescription());
                        } else if (cmd.get(1).equals(World.player.getCurrentRoom().getLocation())) {
                            Location l = World.locationMap.get(cmd.get(1));
                            System.out.println(l.getDescription());
                        } else {
                            try {
                                throw new CommandSyntaxException(cmd.get(0));
                            } catch (CommandSyntaxException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                    }
                    break;
                case "musik":
                    if (cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (cmd.get(1).equals("start")) {
                        World.musicPlayer.play();
                    } else if (cmd.get(1).equals("stop")) {
                        World.musicPlayer.stop(false);
                    }
                    break;
                case "clear":
                    System.out.println("\033[2J");
                    System.out.println("\033[H");
                    break;
                case "hilfe":
                    try {
                        if (cmd.size() > 1) {
                            System.out.println(Help.help("GameHelp", cmd.get(1)));
                        } else {
                            System.out.println(Help.help("GameHelp", null));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "aktion":
                    break;
                default:
                    try {
                        throw new CommandNotFoundException(cmd.get(0));
                    } catch (CommandNotFoundException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
            }
            if (!Event.execEvent(cmd) && error != null) {
                System.out.println(error);
            }
        }
    }
}
