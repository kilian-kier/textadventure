package com.textadventure.locations;

import com.textadventure.things.Item;

import java.util.HashMap;

public class World {
    private HashMap<String, Location> locations;
    private HashMap<String, Room> rooms;
    private HashMap<String, Exit> exits;
    private HashMap<String, Item> items;
}
