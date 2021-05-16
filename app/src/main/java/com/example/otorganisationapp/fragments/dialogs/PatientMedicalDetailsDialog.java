package com.example.otorganisationapp.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.domain.Condition;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.patientTabFragments.ParentPatientDetailsFragment;
import com.example.otorganisationapp.repository.OTDatabase;

import java.util.ArrayList;

import java.util.List;

public class PatientMedicalDetailsDialog extends DialogFragment {

    MainActivity otActivity;
    Patient patient;
    Condition condition;

    OTDatabase db;
    ArrayAdapter<String> conditionAdapter;

    public PatientMedicalDetailsDialog(Patient p) {
        this.patient = p;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = OTDatabase.getDatabase(getContext());

        // Get main activity for access to fragment change method.
        otActivity = (MainActivity)getActivity();

        // Source all conditions saved in db.
        List<Condition> conditionList = db.conditionDAO().getAllConditions();
        ArrayList<String> conditionNames = new ArrayList<>();

        for (Condition condition : conditionList) {
            conditionNames.add(condition.getName());
        }

        View v = inflater.inflate(R.layout.dialog_medical_tab_new_details, container, false);

        // Fields refer to adding/assigning conditions to patient.
        SearchView conditionSearchView = (SearchView) v.findViewById(R.id.condition_search_view);
        TextView conditionSearchViewResult = (TextView) v.findViewById(R.id.condition_search_view_result);
        ImageView isConditionInDbIndicator = (ImageView) v.findViewById(R.id.condition_search_check_in);

        // Regular fields to confirm patient's current health state.
        CheckBox isIndependent = (CheckBox) v.findViewById(R.id.checkbox_medical_record_independent);
        EditText equipment = (EditText) v.findViewById(R.id.medical_record_equipment);
        EditText livingSituation = (EditText) v.findViewById(R.id.medical_record_living_situation);


        AppCompatButton submitButton = (AppCompatButton) v.findViewById(R.id.button_medical_record_submit);
        conditionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, conditionNames);

        // Condition occurs if a medical complaint has not been registered yet.
        if (patient.getPrimaryComplaint() == null) {
            conditionSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String entry) {

                    // Ensure no condition duplicates.
                    if (conditionNames.contains(entry)) {

                        // Grab condition in db that matches entry.
                        List<Condition> existingCondition = db.conditionDAO().getConditionsByName(entry);

                        // Assign condition to patient & update database.
                        patient.setPrimaryComplaint(existingCondition.get(0).getId());
                        db.patientDAO().updatePatient(patient);
                        Toast.makeText(getContext(),
                                "Condition " + existingCondition.get(0).getName() + " applied to patient.",
                                Toast.LENGTH_SHORT).show();
                        String patientTabTitle = String.format(getResources().getString(R.string.patient_medical_result_concat),
                                db.conditionDAO().getConditionById(patient.getPrimaryComplaint()).getName());

                        // Apply patient's condition to TextView.
                        conditionSearchViewResult.setText(patientTabTitle);


                    } else {
                        condition = new Condition(entry);
                        db.conditionDAO().insertCondition(condition);

                        //Update condition in class to reflect new database entry (i.e. grab id).
                        condition.setId(db.conditionDAO().getConditionsByName(condition.getName()).get(0).getId());

                        // Assign condition to patient & update database.
                        patient.setPrimaryComplaint(condition.getId());
                        db.patientDAO().updatePatient(patient);

                        Toast.makeText(getContext(),
                                "Condition " + condition.getName() + " created for patient.",
                                Toast.LENGTH_SHORT).show();

                        String patientTabTitle = String.format(getResources().getString(R.string.patient_medical_result_concat),
                                db.conditionDAO().getConditionById(patient.getPrimaryComplaint()).getName());
                        // Apply patient's condition to TextView.
                        conditionSearchViewResult.setText(patientTabTitle);
                    }

                    // Remove search field & "if exists" color indicator, display patient's condition only.
                    conditionSearchView.setVisibility(View.GONE);
                    isConditionInDbIndicator.setVisibility(View.GONE);
                    conditionSearchViewResult.setVisibility(View.VISIBLE);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    // Change colour of indicator depending on whether entry at current time matches
                    // a Condition in database.
                    if (conditionNames.contains(newText)) {
                        isConditionInDbIndicator.setImageResource(R.drawable.circle_green);
                    } else {
                        isConditionInDbIndicator.setImageResource(R.drawable.circle_red);
                    }

                    return false;
                }
            });
            // Condition if patient already has assigned complaint. set view to as if patient already entered
            // data.
        } else {

            String patientTabTitle = String.format(getResources().getString(R.string.patient_medical_result_concat),
                    db.conditionDAO().getConditionById(patient.getPrimaryComplaint()).getName());
            conditionSearchViewResult.setText(patientTabTitle);

            conditionSearchView.setVisibility(View.GONE);
            isConditionInDbIndicator.setVisibility(View.GONE);
            conditionSearchViewResult.setVisibility(View.VISIBLE);

        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Assigns mobility to patient.
                patient.setIsIndependentlyMobile(isIndependent.isChecked());

                // Assign patient's equipment.
                patient.setEquipment(equipment.getText().toString());

                // Assign patient's living situation.
                patient.setLivingSituation(livingSituation.getText().toString());

                // Update patient with new information.
                db.patientDAO().updatePatient(patient);

                Toast.makeText(getContext(), patient.getName() + "'s medical details have been filed.", Toast.LENGTH_SHORT).show();

                dismiss();



            }
        });



        return v;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // return to patient's detail fragment with updated phone information.
        otActivity.changeCurrentFragment(new ParentPatientDetailsFragment(patient.getPatientId(), 1), "return to Patient details");

    }


}
