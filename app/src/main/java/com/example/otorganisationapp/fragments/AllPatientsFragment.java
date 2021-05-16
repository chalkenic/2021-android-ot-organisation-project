package com.example.otorganisationapp.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.config.AllPatientsAdapter;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.patientTabFragments.ParentPatientDetailsFragment;
import com.example.otorganisationapp.repository.OTDatabase;

import java.util.List;

public class AllPatientsFragment extends Fragment {

    // Field for allowing database access.
    public MainActivity otActivity;

    // List row adapter.
    public AllPatientsAdapter adapter;

    Patient patientIdClicked;
    OTDatabase db;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_patients, container, false);

        final ListView allPatientsView = (ListView) v.findViewById(R.id.all_patients_list_view);

        db = OTDatabase.getDatabase(getContext());

        // Get application context.
        Context context = getContext();

        // Confirm context exists within fragment before fragment logic begins.
        if (context == null) {return v;};

        otActivity = (MainActivity) getActivity();


        List<Patient> allPatients = db.patientDAO().getAllPatients();

        adapter = new AllPatientsAdapter(getContext(), R.layout.list_view_fragment_all_patients);

        allPatientsView.setAdapter(adapter);

        adapter.addPatients(allPatients);

        allPatientsView.setOnItemClickListener((parent, view, position, id) -> {

            patientIdClicked = (Patient) db.patientDAO().getPatientById(((Patient)allPatientsView.getItemAtPosition(position)).patientId);

            otActivity.changeCurrentFragment
                    (ParentPatientDetailsFragment.newPatientDetailsInstance(
                            String.valueOf(patientIdClicked.getPatientId()),
                            String.valueOf(allPatientsView.getItemAtPosition(position))),
                            "List item");

        });


        return v;

    }
}
