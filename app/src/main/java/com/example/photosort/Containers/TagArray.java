package com.example.photosort.Containers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mikez on 5/9/2017.
 */

public class TagArray implements Serializable{
    public static ArrayList<String> tags = new ArrayList<>();
    private static int currentIndex = 0;

    public TagArray(){

    }

    public void add(String name){
        tags.add(name);
    }

    public String getCurrentTag(){
        return tags.get(currentIndex);
    }

    public String increment(){
        currentIndex ++;
        if (currentIndex >= tags.size()){
            currentIndex = 0;
        }
        return tags.get(currentIndex);
    }

    public String decrement(){
        currentIndex --;
        if (currentIndex < 0){
            currentIndex = tags.size()-1;
        }
        return tags.get(currentIndex);
    }

    public String[] asArray(){
        return (String[])tags.toArray(new String[0]);
    }
}
