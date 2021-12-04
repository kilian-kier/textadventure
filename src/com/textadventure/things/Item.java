package com.textadventure.things;

import com.textadventure.GameElement;
import com.textadventure.Story.World;
import com.textadventure.exeptions.ItemNotFoundException;
import com.textadventure.exeptions.ItemNotFoundInContainerException;

import java.io.Serializable;

abstract public class Item  extends GameElement implements Serializable {
    protected String currentContainer;


    public Item(String name, String description) {
        super(name);
        this.description = description;
    }


    public String getCurrentContainer() {
        return currentContainer;
    }

    public void setContainer(String container) {
        this.currentContainer = container;
    }
    public void changeContainer(String newContainerString) throws ItemNotFoundException , NullPointerException{
        Container newContainer=findItemContainer(newContainerString);
        Container oldContainer=findItemContainer(this.currentContainer);
        if(oldContainer.getTools().contains(this.name)){
            oldContainer.removeTool(this.name);
            newContainer.addTool(this.name);
        }else{
            throw new ItemNotFoundException(name);
        }
    }
    abstract Container findItemContainer(String container) throws ItemNotFoundException;

    @Override
    public void investigate() {
        //TODO: comparen
    }
}
