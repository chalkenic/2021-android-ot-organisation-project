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
import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.repository.OTDatabase;

public class MainMenuFragment extends Fragment {

    MainActivity mainActivity;

    OTDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
         mainActivity = (MainActivity) getActivity();


         // Clickable card views.
        CardView allRecordsCard = (CardView) v.findViewById(R.id.all_records_card);
        CardView newPatientCard = (CardView) v.findViewById(R.id.new_patient_card);
        CardView allPatientsCard = (CardView) v.findViewById(R.id.menu_all_patients_card);



        allRecordsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new AllRecordsFragment(), "New Record");
            }
        });

        allPatientsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new AllPatientsFragment(), "New Record");
            }
        });

        newPatientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new NewPatientFragment(), "New Record");
            }
        });



        return v;
    }




//    public void onClickNewPatient(View v) {
//
//        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();


//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.changeCurrentFragment(new NewPatientFragment(), "New Record");

//    }

//    @Override
//    public void onClick(View v) {
//        MainActivity mainActivity = (MainActivity) getActivity();
//        switch(v.getId()) {
//            case R.id.new_patient:
//                Log.d("clicked patient", "test1");
//                Toast.makeText(getActivity(), "patient button clicked", Toast.LENGTH_SHORT).show();
////                mainActivity.changeCurrentFragment(new NewPatientFragment(), "New Patient");
//            case R.id.new_record:
//                Log.d("clicked_record", "test1");
//                Toast.makeText(getActivity(), "record button clicked", Toast.LENGTH_SHORT).show();
////                mainActivity.changeCurrentFragment(new NewRecordFragment(), "New Patient");
//        }
//    }


//    public void newPatientNavigation() {
//
//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.changeCurrentFragment(new NewPatientFragment(), "New patient");
//    }
//
//    public void newRecordNavigation() {
//
////        Toast.makeText(getAppicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//
////        MainActivity mainActivity = (MainActivity) getActivity();
////        mainActivity.changeCurrentFragment(new NewRecordFragment(), "New Record");
//    }
}
