package com.example.otorganisationapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.otorganisationapp.databinding.ActivityMainBinding;
import com.example.otorganisationapp.fragments.MainMenuFragment;
import com.example.otorganisationapp.fragments.NewPatientFragment;
import com.example.otorganisationapp.fragments.AllRecordsFragment;
import com.example.otorganisationapp.fragments.AllPatientsFragment;
import com.example.otorganisationapp.repository.OTDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Fragments can go back.
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        // toolbar containing Home & navigation to all pages.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bottom navigation bar.
        BottomNavigationView bottomView = findViewById(R.id.nav_bar_bottom);

        // On click redirection on bottom nav bar.
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {

                    case R.id.bottom_nav_all_patients:
                        changeCurrentFragment(new AllPatientsFragment(), "All patients");
                        return true;

                    case R.id.bottom_nav_all_records:
                        changeCurrentFragment(new AllRecordsFragment(), "All records");
                        return true;

                    case R.id.bottom_nav_home:
                        changeCurrentFragment(new MainMenuFragment(), "Main Menu");
                }
                return MainActivity.super.onContextItemSelected(item);
            }
        });

        OTDatabase db = OTDatabase.getDatabase(this);

        // Adds dummy data into database on first creation.
        if (db.patientDAO().getAllPatients().size() == 0) {
            StaticMethods.insertDatabaseData(db);
        }


        // If first opening of app, navigate to main menu fragment.
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MainMenuFragment())
                    .commit();
        }
    }

    // Create options menu at top of screen.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    // Navigate to application areas on option menu choice click.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.options_menu_home:

                changeCurrentFragment(new MainMenuFragment(), "Main Menu");
                return true;

            case R.id.options_menu_crPatient:

                changeCurrentFragment(new NewPatientFragment(), "New Patient");
                return true;

            case R.id.options_menu_allPatients:
                changeCurrentFragment(new AllPatientsFragment(), "All patients");

                return true;

            case R.id.options_menu_allRecords:
                changeCurrentFragment(new AllRecordsFragment(), "All patients");

                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    /**
     *
     * @param f - fragment to navigate to.
     * @param tag - reference prior fragment so clicking back on System UI can return to original fragment.
     */
    public void changeCurrentFragment(Fragment f, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, f)
                .addToBackStack(tag)
                .commit();
    }
//
//    public OTDatabase getDb() {
//        return db;
//    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        theInstance = this;
//    }




//    public void showDatePickerDialog(View v) {
//        DialogFragment newDateFragment = new DatePickerFragment();
//        newDateFragment.show(getSupportFragmentManager(), "datePicker");
//
//    }


//    @Override
//    public void onClick(View v) {
//
//        switch(v.getId()) {
//
//            case R.id.patient_form_choose_date: {
//                datePicker.show(getSupportFragmentManager(), "datePicker");
//                break;
//            }
//        }
//    }


}