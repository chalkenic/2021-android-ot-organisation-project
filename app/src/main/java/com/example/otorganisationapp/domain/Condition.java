package com.example.otorganisationapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * Class that handles all Patient medical conditions.
 */
@Entity
@Data
public class Condition {

    @ColumnInfo(name = "conditionId")
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    /**
     * Condition name.
     */

    @ColumnInfo(name = "conditionName")
    public String name;

    public Condition(String name) {
        this.name = name;
    }


}
