package com.example.otorganisationapp.fragments.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.fragments.patientTabFragments.ParentPatientDetailsFragment;
import com.example.otorganisationapp.repository.OTDatabase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PatientBasicDetailsDialog extends DialogFragment {

    MainActivity otActivity;

    Patient patient;
    OTDatabase db;

    public PatientBasicDetailsDialog(Patient p) {
        this.patient = p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_inner_patient_tab_basic_new_phone_no, container, false );

        // Provide fragment-change abilities within fragment.
        otActivity = (MainActivity) getActivity();

        // Provide database access.
        db = OTDatabase.getDatabase(getContext());

        getDialog().setTitle("Enter new phone number");

        // Source textBox & submission button from dialog.
        EditText phoneNoBox = (EditText) v.findViewById(R.id.dialog_new_phone_no_value);
        AppCompatButton submitButton = (AppCompatButton) v.findViewById(R.id.dialog_new_phone_no_button);
        AppCompatButton cancelButton = (AppCompatButton) v.findViewById(R.id.dialog_new_phone_no_cancel);

        //Change patient's phone number within database if entry > 4 digits.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneNoBox.getText().toString().length() > 4) {

                    // Change phone number of patient object & update within database.
                    patient.setPhoneNo(phoneNoBox.getText().toString());

                    db.patientDAO().updatePatient(patient);

                    // Close dialog on submission.
                    dismiss();
                }
                else {
                    Toast.makeText(getContext(), "Number must be longer than 5 digits!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButton.setOnClickListener(v1 -> dismiss());

        return v;
    }

    // Processes following closure of dialog.
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // return to patient's detail fragment with updated phone information.
        otActivity.changeCurrentFragment(new ParentPatientDetailsFragment(patient.getPatientId()), "return to Patient details");

    }
}
