package com.example.otorganisationapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.otorganisationapp.databinding.ActivityMainBinding;
import com.example.otorganisationapp.fragments.MainMenuFragment;
import com.example.otorganisationapp.fragments.PatientDetailsFragment;
import com.example.otorganisationapp.fragments.NewPatientFragment;
import com.example.otorganisationapp.fragments.NewRecordFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MainMenuFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.home:

                changeCurrentFragment(new MainMenuFragment(), "Main Menu");
                return true;

            case R.id.crRecord:

                changeCurrentFragment(new NewRecordFragment(), "New Record");
                return true;

            case R.id.crPatient:

                changeCurrentFragment(new NewPatientFragment(), "New Patient");
                return true;

            case R.id.testPatient:
                changeCurrentFragment(new PatientDetailsFragment(), "check patient");

                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public void changeCurrentFragment(Fragment f, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, f)
                .addToBackStack(tag)
                .commit();
    }
}