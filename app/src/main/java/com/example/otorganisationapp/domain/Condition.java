package com.example.otorganisationapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class Condition {

    @ColumnInfo(name = "conditionId")
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "conditionName")
    public String name;



}
