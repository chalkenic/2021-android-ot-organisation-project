package com.example.otorganisationapp.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.otorganisationapp.R;
import com.example.otorganisationapp.domain.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Java class adapted from javapapers.com - Android ListView Custom Layout Tutorial.
 * Available at: https://javapapers.com/android/android-listview-custom-layout-tutorial/
 */
public class AllPatientsAdapter extends ArrayAdapter<Patient> {
    private final List<Patient> patients = new ArrayList<Patient>();

    /**
     * in-line class that holds all UI views that will have data implemented into.
     */
    static class PatientViewHolder {
        AppCompatImageView patientImage;
        TextView patientName;
        TextView patientNHSNumber;

    }

    /**
     * Initialize adapter for adding Sessions to appropriate ListView.
     * @param c - application context.
     * @param resourceId - layout id.
     */
    public AllPatientsAdapter(Context c, int resourceId) {
        super(c, resourceId);
    }

    @Override
    public void add(@Nullable Patient object) {
        patients.add(object);
        super.add(object);
    }

    /**
     * Add multiple Patient objects to adapter.
     * @param collection - x amount of patient objects.
     */
    public void addPatients(List<Patient> collection) { patients.addAll(collection); }

        @Override public int getCount() { return this.patients.size(); }

    /**
     * get specific patient item in row list.
     * @param position position of row clicked.
     * @return patient at selected position.
     */
    @Nullable
    @Override
    public Patient getItem(int position) { return this.patients.get(position); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;

        // Initialize object to hold patient data for rows.
        PatientViewHolder holder;

        //Grab layout data for patient data insertion.
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_view_fragment_all_patients, parent, false);

            holder = new PatientViewHolder();
            holder.patientImage = (AppCompatImageView) row.findViewById(R.id.patient_list_image);
            holder.patientName = (TextView) row.findViewById(R.id.patient_list_name);
            holder.patientNHSNumber = (TextView) row.findViewById(R.id.patient_list_nhs_number);
            row.setTag(holder);
        } else {
            holder = (PatientViewHolder)row.getTag();

            }
        // Grab Patient object at position.
        Patient p = getItem(position);
        holder.patientImage.setImageResource(R.drawable.icon_list_patient);

        // Ensure patient name does not overlap with other fields in row.
        if (p.getName().length() < 15) {
            holder.patientName.setText(p.getName());
        } else {
            int nameMaxLimit = 15;

            String patientNameEdited = p.getName().substring(0, nameMaxLimit);

            holder.patientName.setText(patientNameEdited + "...");
            }
        holder.patientNHSNumber.setText("NHS number: " + p.getNHSNumber().toString());
        return row;
        }

    }

