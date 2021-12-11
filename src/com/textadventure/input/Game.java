package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.Story.World;
import com.textadventure.exeptions.*;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;
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

    public static void start(String path) {
        //TODO: Welt laden
        //World.load(path);
        //TODO schauen ob start gibt
        //World.eventMap.get(World.eventKeyMap.get("start"));
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> cmd;
        String input;

        while (true) {
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
                    Tool tool = World.toolMap.get(cmd.get(1));
                    if (tool == null) {
                        try {
                            throw new NotAToolException(cmd.get(1));
                        } catch (NotAToolException e) {
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
                        }
                    }
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
                    Exit exit = World.exitMap.get(cmd.get(1));
                    Room room = World.roomMap.get(cmd.get(1));
                    if (exit == null) {
                        try {
                            throw new ExitNotFoundException(cmd.get(1));
                        } catch (ExitNotFoundException e) {
                            if (room == null) {
                                System.out.println(e.getMessage());
                                continue;
                            } else {
                                boolean found = false;
                                for (String ex : World.player.getCurrentRoom().getExits()) {
                                    Exit roomExit = World.exitMap.get(ex);
                                    if (roomExit.getDestination1().equals(room.getName()) || roomExit.getDestination2().equals(room.getName())) {
                                        try {
                                            World.player.changeRoom(ex);
                                            found = true;
                                            continue;
                                        } catch (ExitNotFoundException enf) {
                                            System.out.println(enf.getMessage());
                                            continue;
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
                default:
                    try {
                        throw new CommandNotFoundException(cmd.get(0));
                    } catch (CommandNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
            }
            Event.execEvent(cmd);
        }
    }
}
