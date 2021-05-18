package com.example.otorganisationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;

public class MainMenuFragment extends Fragment {

    // Field used for accessing change fragment method.
    MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
         mainActivity = (MainActivity) getActivity();


         // Clickable card views.
        CardView allRecordsCard = (CardView) v.findViewById(R.id.all_records_card);
        CardView newPatientCard = (CardView) v.findViewById(R.id.new_patient_card);
        CardView allPatientsCard = (CardView) v.findViewById(R.id.menu_all_patients_card);



        // Navigate to All records page via clicking card.
        allRecordsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new AllSessionsFragment(), "New Record");
            }
        });

        // Navigate to All page page via clicking card.
        allPatientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new AllPatientsFragment(), "New Record");
            }
        });

        // Navigate to create new patient page via clicking card.
        newPatientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new NewPatientFragment(), "New Record");
            }
        });



        return v;
    }
}
