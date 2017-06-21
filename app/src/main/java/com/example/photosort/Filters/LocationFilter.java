package com.example.photosort.Filters;

import android.location.Location;

/**
 * Created by mikez on 5/17/2017.
 */

public class LocationFilter {

    public static boolean inRadius(Double latitude, Double longitude, int radius, Location location){
        if(location == null){
            return false;
        }
        Location tempLocation = new Location("");
        tempLocation.setLongitude(longitude);
        tempLocation.setLongitude(latitude);

        Float distanceKM = tempLocation.distanceTo(location)/1000;
        return (distanceKM <= radius*100);
    }
}
