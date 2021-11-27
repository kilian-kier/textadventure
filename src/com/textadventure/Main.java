package com.textadventure;

import com.textadventure.Story.WorldLoad;
import com.textadventure.Story.WorldMaker;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            WorldLoad.load("/home/martin/IdeaProjects/textadventure/0_Story");
        }catch (FileNotFoundException e){}
    }

}
