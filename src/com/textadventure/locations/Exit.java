package com.textadventure.locations;

import com.textadventure.GameElement;
import com.textadventure.story.World;
import com.textadventure.exeptions.ItemNotFoundException;

import java.io.Serializable;

/**
 * Ein Ausgang enthält zwei Räume (destination1/2) zwischen denen er sich befinded. Es gibt eine Doppelte Beschreibung welche in der Mitte mit einem
 * @ getrennt wird.
 */
public class Exit extends GameElement implements Serializable {
    private static final long serialVersionUID = -5624726393622315238L;
    private String destination1;
    private String destination2;

    public Exit(String name, String description) {
        super(name);
        this.description = description; //Beschreibung 1 isch links vor an @ und die Beschreibung 2 ich rechts noch an @
    }

    /**
     * Ändert einen der Räume
     * @param newRoomString neuer Raum
     * @param oneOrTwo Raum bei destination 1 (false), oder 2 (true)
     * @throws ItemNotFoundException Wenn der neue Raum nicht existiert
     * @throws NullPointerException Wenn ungültige Parameter übergeben werden
     */
    public void changeDestination(String newRoomString, boolean oneOrTwo) throws ItemNotFoundException, NullPointerException{
        Room newRoom= World.roomMap.get(newRoomString);
        String destination=destination2;
        if(!oneOrTwo){
            destination=destination1;
        }
        if(destination!=null) {
            Room oldRoom = World.roomMap.get(destination);
            if (oldRoom.getExits().contains(this.name)) {
                oldRoom.getExits().remove(name);
                newRoom.addExit(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newRoom.addExit(this.name);
        }
        this.name=newRoomString;
    }



    public String getDestination2() {
        return destination2;
    }

    public void setDestination2(String destination2) {
        this.destination2 = destination2;
    }

    public String getDestination1() {
        return destination1;
    }

    public void setDestination1(String destination1) {
        this.destination1 = destination1;
    }

    @Override
    public void look() {
        if(World.player.getCurrentRoom().getName().equals(destination1)){
            System.out.println("Da ist ein Ausgang, der nach " + destination2 + " führt.");
        }else{
            System.out.println("Da ist ein Ausgang, der nach " + destination1 + " führt.");
        }
    }

    @Override
    public void investigate() {
        System.out.println(this.description);
    }

    public java.lang.String getName() {
        return this.name;
    }

    public boolean check(){
        boolean ret=true;
        if(World.roomMap.get(destination1)==null){
            System.out.printf("Destination 1 %s von Exit %s existiert nicht\n",destination1,name);
            ret=false;
        }else{
            if(!World.roomMap.get(destination1).getExits().contains(name)){
                System.out.printf("Raum %s wird von Exit %s als Destination 1 referenziert aber nicht umgekehrt\n",destination1,name);
                ret=false;
            }
        }
        if(World.roomMap.get(destination2)==null){
            System.out.printf("Destination 2 %s von Exit %s existiert nicht\n",destination2,name);
            ret=false;
        }else{
            if(!World.roomMap.get(destination2).getExits().contains(name)){
                System.out.printf("Raum %s wird von Exit %s als Destination 2 referenziert aber nicht umgekehrt\n",destination2,name);
                ret=false;
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        String string="";
        string+=String.format("Exit %s\n",name);
        string+=String.format("Beschreibung: %s\n",description);
        string+=String.format("Destination1: %s\n",destination1);
        string+=String.format("Destination2: %s",destination2);
        return string;
    }

}
