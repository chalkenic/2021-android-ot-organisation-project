package com.example.otorganisationapp.fragments.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.example.otorganisationapp.domain.Session;
import com.example.otorganisationapp.fragments.patientTabFragments.ParentPatientDetailsFragment;
import com.example.otorganisationapp.repository.OTDatabase;

import java.time.LocalDateTime;
import java.util.Date;

public class PatientNewSessionDialog extends DialogFragment {

    MainActivity otActivity;
    Patient patient;
    Session session;
    OTDatabase db;

    public PatientNewSessionDialog(Patient p) { this.patient = p; }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Store array of different session types - used within alert dialog as items.
        CharSequence[] typeList = this.getResources().getStringArray(R.array.sessionTypes);

        // Provide database access.
        db = OTDatabase.getDatabase(getContext());

        // Get main activity for access to fragment change method.
        otActivity = (MainActivity)getActivity();


        View v = inflater.inflate(R.layout.dialog_session_tab_new_session, container, false);

        TextView sessionType = (TextView) v.findViewById(R.id.dialog_new_session_type_choice);
        EditText sessionNotes = (EditText) v.findViewById(R.id.dialog_new_session_notes);

        AppCompatButton submitButton = (AppCompatButton) v.findViewById(R.id.dialog_new_session_submit);
        AppCompatButton cancelButton = (AppCompatButton) v.findViewById(R.id.dialog_new_session_cancel);

        // Set listener on TextView that opens up Alert Dialog.
        sessionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String choice = "";

                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Choose session type");
                b.setItems(typeList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Type chosen appended into parent dialog.
                        if (typeList[which].equals("Phone Call")) {
                            dialog.dismiss();
                            sessionType.setText(("Phone Call"));
                            //Background colour of TextView changes depending on type choice.
                            sessionType.setBackgroundColor(getResources().getColor(R.color.sessionType1));

                        } else if (typeList[which].equals("Face-to-Face")) {
                            dialog.dismiss();
                            sessionType.setText(("Face-to-Face"));
                            sessionType.setBackgroundColor(getResources().getColor(R.color.sessionType2));

                        } else if (typeList[which].equals("Email")) {
                            dialog.dismiss();
                            sessionType.setText(("Email"));
                            sessionType.setBackgroundColor(getResources().getColor(R.color.sessionType3));

                        } else if (typeList[which].equals("SMS")) {
                            dialog.dismiss();
                            sessionType.setText(("SMS"));
                            sessionType.setBackgroundColor(getResources().getColor(R.color.sessionType4));

                        } else if (typeList[which].equals("Letter")) {
                            dialog.dismiss();
                            sessionType.setText(("Letter"));
                            sessionType.setBackgroundColor(getResources().getColor(R.color.sessionType5));

                        }
                    }
                });
                b.show();

            }
        });

        // Add session into database using type & notes data from dialog.
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sessionType.getText().toString().equals("TAP TO CHOOSE")) {

                    Date now = new Date();
                    session = new Session(
                            now,
                            sessionType.getText().toString(),
                            sessionNotes.getText().toString(),
                            patient.getPatientId());

                    db.sessionDAO().insertSession(session);

                    Toast.makeText(getContext(),
                            "Session record for " + patient.getName() + "created!",
                            Toast.LENGTH_SHORT).show();

                    // Close dialog on submission.
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Please choose a session type.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(v1 -> dismiss());


        return v;

    }

    /**
     * // return to patient's detail fragment with updated phone information.
     * @param dialog - this.
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);


        otActivity.changeCurrentFragment(new ParentPatientDetailsFragment(
                patient.getPatientId(), 2), "return to Patient sessions");

    }
}
