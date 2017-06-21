package com.example.photosort.mydb;

/**
 * Created by mikez on 5/24/2017.
 */

public interface IDataStore {
    void saveState(String state);
    String getState();
}
