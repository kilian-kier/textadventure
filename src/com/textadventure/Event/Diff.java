package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.interfaces.Checkable;
import com.textadventure.interfaces.Editable;
import com.textadventure.interfaces.Loadable;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Diff implements Serializable, Editable , Loadable, Checkable {
    private static final long serialVersionUID = 6806889103294573466L;
    protected String name;

    public Diff(String name)  {
        this.name=name;
    }



    public abstract void applyDiffToWorld() throws GameElementNotFoundException;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
