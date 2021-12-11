package com.textadventure.characters;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dialog {
    private ArrayList<String[]> dialog=new ArrayList<>();

    public ArrayList<String[]> getDialog() {
        return dialog;
    }

    public void setDialog(ArrayList<String[]> dialog) {
        if(dialog==null){
            dialog.clear();
        }else {
            this.dialog = dialog;
        }
    }

    public static void edit(){

    }
    @Override
    public String toString(){
        String string="";
            for (String[] i: dialog) {
                try {
                    string += String.format("Frage: %s; Antwort: %s; Event: %s\n", i[0], i[1], i[2]);
                }catch(IndexOutOfBoundsException e){
                    System.err.println("Ungültiger Dialog\n");
                    e.printStackTrace();
                }
            }
        return string;
    }
}
