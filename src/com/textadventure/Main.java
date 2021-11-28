package com.textadventure;

import com.textadventure.Story.World;
import com.textadventure.Story.WorldMaker;
import com.textadventure.input.Input;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            Input.edit("Hallo Welt");
            World world = new World();
            world.load("/home/martin/IdeaProjects/textadventure/0_Story2");
        }catch (FileNotFoundException e){}
    }

}
