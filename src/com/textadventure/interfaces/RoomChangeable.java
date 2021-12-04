package com.textadventure.interfaces;

import com.textadventure.exeptions.*;

public interface RoomChangeable {
    void changeRoom(String exit) throws ExitNotFoundException;

    void goBack() throws NoBackException;
}
