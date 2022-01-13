package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.story.World;
import com.textadventure.exeptions.ItemNotFoundException;

import java.io.Serializable;

/**
 * Ein item enthält den Container in dem es sich befindet.
 */
abstract public class Item  extends GameElement implements Serializable {
    private static final long serialVersionUID = 6229304006313634505L;
    protected String currentContainer;


    public Item(String name, String description) {
        super(name);
        this.description = description;
    }


    public String getCurrentContainer() {
        return currentContainer;
    }

    public void setContainer(String container) {
        if(container!=null){
            this.currentContainer = container.toLowerCase();
        }else{
            this.currentContainer=null;
        }
    }

    /**
     * Ändert den Container in dem sich ein Item befindet.
     * @param newContainerString neuer Container
     * @throws ItemNotFoundException Wenn neuer Container nicht existiert
     * @throws NullPointerException Wenn ein ungültiger Parameter übergeben wird.
     */
    public void changeContainer(String newContainerString) throws ItemNotFoundException , NullPointerException{
        Container newContainer=findItemContainer(newContainerString);
        if(this.currentContainer!=null) {
            Container oldContainer = findItemContainer(this.currentContainer);
            if (oldContainer.getTools().contains(this.name)) {
                oldContainer.removeTool(this.name);
                newContainer.addTool(this.name);
            } else {
                throw new ItemNotFoundException(name);
            }
        }else{
            newContainer.addTool(this.name);
        }
        this.currentContainer=newContainerString;
    }

    /**
     * Sucht ein Container in der container-Map oder falls nach "player" gesucht wird das Inventar des Spielers
     * @param container Name des Containers
     * @return Objekt des Containers
     * @throws ItemNotFoundException wenn der Container nicht gefunden wurde
     */
    Container findItemContainer(String container) throws ItemNotFoundException {
        Container newContainer;
        if (container.equals("player")) {
            newContainer = World.player.getToolsContainer();
        } else {
            newContainer = World.containerMap.get(container);
            if (newContainer == null) {
                throw new ItemNotFoundException(container);
            }
        }
        return newContainer;
    }

    public abstract boolean check(boolean fix);

    @Override
    public void investigate() {

    }
}
