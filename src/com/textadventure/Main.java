package com.textadventure;

import com.textadventure.characters.Player;
import com.textadventure.exeptions.NoBackException;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.locations.World;

public class Main {

    public static void main(java.lang.String[] args) {
        World world = new World();
        Location village = new Location("Dorf", "Ein Dorf in Lusina");
        Room house = new Room("Haus", "Ein kleines Haus mit Garten");
        Room yard = new Room("Garten", "Ein Garten mit hinter dem Haus");
        Exit houseToYard = new Exit("Gartentür", yard, "Hier geht es zum Garten");
        Exit YardToHouse = new Exit("Hintereingang", house, "Hier geht es ins Haus");
        house.addExit(houseToYard);
        yard.addExit(YardToHouse);
        village.addRoom(house);
        village.addRoom(yard);
        world.addLocation(village);

        Player player = new Player("Stefe", house);
        try {
            player.goBack();
        } catch (NoBackException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.currentRoom.getName());
        player.changeRoom("Gartentür");
        System.out.println(player.currentRoom.getName());
        player.changeRoom("Hintereingang");
        System.out.println(player.currentRoom.getName());
        try {
            player.goBack();
        } catch (NoBackException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.currentRoom.getName());
    }
}
