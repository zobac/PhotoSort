package com.example.photosort.Containers;

import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mikez on 5/8/2017.
 */

public class ImageArray implements Serializable{
    ArrayList<File> images = new ArrayList<>();
    private static int currentIndex = 0;
    private File filePath;

    public ImageArray(){

    }

    public File setFilePath(File fp){
        filePath = fp;
        return filePath;
    }

    public File getFilePath(){
        return filePath;
    }

    public void add(File file){
        images.add(file);
    }


    public File getCurrentId(){
        return images.get(currentIndex);
    }


    public File increment(){
        currentIndex ++;
        if (currentIndex >= images.size()){
            currentIndex = 0;
        }
        return images.get(currentIndex);
    }

    public File decrement(){
        currentIndex --;
        if (currentIndex < 0){
            currentIndex = images.size()-1;
        }
        return images.get(currentIndex);
    }

    public File[] asArray(){
        return (File[])images.toArray(new File[0]);
    }


    public File getItemAt(int index){
        return images.get(index);
    }


    public int getSize(){
        return images.size();
    }

    public void clear(){
        images.clear();
    }

    public void setLastAsCurrent(){
        currentIndex = images.size()-1;
    }
}
