package com.textadventure.input;

import com.textadventure.Event.Event;
import com.textadventure.Story.World;
import com.textadventure.exeptions.CommandNotFoundException;
import com.textadventure.exeptions.CommandSyntaxException;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.NotAToolException;
import com.textadventure.things.Tool;

import java.util.*;

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
        World.eventMap.get(World.eventKeyMap.get("start")).exec();
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
                    if (cmd.size() != 2 && cmd.size() != 3)
                        try {
                            throw new CommandSyntaxException(cmd.get(0));
                        } catch (CommandSyntaxException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    if (!cmd.get(1).getClass().toString().equals(Tool.class.toString())) {
                        try {
                            throw new NotAToolException(cmd.get(1));
                        } catch (NotAToolException e) {
                            e.getMessage();
                        }
                    }
                    if (cmd.size() == 2) {
                        Tool tool = World.toolMap.get(cmd.get(1));
                        if (!tool.getCurrentContainer().equals(World.player.getCurrentRoom().getName())) {
                            try {
                                throw new ItemNotFoundException(tool.getName());
                            } catch (ItemNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        try {
                            tool.changeContainer("player");
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            }
            Event.execEvent(cmd);
        }
    }
}
