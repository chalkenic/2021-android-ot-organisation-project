package com.example.otorganisationapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Entity(foreignKeys = {
        @ForeignKey(entity=Condition.class, parentColumns="conditionId", childColumns="patientPrimaryComplaint"),
        @ForeignKey(entity=Condition.class, parentColumns="conditionId", childColumns="patientMedicalHistory"),
})
@Data
public class Patient {

    @ColumnInfo(name = "patientId")
    @PrimaryKey(autoGenerate = true)
    public Integer patientId;

    // Patient title (Mr/Mrs)
    @ColumnInfo(name = "patientTitle")
    @NonNull
    public String patientTitle;

    @ColumnInfo(name = "patientName")
    public String name;

    @ColumnInfo(name = "patientNHSNumber")
    public String NHSNumber;

    @ColumnInfo(name = "patientDOB")
    public Date dob;

    @ColumnInfo(name = "patientGender")
    public String gender;

    @ColumnInfo(name = "patientConsultantId")
    public Integer consultant;

    // Primary ailment affecting patient.
    @ColumnInfo(name = "patientPrimaryComplaint")
    public Integer patientPrimaryComplaint;

    // List of prior medical conditions.
    @ColumnInfo(name = "patientMedicalHistory")
    public ArrayList<Integer> patientMedicalHistory;

    // smart goals that patient works towards.
    @ColumnInfo(name = "patientGoals")
    public String goals;

    // Does patient use mobility equipment
    @ColumnInfo(name = "equipment")
    public String equipment;

    // Who they live with, social support (living conditions & day-to-day help).
    @ColumnInfo(name = "patientSocialHistory")
    public String socialHistory;

    // Can move without assistance?
    @ColumnInfo(name = "isIndependentlyMobile")
    public boolean isIndependentlyMobile;


    @ColumnInfo(name = "lastSession")
    public Date lastSession;


}
