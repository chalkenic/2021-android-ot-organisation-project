package com.example.otorganisationapp.fragments;

import android.app.AlertDialog;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.config.SessionsAdapter;
import com.example.otorganisationapp.domain.Session;
import com.example.otorganisationapp.repository.OTDatabase;

import java.util.List;

public class AllRecordsFragment extends Fragment {

    MainActivity activity;

    public SessionsAdapter adapter;

    Session sessionClicked;

    public AllRecordsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_records, container, false);

        final ListView sessionListView = (ListView)v.findViewById(R.id.all_sessions_list_view);

        OTDatabase db = OTDatabase.getDatabase(getContext());

        FragmentManager fm = getActivity().getSupportFragmentManager();

        activity = (MainActivity) getActivity();

        List<Session> allSessions = db.sessionDAO().getAllSessions();

        adapter = new SessionsAdapter(getContext(), R.layout.list_view_fragment_all_records, 2);

        sessionListView.setAdapter(adapter);

        adapter.addSessions(allSessions);

        sessionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sessionClicked = (Session) db.sessionDAO().getSessionById(((Session)sessionListView.getItemAtPosition(position)).sessionId);

                AppCompatTextView textView = new AppCompatTextView(getContext());

                // Use custom title for larger input.
                textView.setText("Record " + sessionClicked.getSessionId() + " notes for "
                        + db.patientDAO().getPatientById(sessionClicked.getPatientId()).getName());
                textView.setTextSize(22);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setPadding(2, 10, 2, 2);


                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setCustomTitle(textView);
                b.setMessage(sessionClicked.getSessionNotes());

                b.show();


            }
        });




        return v;

    }
}
