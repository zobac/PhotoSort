package com.example.photosort.Filters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mikez on 5/17/2017.
 */

public class DateFilter {

    public static boolean inRange(int year, int month, int months, Date date){
        if(date == null){
            return false;
        }

        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date);
        Calendar cal2 = new GregorianCalendar();
        cal2.set(year, month, 0);
        int diff = differenceInMonths(cal2, cal1);
        return(diff <= months);
    }

    private static Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }
        int m1 = beginningDate.get(Calendar.YEAR) * 12 + beginningDate.get(Calendar.MONTH);
        int m2 = endingDate.get(Calendar.YEAR) * 12 + endingDate.get(Calendar.MONTH);
        return m2 - m1;
    }
}
