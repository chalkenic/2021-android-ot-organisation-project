package com.example.otorganisationapp.services;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.otorganisationapp.domain.Session;

import java.util.List;

@Dao
public interface SessionDAO {

     /**
      * Create a session record in db.
      * @param sessions - Session(s) to be added.
      */
     @Insert
     void insertSession(Session... sessions);

    /**
     * Delete a patient's record in db.
     * @param sessions - Session(s) to be removed.
     */
    @Delete
    void deleteSession(Session... sessions);

    /**
     * Update a session record in db.
     * @param sessions - Session(s) to be updated.
     */
    @Update
    void updateSession(Session... sessions);

    /**
     * Find patient in db by name
     * @param id - patient id.
     * @return List of all sessions attached to patient.
     */
    @Query("SELECT * FROM Session WHERE patientId = :arg0")
    List<Session> getSessionsByPatientId(Integer arg0);

    /**
     * Get all saved patients in db.
     * @return - List of all patients;
     */
    @Query("SELECT * FROM Session")
    List<Session> getAllSessions();

    /**
     * Get patient by specific id in db
     * @param id - database id.
     * @return patient that matches id (unique).
     */
    @Query("SELECT * FROM Session WHERE sessionId = :arg0")
    Session getSessionById(Integer arg0);
}
