package com.example.otorganisationapp.services;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.otorganisationapp.domain.Patient;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PatientDAO {
    /**
     * Create a patient record in db.
     * @param patients - Patient(s) to be added.
     */
    @Insert
    void insertPatient(Patient... patients);


    /**
     * Delete a patient's record in db.
     * @param patients - Patient(s) to be removed.
     */
    @Delete
    void deletePatient(Patient... patients);

    /**
     * Update a patient's record in db.
     * @param patients - Patient(s) to be updated.
     */
    @Update
    void updatePatient(Patient... patients);

    /**
     * Find patient in db by name
     * @param name - user entry.
     * @return List of all patients matching name
     */
    @Query("SELECT * FROM Patient WHERE patientName = :arg0")
    List<Patient> getPatientsByName(String arg0);

    /**
     * Get all saved patients in db.
     * @return - List of all patients;
     */
    @Query("SELECT * FROM Patient")
    List<Patient> getAllPatients();

    /**
     * Get patient by specific id in db
     * @param id - database id.
     * @return patient that matches id (unique).
     */
    @Query("SELECT * FROM Patient WHERE patientId = :arg0")
    Patient getPatientById(Integer arg0);



}
