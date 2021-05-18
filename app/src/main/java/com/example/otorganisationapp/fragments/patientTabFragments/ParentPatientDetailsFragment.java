package com.example.otorganisationapp.fragments.patientTabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.otorganisationapp.R;
import com.example.otorganisationapp.domain.Patient;
import com.example.otorganisationapp.repository.OTDatabase;
import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentPagerAdapter.*;

public class ParentPatientDetailsFragment extends Fragment {

    // Key & value for sourcing patient id for page.
    private static final String ARG_PATIENT_ID = "patientId";
    private static final String ARG_PATIENT_POSITION = "patientIdPosition";

    public static Patient patient;

    // Local patient id value.
    private String patientId;
    private  Integer patientPage;



    public ParentPatientDetailsFragment() {}

    public ParentPatientDetailsFragment(Integer id) {
        this.patientId = id.toString();
    }

    public ParentPatientDetailsFragment(Integer id, Integer tabPage) {
        this.patientId = id.toString();
        this.patientPage = tabPage;
    }

    /**
     * Sources information from AllPatients fragment & discerns specified patient from data.
     **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            patientId = getArguments().getString(ARG_PATIENT_ID);
            String patientIdPosition = getArguments().getString(ARG_PATIENT_POSITION);


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patienttabs, container, false);

        // Database access
        OTDatabase db = OTDatabase.getDatabase(getContext());




        // Assign patient object from data provided.
        patient = db.patientDAO().getPatientById(Integer.parseInt(patientId));


        // Build navigation tabs at top of screen.
        String[] patientTabList = this.getResources().getStringArray(R.array.patientTabs);

        // Assign tab layout and page viewer from Ids.
        TabLayout layout = (TabLayout)v.findViewById(R.id.tabLayout);

        ViewPager pager = (ViewPager)v.findViewById(R.id.patientPager);

        // Create tabs class, containing array of tabs to apply into TabLayout.
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this.getChildFragmentManager(), patientTabList);

        // Attach PagerView object to all tabs within adapter.
        pager.setAdapter(adapter);

        //Assign ViewPager layouts into each tab created.
        layout.setupWithViewPager(pager);

        return v;
    }

    // Append to textView the patient's details after view created.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String patientTabTitle = String.format(getResources().getString(R.string.patient_tab_title_concat), patient.getName());
        TextView tabTitle = view.findViewById(R.id.patient_details_title);
        tabTitle.setText(patientTabTitle);
    }

    /**
     * Object on static initialization grabs patient's name & position of patient within allPatients
     * ListView, and and stores values within object fields for later creation of tab data.
     * @param patientName - patient's name in database.
     * @return bundle containing patient id for fragment navigation.
     */
    public static ParentPatientDetailsFragment newPatientDetailsInstance(String patientName, String patientIdPosition) {
        ParentPatientDetailsFragment frag = new ParentPatientDetailsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PATIENT_ID, patientName);
        args.putString(ARG_PATIENT_POSITION, patientIdPosition);
        frag.setArguments(args);

        return frag;
    }

    /**
     * Class for handling the all fragments within the Tabs layout.
     */
    public static class TabsFragmentAdapter extends FragmentPagerAdapter {

        // Contains all tab titles.
        private final String[] tabTitles;

        TabsFragmentAdapter(FragmentManager manager, String[] tabTitles) {
            // Resume state on fragment re-open (if navigating to different app)
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.tabTitles = tabTitles;
        }

        // Swap between fragments.
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 1:
                    return new PatientMedicalDetailsFragment(ParentPatientDetailsFragment.patient);
                case 2:
                    return new PatientSessionsFragment(ParentPatientDetailsFragment.patient);
                default:
                    return new PatientBasicDetailsFragment(ParentPatientDetailsFragment.patient);

            }
        }
        // Source total tab count. static class requirement
        @Override
        public int getCount() {
            return this.tabTitles.length;
        }

        // Get title of page at specific position. static class requirement.
        @Override
        public CharSequence getPageTitle(int position) {
            return this.tabTitles[position];
        }
    }
}
