package com.textadventure.interfaces;

import com.textadventure.exeptions.ExitNotFoundException;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.locations.Exit;

public interface RoomChangeable {
    void changeRoom(String exit) throws ExitNotFoundException;

    void goBack() throws NoBackException;
}
