package com.example.otorganisationapp.fragments.patientTabFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.otorganisationapp.R;

public class PatientSessionsDetailsFragment extends Fragment {

    public PatientSessionsDetailsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inner_patienttabs, container, false);

        return v;
    }
}
