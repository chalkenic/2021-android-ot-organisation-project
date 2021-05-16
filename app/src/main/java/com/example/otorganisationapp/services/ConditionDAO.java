package com.example.otorganisationapp.services;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.otorganisationapp.domain.Condition;
import com.example.otorganisationapp.domain.Patient;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ConditionDAO {
    /**
     * Create a patient record in db.
     * @param conditions - Patient(s) to be added.
     */
    @Insert
    void insertCondition(Condition... conditions);

    /**
     * Delete a patient's record in db.
     * @param conditions - Patient(s) to be removed.
     */
    @Delete
    void deleteCondition(Condition... conditions);

    /**
     * Update a patient's record in db.
     * @param conditions - Patient(s) to be updated.
     */
    @Update
    void updateCondition(Condition... conditions);

    /**
     * Find patient in db by name
     * @param name - user entry.
     * @return List of all patients matching name
     */
    @Query("SELECT * FROM Condition WHERE conditionName = :arg0")
    List<Condition> getConditionsByName(String arg0);

    /**
     * Get all saved patients in db.
     * @return - List of all patients;
     */
    @Query("SELECT * FROM Condition")
    List<Condition> getAllConditions();

    /**
     * Get patient by specific id in db
     * @param id - database id.
     * @return patient that matches id (unique).
     */
    @Query("SELECT * FROM Condition WHERE conditionId = :arg0")
    Condition getConditionById(Integer arg0);



}
