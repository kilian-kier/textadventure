package com.textadventure.interfaces;

import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.KeyAlreadyUsedException;
import com.textadventure.things.Tool;

public interface Containable {
    void put (Tool tool) throws KeyAlreadyUsedException;
    Tool take (String name) throws ItemNotFoundException;
}
