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

public abstract class OTDatabase extends RoomDatabase {

    private static OTDatabase OTDATABASE;

    public abstract PatientDAO patientDAO();

    public abstract ConditionDAO conditionDAO();

    public abstract SessionDAO sessionDAO();

    public static synchronized OTDatabase getDatabase(Context context) {
        if (OTDATABASE == null) {
            OTDATABASE = Room.databaseBuilder(context,
                    OTDatabase.class, "OT_database")
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return OTDATABASE;
    }
}

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDBTask(OTDATABASE).execute();
//        }
//    };
//

//    private static class PopulateDBTask extends AsyncTask<Void, Void, Void> {
//        private PatientDAO patientDAO;
//        private SessionDAO sessionDAO;
//        private ConditionDAO conditionDAO;
//
//        public PopulateDBTask(OTDatabase db) {
//            this.patientDAO = db.patientDAO();
//            this.conditionDAO = db.conditionDAO();
//            this.sessionDAO = db.sessionDAO();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            conditionDAO.insertCondition(StaticMethods.createDummyConditionData());
//            patientDAO.insertPatient(StaticMethods.createDummyPatientData());
//
//        }
//    }

//    public static OTDatabase getDatabase(Context context) {
//
//        if (OTDATABASE == null) {
//            Log.d("add_data", "database is currently null!");
//
//
//            otDatabase = Room.databaseBuilder(context, OTDatabase.class, "OT")
//                    .allowMainThreadQueries()
//                    .build();
//
//            otDatabase.populateTables();
//
//        }
////
//
//        StaticMethods.insertDatabaseData(otDatabase);
//        return otDatabase;
//    }

//    private void populateTables() {
//        if (patientDAO().getAllPatients().size() == 0) {
//            StaticMethods.insertDatabaseData(otDatabase);
//        }
//    }
//}
