package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.Story.World;
import com.textadventure.exeptions.*;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
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

    public static void start(String path) {
        //TODO: Welt laden
        //World.load(path);
        //TODO schauen ob start gibt
        //World.eventMap.get(World.eventKeyMap.get("start"));
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> cmd;
        String input;

        while (true) {
            String str = World.player.getCurrentRoom().getName();
            System.out.printf("%s >>> ", str.substring(0, 1).toUpperCase() + str.substring(1));
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
                    System.out.println(World.player.getName() + " hat nun " + tool.getName() + " in seinem Rucksack.");
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
                                    System.out.println("Du bist bereits in diesem Raum.");
                                    break;
                                }
                                for (String ex : World.player.getCurrentRoom().getExits()) {
                                    Exit roomExit = World.exitMap.get(ex);
                                    if (roomExit.getDestination1().equals(room.getName()) || roomExit.getDestination2().equals(room.getName())) {
                                        try {
                                            World.player.changeRoom(ex);;
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
                    System.out.println(World.player.getName() + " befindet sich nun in " + World.player.getCurrentRoom().getName());
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
                            System.out.println(World.player.getName() + " hat " + tool.getName() + " auf den Boden gelegt.");
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
                            System.out.println(World.player.getName() + " hat " + tool.getName() + " in " + cmd.get(2) + " gegeben.");
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                default:
                    try {
                        throw new CommandNotFoundException(cmd.get(0));
                    } catch (CommandNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
            }
            Event.execEvent(cmd);
            //TODO: Clear screen
        }
    }
}
