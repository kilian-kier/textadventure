package com.textadventure.interfaces;

import com.textadventure.exeptions.NoBackException;
import com.textadventure.locations.Exit;

public interface RoomChangeable {
    void changeRoom(String exit);

    void goBack() throws NoBackException;
}
