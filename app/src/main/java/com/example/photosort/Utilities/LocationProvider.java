package com.example.photosort.Utilities;

import android.location.Location;

import java.util.Random;

/**
 * Created by mikez on 6/4/2017.
 */

public class LocationProvider {
    Location location;

    public LocationProvider(){
        location = new Location("");
        Random random = new Random();

        double randLong = random.nextFloat() * 360.0 - 180;
        String str = String.format("%.5f", randLong);
        randLong = Double.parseDouble(str);
        location.setLongitude(randLong);

        double randLat = random.nextFloat() * 180.0 - 90;
        str = String.format("%.5f", randLat);
        randLat = Double.parseDouble(str);
        location.setLatitude(randLat);
    }

    public String getLongitude(){
        StringBuilder builder = new StringBuilder();
        String longitudeDegrees = Location.convert(Math.abs(location.getLongitude()), location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("/1,");
        builder.append(longitudeSplit[1]);
        builder.append("/1,");
        String seconds = longitudeSplit[2];
        if(seconds.contains(".")){
            seconds = seconds.split("\\.")[0];
        }
        builder.append(seconds);
        builder.append("/1");
        return builder.toString();

    }

    public String getLatitude(){
        StringBuilder builder = new StringBuilder();
        String latitudedegrees = Location.convert(Math.abs(location.getLatitude()), location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudedegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("/1,");
        builder.append(latitudeSplit[1]);
        builder.append("/1,");
        String seconds = latitudeSplit[2];
        if(seconds.contains(".")){
            seconds = seconds.split("\\.")[0];
        }
        builder.append(seconds);
        builder.append("/1");
        return builder.toString();

    }

    public String getNS(){
        return location.getLatitude()>0?"N":"S";
    }

    public String getEW(){
        return location.getLongitude()>0?"E":"W";
    }


    public static Location getLocation(String latitude, String latRef, String longitude, String lonRef){
        double Latitude;
        double Longitude;
        Location loc = new Location("");

        if(latRef.equals("N")){
            Latitude = convertToDegree(latitude);
        }
        else{
            Latitude = 0 - convertToDegree(latitude);
        }

        if(lonRef.equals("E")){
            Longitude = convertToDegree(longitude);
        }
        else{
            Longitude = 0 - convertToDegree(longitude);
        }

        loc.setLatitude(Latitude);
        loc.setLongitude(Longitude);

        return loc;
    }

    private static Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    };



}
