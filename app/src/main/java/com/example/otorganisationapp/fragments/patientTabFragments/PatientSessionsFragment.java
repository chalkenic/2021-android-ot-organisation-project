package com.example.otorganisationapp.fragments.patientTabFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.config.SessionsAdapter;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.domain.Session;
import com.example.otorganisationapp.fragments.dialogs.PatientNewSessionDialog;
import com.example.otorganisationapp.repository.OTDatabase;

import java.util.List;

public class PatientSessionsFragment extends Fragment {

    // Field for allowing database access.
    public MainActivity  otActivity;

    // List row adapter.
    public SessionsAdapter adapter;

    Patient patient;

    Session sessionClicked;

    public PatientSessionsFragment(Patient patient) {
        this.patient = patient;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inner_patient_tab_sessions, container, false);

        final ListView patientSessionsView = (ListView) v.findViewById(R.id.patient_tab_sessions_list_view);

        AppCompatButton newSessionButton = (AppCompatButton) v.findViewById(R.id.patient_tab_sessions_new_session_button);

        OTDatabase db = OTDatabase.getDatabase(getContext());
        FragmentManager fm =getActivity().getSupportFragmentManager();

        // get Activity for db access.
        otActivity = (MainActivity) getActivity();

        List<Session> patientSessions = db.sessionDAO().getSessionsByPatientId(patient.getPatientId());

        // Source sessions adaptor for application of patient's session into list row.
        adapter = new SessionsAdapter(getContext(), R.layout.list_view_fragment_inner_patient_session, 1);

        //Apply row styling & data to ListView.
        patientSessionsView.setAdapter(adapter);

        // Implement data to be inserted within rows into adapter.
        adapter.addSessions(patientSessions);

        // Open Alert dialog of information specific to session on row item click.
        patientSessionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Source object at position of row..
                sessionClicked = (Session) db.sessionDAO().getSessionById(((Session)patientSessionsView.getItemAtPosition(position)).sessionId);

                AppCompatTextView textView = new AppCompatTextView(getContext());

                String rowTitle = getString(
                        R.string.patient_tab_sessions_list_data,
                        sessionClicked.getSessionId().toString(),
                        patient.getName());

                // Use custom title for larger input.
                textView.setText(rowTitle);
                textView.setTextSize(22);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setPadding(2, 10, 2, 2);


                // Show session notes in dialog.
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setCustomTitle(textView);

                // Apply session notes & show dialog.
                b.setMessage(sessionClicked.getSessionNotes());
                b.show();

            }
        });

        // Create new session within dialog.
        newSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open dialog object that handles input of new data relating to patient.
                final PatientNewSessionDialog dialog = new PatientNewSessionDialog(patient);

                dialog.show(fm, "New Patient Session");

            }
        });
        return v;
    }
}
