package com.example.photosort.mydb;

/**
 * Created by mikez on 5/24/2017.
 */

public class DataStorageImp implements IDataStore {
    private String state = null;
    public void saveState(String state){this.state = state;}
    public String getState(){return state;}
}
