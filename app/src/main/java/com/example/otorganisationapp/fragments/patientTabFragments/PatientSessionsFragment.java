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

        adapter = new SessionsAdapter(getContext(), R.layout.list_view_fragment_inner_patient_session, 1);

        patientSessionsView.setAdapter(adapter);

        adapter.addSessions(patientSessions);

        patientSessionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                sessionClicked = (Session) db.sessionDAO().getSessionById(((Session)patientSessionsView.getItemAtPosition(position)).sessionId);

                AppCompatTextView textView = new AppCompatTextView(getContext());

                // Use custom title for larger input.
                textView.setText("Record " + sessionClicked.getSessionId() + " notes for " + patient.getName());
                textView.setTextSize(22);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setPadding(2, 10, 2, 2);


                // Show session notes in dialog.
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setCustomTitle(textView);
                b.setMessage(sessionClicked.getSessionNotes());

                b.show();

            }
        });

        // Create new session within dialog.
        newSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PatientNewSessionDialog dialog = new PatientNewSessionDialog(patient);

                dialog.show(fm, "New Patient Session");

            }
        });
        return v;
    }
}
