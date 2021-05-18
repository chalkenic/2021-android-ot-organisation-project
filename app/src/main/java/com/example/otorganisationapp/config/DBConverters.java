package com.example.otorganisationapp.config;


import androidx.room.TypeConverter;
import java.util.Date;

public class DBConverters {

    /**
     * Code adapted from Zhar: Room Using Date field.
     * available at: https://stackoverflow.com/questions/50313525/room-using-date-field
     */
    // Convert Long value within Room db to Date object upon value exiting database.
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    //Convert Date object to Long value when entered into database.
    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}