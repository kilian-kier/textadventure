package com.textadventure.story;

import com.textadventure.Event.Event;
import com.textadventure.characters.NPC;
import com.textadventure.locations.Exit;
import com.textadventure.locations.Location;
import com.textadventure.locations.Room;
import com.textadventure.things.Container;
import com.textadventure.things.Tool;

/**
 * Überprüft die Welt auf Fehler
 */
public class Checker {
    /**
     * Funktion überprüft die Welt auf Fehler. Bei Fehlern werden passende Meldungen ausgegeben.
     * Jedes Element der Welt besitzt eine eigene check Funktion, welche hier aufgerufen wird, sodass sich die Elemente selbst überprüfen.
     * @return true, wenn es keine Fehler gibt, ansonsten false
     */
    public static boolean check(boolean fix){
        //TODO fix
        boolean ret=true;
        for (Room room: World.roomMap.values() ) {
            if(!room.check(fix)){
                ret=false;
            }
        }
        for (Container container: World.containerMap.values() ) {
            if(World.roomMap.containsKey((container.getName()))) continue;
            if(!container.check(fix)){
                ret=false;
            }
        }
        for (Location location: World.locationMap.values() ) {
            if(!location.check()){
                ret=false;
            }
        }
        for (Tool tool: World.toolMap.values() ) {
            if(!tool.check(fix)){
                ret=false;
            }
        }
        for (NPC npc: World.npcMap.values() ) {
            if(!npc.check(fix)){
                ret=false;
            }
        }
        for (Exit exit: World.exitMap.values() ) {
            if(!exit.check(fix)){
                ret=false;
            }
        }
        for (Event event: World.eventMap.values() ) {
            if(!event.check(fix)){
                ret=false;
            }
        }
        if(World.eventKeyMap.get("start")==null){
            System.out.println("Start Event nicht gefunden");
            ret=false;
        }
        if(World.player==null){
            System.out.println("Player nicht gefunden");
        }
        return ret;
    }

}
