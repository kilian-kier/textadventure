package com.textadventure.input;

import java.util.LinkedList;

public class Input {
    /**
     * Method creates List of words from String
     * @param string String of which a List is created
     * @return List of Words
     */
    public static LinkedList<String> StringToList(String string){
        LinkedList<String> list = new LinkedList<>();
        char c;
        int wordStart=0;
        int wordEnd=0;
        boolean wordFinished=true;
        for(int i=0;i<string.length();i++){
            c = string.charAt(i);
            switch(c){
                case ' ':
                    if(!wordFinished){
                        wordFinished=true;
                        list.add(string.substring(wordStart,wordEnd+1));
                        wordStart=i;
                        wordEnd=i;
                    }
                    break;
                default:
                    if(wordFinished){
                        wordFinished=false;
                        wordStart=i;
                        wordEnd=i;
                    }else{
                        wordEnd++;
                }
            }
        }
        if(wordStart!=wordEnd){
            list.add(string.substring(wordStart,wordEnd+1));
        }
        return list;
    }

    public static void edit(String string){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("/usr/bin/vlc");
            process.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
