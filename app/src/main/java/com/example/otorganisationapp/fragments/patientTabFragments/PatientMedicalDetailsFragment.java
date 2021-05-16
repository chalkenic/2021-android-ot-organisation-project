package com.example.otorganisationapp.fragments.patientTabFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.otorganisationapp.R;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.dialogs.PatientMedicalDetailsDialog;
import com.example.otorganisationapp.repository.OTDatabase;


public class PatientMedicalDetailsFragment extends Fragment {

    Patient patient;
    OTDatabase db;

    public PatientMedicalDetailsFragment(Patient patient) {
        this.patient = patient;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = OTDatabase.getDatabase(getContext());

        FragmentManager fm =getActivity().getSupportFragmentManager();
        View v;

        if ((patient.getPrimaryComplaint() == null ||
                patient.getEquipment() == null ||
                patient.getLivingSituation() == null)) {

            v = inflater.inflate(R.layout.fragment_inner_patient_tab_medical_no_information, container, false);

            ImageButton newMedInfoButton = (ImageButton)v.findViewById(R.id.patient_medical_tab_no_info_button);

            newMedInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PatientMedicalDetailsDialog dialog = new PatientMedicalDetailsDialog(patient);
                    dialog.show(fm, "new patient details");

                }
            });




        }
         else {
            v = inflater.inflate(R.layout.fragment_inner_patient_tab_medical, container, false);

            AppCompatTextView conditionTextView = (AppCompatTextView)v.findViewById(R.id.patient_tab_medical_condition_result);
            AppCompatTextView ableTextView = (AppCompatTextView)v.findViewById(R.id.patient_tab_medical_able_result);
            AppCompatTextView equipmentTextView = (AppCompatTextView)v.findViewById(R.id.patient_tab_medical_equipment_result);
            AppCompatTextView livingResultTextView = (AppCompatTextView)v.findViewById(R.id.patient_tab_medical_living_situation_result);

            conditionTextView.setText(db.conditionDAO().getConditionById(patient.getPrimaryComplaint()).getName());
            ableTextView.setText(patient.getIsIndependentlyMobile().toString());
            equipmentTextView.setText(patient.getEquipment());
            livingResultTextView.setText(patient.getLivingSituation());

        }




        return v;
    }
}
