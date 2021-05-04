package com.example.otorganisationapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Consultant {

    @ColumnInfo(name = "consultantId")
    @PrimaryKey(autoGenerate = true)
    public Integer consultantId;

    @ColumnInfo(name = "consultantName")
    String name;

    @ColumnInfo(name = "consultantEmail")
    String email;

    @ColumnInfo(name = "consultantPhoneNo")
    Integer phoneNo;

    @ColumnInfo(name = "consultantHealthBoard")
    String healthBoard;
}
