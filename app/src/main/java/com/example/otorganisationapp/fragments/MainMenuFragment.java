package com.example.otorganisationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.Fragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;

public class MainMenuFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();


//        CardView cardview =(CardView) inflater.from(getContext()).inflate(R.id.new_patient_card, null);
        CardView newRecordCard = (CardView) v.findViewById(R.id.new_record_card);
        CardView newPatientCard = (CardView) v.findViewById(R.id.new_patient_card);



        newRecordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeCurrentFragment(new NewRecordFragment(), "New Record");
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
