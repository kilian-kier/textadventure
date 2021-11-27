package com.textadventure.interfaces;

import com.textadventure.exeptions.NoBackException;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Room;

public interface RoomChangeable {
    void changeRoom(Exit exit);

    void goBack() throws NoBackException;
}
