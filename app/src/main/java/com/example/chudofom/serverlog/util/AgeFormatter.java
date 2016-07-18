package com.example.chudofom.serverlog.util;

/**
 * Created by Chudofom on 18.07.16.
 */
public class AgeFormatter {
    public static String milisToAge(Long ageInMillis) {
        Long MILLIS_IN_YEAR = 31536000000l;
        ageInMillis /= MILLIS_IN_YEAR;
        String age = String.valueOf(ageInMillis);
        if (age.matches(".*1")) age += " год";
        else if (age.matches(".*[2-4]")) age += " года";
        else if (age.matches(".*[5-9,0]")) age += " лет";
        return  age;
    }
}
