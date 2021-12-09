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

    public boolean check(){
        boolean ret=true;
        if(World.roomMap.get(currentContainer)==null){
            System.out.printf("Raum %s von Item %s existiert nicht\n",currentContainer,name);
            ret=false;
        }else{
            if(!World.roomMap.get(currentContainer).getTools().contains(name)){
                System.out.printf("Raum %s wird von Item %s referenziert aber nicht umgekehrt\n",currentContainer,name);
                ret=false;
            }
        }
        return ret;
    }

    @Override
    public void investigate() {
        //TODO: comparen
    }
}
