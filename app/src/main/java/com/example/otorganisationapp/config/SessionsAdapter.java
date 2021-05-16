package com.example.otorganisationapp.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.otorganisationapp.MainActivity;
import com.example.otorganisationapp.R;
import com.example.otorganisationapp.StaticMethods;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.domain.Session;
import com.example.otorganisationapp.repository.OTDatabase;

import java.util.ArrayList;
import java.util.List;

public class SessionsAdapter extends ArrayAdapter<Session> {
    MainActivity mainActivity;

    private final List<Session> sessions = new ArrayList<Session>();

    // Determines if sessions to return are for 1 patient or all.
    private final int sessionRequestSource;

    // Stores resource ID & applies to row for different layouts.
    private final int sessionResourceId;

    /**
     * in-line class that holds all UI views that will have data implemented into.
     */
    static class ViewHolder {
        AppCompatImageView sessionImage;
        AppCompatTextView sessionPatient;
        AppCompatTextView sessionId;
        AppCompatTextView lineBreak1;
        AppCompatTextView sessionType;
        AppCompatTextView sessionDate;

    }

    /**
     * Initialize adapter for adding Sessions to appropriate ListView.
     * @param context - application context.
     * @param resource - view Layout id.
     * @param source - source of dataSet that will express values to show in list.
     */
    public SessionsAdapter(@NonNull Context context, int resource, int source) {
        super(context, resource);
        this.sessionResourceId = resource;
        this.sessionRequestSource = source;
    }
    /**
     * add one session object to adapter.
     * @param object - the Session.
     */
    @Override
    public void add(@Nullable Session object) {
        sessions.add(object);
        super.add(object);
    }
    /**
     * Add multiple session objects to adapter.
     * @param collection - x amount of Session objects.
     */
    public void addSessions(List<Session> collection) { sessions.addAll(collection); }
    /**
     * Get Amount of rows in adapter.
     * @return total rows.
     */
    @Override
    public int getCount() { return this.sessions.size(); }

    /**
     * get specific Session item in row list.
     * @param position position of row clicked.
     * @return get Session at selected position.
     */
    @Nullable
    @Override
    public Session getItem(int position) { return this.sessions.get(position); }

    // Provide data within UI view.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // Grab specific row.
        View row = convertView;

        LayoutInflater inflater;

        // Object to hold all UI views from layout.
        ViewHolder holder;

        OTDatabase db = OTDatabase.getDatabase(getContext());

            // If individual patient, return specific data into rows.
            if (sessionRequestSource == 1) {

                // check if row is empty before adding information.
                if (row == null) {
                    inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    //Attach row view layout into main layout CardView.
                    row = inflater.inflate(sessionResourceId, parent, false);

                    holder = new ViewHolder();

                    // Attach views to holder fields.
                    holder.sessionImage = (AppCompatImageView) row.findViewById(R.id.selected_patient_session_image);
                    holder.sessionId = (AppCompatTextView) row.findViewById(R.id.selected_patient_session_id);;
                    holder.lineBreak1 = (AppCompatTextView) row.findViewById(R.id.selected_patient_session_line_break_1);;
                    holder.sessionType = (AppCompatTextView) row.findViewById(R.id.selected_patient_session_type);
                    holder.sessionDate = (AppCompatTextView) row.findViewById(R.id.selected_patient_session_date);;
                    row.setTag(holder);

                } else {
                    // apply existing Tag & data into ViewHOlder object.
                    holder = (ViewHolder)row.getTag();
                }

                // Grab session.
                Session s = getItem(position);

                // apply session values into object.
                holder.sessionImage.setImageResource(R.drawable.icon_patient_session_list_item);
                holder.sessionId.setText("Record id: " + s.getSessionId());
                holder.sessionType.setText("Type: " + s.getSessionType());
                holder.sessionDate.setText(StaticMethods.getFormattedDate(s.getDateOfSession()));


            // Return a larger row layout if using all records.
            } else {

                if (row == null) {

                    inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    row = inflater.inflate(sessionResourceId, parent, false);

                    holder = new ViewHolder();

                    holder.sessionPatient = (AppCompatTextView) row.findViewById(R.id.selected_record_patient_name);
                    holder.sessionImage = (AppCompatImageView) row.findViewById(R.id.selected_record_image);
                    holder.sessionId = (AppCompatTextView) row.findViewById(R.id.selected_record_id);;
                    holder.lineBreak1 = (AppCompatTextView) row.findViewById(R.id.selected_record_line_break_1);;
                    holder.sessionType = (AppCompatTextView) row.findViewById(R.id.selected_record_type);
                    holder.sessionDate = (AppCompatTextView) row.findViewById(R.id.selected_record_date);
                    row.setTag(holder);

                } else {
                    holder = (ViewHolder)row.getTag();
                }

                Session s = getItem(position);

                Patient sessionPatient = db.patientDAO().getPatientById(s.getPatientId());

                holder.sessionPatient.setText("Patient: " + sessionPatient.getName());
                holder.sessionImage.setImageResource(R.drawable.icon_list_record);
                holder.sessionId.setText("Record id: " + s.getSessionId());
                holder.sessionType.setText("Type: " + s.getSessionType());
                holder.sessionDate.setText(StaticMethods.getFormattedDate(s.getDateOfSession()));

            }

            // get existing row data via tag if exists when sourced originally.
//        } else {
//            holder = (IndividualSessionViewHolder)row.getTag();
//        }

        return row;

    }
}
