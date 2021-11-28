package com.textadventure.locations;

import com.textadventure.things.Item;

import java.util.HashMap;

public class World {
    private final HashMap<java.lang.String, Location> locations;

    public World() {
        locations = new HashMap<>();
    }

    public void addLocation(Location location) {
        locations.put(location.getName(), location);
    }
}
