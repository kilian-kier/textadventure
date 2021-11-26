package com.textadventure.interfaces;

import com.textadventure.locations.Exit;

public interface RoomChangeable {
    void changeRoom(Exit exit);
    void goBack();
}
