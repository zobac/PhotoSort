package com.example.photosort.Utilities;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by mikez on 6/4/2017.
 */

public class DateProvider {

    public DateProvider(){

    }

    public static String getDateString(){
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar randomDate = (Calendar) now.clone();

        int minYear = 2000;
        int minMonth = 1;
        int minDay = 1;
        int hour = 0;
        int minute = 0;
        int second = 0;

        min.set(minYear, minMonth-1, minDay, hour, minute, second);
        int numberOfDaysToAdd = (int) (Math.random() * (daysBetween(min, now) + 1));
        randomDate.add(Calendar.DAY_OF_YEAR, -numberOfDaysToAdd);

        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
        return dateFormat.format(randomDate.getTime());
    }

    private static int daysBetween(Calendar from, Calendar to) {
        Calendar date = (Calendar) from.clone();
        int daysBetween = 0;
        while (date.before(to)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        System.out.println(daysBetween);
        return daysBetween;
    }

    public static Date getDate(String dateString){
        Date date = new Date();
        String pattern = "yyyy:MM:dd hh:mm:ss";
        try {
            date = new SimpleDateFormat(pattern).parse(dateString);
            return date;
        }catch (ParseException e){
            e.getStackTrace();
        }
        return date;
    }
}
