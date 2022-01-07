package com.textadventure.Event;

public abstract class ElementDiff extends Diff{
    public ElementDiff(String name) {
        super(name);
    }

    public void setDescription(String description){
        this.description=description;
    }
    protected String description;
    protected Boolean interactable=null;
    public String getDescription(){
        return description;
    }
}
