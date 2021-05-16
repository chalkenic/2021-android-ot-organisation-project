package com.example.otorganisationapp.domain;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NonNull;


@Entity(foreignKeys = {
        @ForeignKey(entity=Condition.class, parentColumns="conditionId", childColumns="conditionId")
//        @ForeignKey(entity=Condition.class, parentColumns="conditionId", childColumns="patientMedicalHistory"),
}, indices = {@Index("conditionId")})

@Data
public class Patient {

    @ColumnInfo(name = "patientId")
    @PrimaryKey(autoGenerate = true)
    public Integer patientId;

    // Patient title (Mr/Mrs)
    @ColumnInfo(name = "patientTitle")
    @NonNull
    public String title;

    /**
     * Patient name.
     */
    @ColumnInfo(name = "patientName")
    @NonNull
    public String name;

    /**
     * Patient's 10 digit NHS number.
     */
    @ColumnInfo(name = "patientNHSNumber")
    @NonNull
    public String NHSNumber;

    /**
     * patient's date of birth (stored as Long when within database).
     */
    @ColumnInfo(name = "patientDOB")
    @NonNull
    public Date dob;

    /**
     * Patient's gender.
     */
    @ColumnInfo(name = "patientGender")
    @NonNull
    public String gender;

    /**
     * Patient's phone number (must be 5+ digits).
     */
    @ColumnInfo(name = "patientPhoneNo")
    @NonNull
    public String phoneNo;


    // Primary ailment affecting patient. Foreign key for Condition.
    @ColumnInfo(name = "conditionId")
    public Integer primaryComplaint;

    // Does patient use mobility equipment?
    @ColumnInfo(name = "equipment")
    public String equipment;

    // Who Patient lives with, social support (living conditions & day-to-day help).
    @ColumnInfo(name = "patientLivingSituation")
    public String livingSituation;

    // Can Patient move without assistance?
    @ColumnInfo(name = "isIndependentlyMobile")
    public Boolean isIndependentlyMobile;

    // Patient's saved image Uri path.
    @ColumnInfo(name = "patientImageUri")
    public String imageUri;

    public Patient(String title, String name, String gender, String nhsNo, Date dob, String phoneNo) {
        this.title = title;
        this.name = name;
        this.gender = gender;
        this.NHSNumber = nhsNo;
        this.dob = dob;
        this.phoneNo = phoneNo;
    }

    public Patient() {}


}
