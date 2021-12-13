package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.Story.World;
import com.textadventure.exeptions.*;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.util.LinkedList;
import java.util.Scanner;

public class Game {
    public static AdvancedArray<String> commands = new AdvancedArray<>(new String[]{
            "nehme",
            "gehe",
            "spreche",
            "schaue",
            "untersuche",
            "gebe",
            "aktion"
    });

    private static String firstCap(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void start(String path) {
        //TODO: Welt laden
        //World.load(path);
        //TODO schauen ob start gibt
        //World.eventMap.get(World.eventKeyMap.get("start"));
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> cmd;
        String input;

        while (true) {
            System.out.printf("%s@%s >>> ", firstCap(World.player.getName()), firstCap(World.player.getCurrentRoom().getName()));
            input = scanner.nextLine();
            cmd = World.splitInput(input);
            if (cmd == null)
                continue;
            if (cmd.get(0).equals("exit"))
                break;
            if (!commands.contains(cmd.get(0))) {
                try {
                    throw new CommandNotFoundException(cmd.get(0));
                } catch (CommandNotFoundException e) {
                    e.getMessage();
                }
            }
            Tool tool;
            Exit exit;
            Room room;
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
                        }
                    }
                    System.out.println(firstCap(World.player.getName()) + " befindet sich nun in: " + firstCap(World.player.getCurrentRoom().getName()));
                    break;
                case "lege":
                    //TODO lege isch oanfoch gebe aufn Boden odo?, deswegn koan break
                case "gebe":
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
                    break;
                case "spreche":
                    if (cmd.size() != 2) {
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    //TODO: sprechen
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
                                }
                            }

                        } else if (World.toolMap.containsKey(cmd.get(1))) {
                            Tool t = World.toolMap.get(cmd.get(1));
                            System.out.println(firstCap(t.getName()) + ": " + t.getDescription());
                        } else {
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
            //TODO: Clear screen
        }
    }
}
