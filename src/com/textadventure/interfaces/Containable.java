package com.textadventure.interfaces;

import com.textadventure.things.Tool;

public interface Containable {
    public void put (Tool tool);
    public Tool take (String name);
}
