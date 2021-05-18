package com.example.otorganisationapp.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.config.DBConverters;
import com.example.otorganisationapp.domain.Condition;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.domain.Session;
import com.example.otorganisationapp.services.ConditionDAO;
import com.example.otorganisationapp.services.PatientDAO;
import com.example.otorganisationapp.services.SessionDAO;

@Database(entities = {Patient.class, Condition.class, Session.class}, version = 1, exportSchema = false)
@TypeConverters(DBConverters.class)

/**
 * Class for handling database creation and access.
 */
public abstract class OTDatabase extends RoomDatabase {

    private static OTDatabase OTDATABASE;

    /**
     * Class holds methods to access patient table rows/columns.
     * @return abstract patient table method class.
     */
    public abstract PatientDAO patientDAO();

    /**
     * Class holds methods to access Medical Condition table rows/columns.
     * @return abstract patient table method class.
     */
    public abstract ConditionDAO conditionDAO();

    /**
     * Class holds methods to access Patient's session table rows/columns.
     * @return abstract patient table method class.
     */
    public abstract SessionDAO sessionDAO();

    /**
     * Method sources static RoomDatabase object; creates database if field data doesn't exist.
     * @param context - application context.
     * @return OT database.
     */
    public static synchronized OTDatabase getDatabase(Context context) {
        if (OTDATABASE == null) {
            OTDATABASE = Room.databaseBuilder(context,
                    OTDatabase.class, "OT_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return OTDATABASE;
    }
}
