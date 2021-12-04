package com.textadventure.Event;

import com.textadventure.exeptions.GameElementNotFoundException;
import com.textadventure.exeptions.TypeDoesNotExistException;
import com.textadventure.exeptions.TypeNotValidException;

import java.util.Collection;

public class NPCDiff extends Diff{
    public NPCDiff(String name) {
        super(name);
    }

    @Override
    void applyDiffToWorld() throws GameElementNotFoundException {

    }

    public void setDialog(Collection<String[]> dialog){
        differences.put("dialog",dialog);
    }
    public Collection<String[]> getDialog(){
        return (Collection<String[]>)differences.get("dialog");
    }
}
