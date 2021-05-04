//package com.example.otorganisationapp.domain;
//
//import android.text.format.Time;
//
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import lombok.Data;
//
//@Entity(foreignKeys = {
//    @ForeignKey(entity=Patient.class, parentColumns="patientId", childColumns="patientId"),
//    @ForeignKey(entity=Therapist.class, parentColumns="patientId", childColumns="therapistId"),
//})
//@Data
//public class Record {
//
//    @PrimaryKey(autoGenerate = true)
//    public Integer recordId;
//
//    String type;
//
//    ArrayList<String> categories = new ArrayList<>();
//    List<String> categoryList = Arrays.asList("Treatment", "Conversation", "Email correspondence", "Phone Call", "Assessment");
//
//
//    public Integer patientId;
//    public Integer therapistId;
//
//    public Date dateOfRecord;
//
//    public Time timeOfRecord;
//
//    public String recordNotes;
//
//    public Record() {
//
//        categories.addAll(categoryList);
//
//    }
//}
