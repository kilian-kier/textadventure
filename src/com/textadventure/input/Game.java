package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.story.Checker;
import com.textadventure.help.Help;
import com.textadventure.story.LoadStoreWorld;
import com.textadventure.story.World;
import com.textadventure.characters.NPC;
import com.textadventure.exeptions.*;
import com.textadventure.gamemusic.MusicPlayer;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     *
     * @param path zur Weltdatei
     */
    public static void start(String path) {
        try {
            LoadStoreWorld.load(path);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Event.execSingleEvent("start");
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> cmd;
        String input;

        World.musicPlayer = new MusicPlayer(World.player.getCurrentRoom().getMusicPath());
        World.musicPlayer.play();

        while (true) {
            System.out.printf("%s@%s >>> ", firstCap(World.player.getName()), firstCap(World.player.getCurrentRoom().getName()));
            input = scanner.nextLine();
            cmd = Input.splitInput(input);
            if (cmd == null)
                continue;
            if (cmd.get(0).equals("exit")) {
                World.musicPlayer.stop();
                break;
            }
            Tool tool;
            Exit exit;
            Room room;
            switch (cmd.get(0)) {
                case "check":
                    Checker.check();
                    break;
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
                        try {
                            throw new ElementNotFoundException(cmd.get(1), "Tool");
                        } catch (ElementNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (cmd.size() == 2) {
                        if (!tool.getCurrentContainer().equals(World.player.getCurrentRoom().getName())) {
                            try {
                                throw new ItemNotFoundException(tool.getName());
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                        try {
                            tool.changeContainer("player");
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    } else {
                        if (!World.player.getCurrentRoom().getContainers().contains(tool.getCurrentContainer())) {
                            try {
                                throw new ItemNotFoundException(tool.getName());
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                        try {
                            tool.changeContainer("player");
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    System.out.println(firstCap(World.player.getName()) + " hat nun folgendes in seinem Rucksack: " + firstCap(tool.getName()));
                    break;
                case "gehe":
                    if (cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
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
                                    System.out.println(firstCap(World.player.getName()) + " ist bereits in diesem Raum.");
                                    break;
                                }
                                for (String ex : World.player.getCurrentRoom().getExits()) {
                                    Exit roomExit = World.exitMap.get(ex);
                                    if (roomExit.getDestination1().equals(room.getName()) || roomExit.getDestination2().equals(room.getName())) {
                                        try {
                                            World.player.changeRoom(ex);
                                            found = true;
                                            break;
                                        } catch (ExitNotFoundException enf) {
                                            System.out.println(enf.getMessage());
                                            break;
                                        }
                                    }
                                }
                                if (!found)
                                    continue;
                            }
                        }
                    } else {
                        try {
                            World.player.changeRoom(exit.getName());
                        } catch (ExitNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    World.musicPlayer.stop();
                    World.musicPlayer = new MusicPlayer(World.player.getCurrentRoom().getMusicPath());
                    World.musicPlayer.play();
                    System.out.println(firstCap(World.player.getName()) + " befindet sich nun in: " + firstCap(World.player.getCurrentRoom().getName()));
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
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }

                    tool = World.toolMap.get(cmd.get(1));
                    if (cmd.size() == 2) {
                        if (tool == null) {
                            try {
                                throw new ElementNotFoundException(cmd.get(1), "Tool");
                            } catch (ElementNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                        try {
                            tool.changeContainer(World.player.getCurrentRoom().getName());
                            System.out.println(firstCap(World.player.getName()) + " hat folgendes auf den Boden gelegt: " + firstCap(tool.getName()));
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    } else {
                        if (!World.containerMap.containsKey(cmd.get(2))) {
                            try {
                                throw new ElementNotFoundException(cmd.get(2), "Container");
                            } catch (ElementNotFoundException e) {
                                System.out.println(e.getMessage());
                                continue;
                            }
                        }
                        try {
                            tool.changeContainer(cmd.get(2));
                            System.out.println(firstCap(World.player.getName()) + " hat folgendes in " + firstCap(cmd.get(2)) + " gegeben: " + firstCap(tool.getName()));
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                case "spreche":
                    if (cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    if (!World.npcMap.containsKey(cmd.get(1))) {
                        try {
                            throw new ElementNotFoundException(cmd.get(1), "NPC");
                        } catch (ElementNotFoundException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.print("Wähle eine Frage: ");
                        NPC n = World.npcMap.get(cmd.get(1));
                        for (int i = 0; i < n.getDialog().getDialog().size(); i++) {
                            System.out.printf("[%d] ", i + 1);
                            System.out.println(n.getDialog().getDialog().get(i)[0]);
                        }
                        int x;
                        do {
                            x = scanner.nextInt();
                        } while (x > 0 && x <= n.getDialog().getDialog().size());
                        ArrayList<String> event = new ArrayList<>();
                        event.add(n.getDialog().getDialog().get(x - 1)[1]);
                        Event.execEvent(event);
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
                            System.out.print("In diesem Raum befinden sich folgende Dinge: ");
                            for (String t : World.player.getCurrentRoom().getTools())
                                System.out.print(firstCap(t) + " ");
                            for (String c : World.player.getCurrentRoom().getContainers())
                                System.out.print(firstCap(c) + " ");
                            System.out.println();
                        }

                        if (World.player.getCurrentRoom().getNpcs().size() != 0) {
                            System.out.print("In diesem Raum befinden sich folgende NPCs: ");
                            for (String n : World.player.getCurrentRoom().getNpcs())
                                System.out.print(firstCap(n) + " ");
                            System.out.println();
                        }

                        if (World.player.getCurrentRoom().getExits().size() != 0) {
                            System.out.print("In diesem Raum befinden sich folgende Ausgänge: ");
                            for (String e : World.player.getCurrentRoom().getExits())
                                System.out.print(firstCap(e) + " ");
                            System.out.println();
                        } else
                            System.out.println("In diesem Raum befinden sich keine Ausgänge.");

                        System.out.println("Dieser Raum befindet sich in: " + firstCap(World.player.getCurrentRoom().getLocation()));
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
                        System.out.println("Dieser Raum befindet sich in: " + firstCap(World.player.getCurrentRoom().getLocation()));
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
                case "music":
                    if (cmd.size() != 1) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
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
                default:
                    try {
                        throw new CommandNotFoundException(cmd.get(0));
                    } catch (CommandNotFoundException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
            }
            Event.execEvent(cmd);
        }
    }
}
