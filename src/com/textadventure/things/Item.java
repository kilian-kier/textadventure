package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

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
        this.currentContainer = container.toLowerCase();
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
    abstract Container findItemContainer(String container) throws ItemNotFoundException;

    public abstract boolean check();

    @Override
    public void investigate() {
        //TODO: comparen
    }
}
