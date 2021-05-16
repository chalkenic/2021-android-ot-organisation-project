package com.example.otorganisationapp.domain;

import android.text.format.Time;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Entity(foreignKeys = {
        @ForeignKey(entity=Patient.class, parentColumns="patientId", childColumns="patientId"),
}, indices = {@Index("patientId")})
@Data
public class Session {

    /**
     * Primary key id.
     */
    @ColumnInfo(name = "sessionId")
    @PrimaryKey(autoGenerate = true)
    public Integer sessionId;

    /**
     * Type of session (F2F, phone, email etc).
     */
    @ColumnInfo(name = "sessionType")
    public String sessionType;

    /**
     * Patient involved within Session object. Foreign Key of Patient.
     */
    @NonNull
    @ColumnInfo(name = "patientId")
    public Integer patientId;

    /**
     * date of session. Automatically saves as today's date.
     */
    @NonNull
    @ColumnInfo(name = "sessionDate")
    public Date dateOfSession;

    /**
     * All notes to be made by Consultant from patient interaction.
     */
    @NonNull
    @ColumnInfo(name = "sessionNotes")
    public String sessionNotes;
    //
    public Session(Date dateOfSession, String sessionType, String sessionNotes, Integer patientId) {

        this.dateOfSession = dateOfSession;
        this.sessionType = sessionType;
        this.sessionNotes = sessionNotes;
        this.patientId = patientId;


    }
}
