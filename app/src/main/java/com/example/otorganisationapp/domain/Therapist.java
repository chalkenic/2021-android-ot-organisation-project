//package com.example.otorganisationapp.domain;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//import lombok.Data;
//
//@Entity(foreignKeys = @ForeignKey(entity=Team.class, parentColumns="teamId", childColumns="therapistTeamId"))
//@Data
//
//public class Therapist {
//
//    @PrimaryKey(autoGenerate = true)
//    public Integer therapistId;
//
//    @ColumnInfo()
//    private String name;
//
//    @ColumnInfo()
//    private String title;
//
//    @ColumnInfo()
//    private String email;
//
//    @ColumnInfo()
//    private String password;
//
//    @ColumnInfo(name = "therapistTeamId")
//    private Integer therapistTeamId;
//
//    // Responsibility level. Dictates pay.
//    @ColumnInfo(name = "therapistBand")
//    private Integer band;
//}
