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
    private static Integer patientPage;
//    TabLayout layout;
//    ViewPager pager;


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

        // Assign tab layout and page viewer from Ids.
        ViewPager pager = (ViewPager)v.findViewById(R.id.patientPager);
        TabLayout layout = (TabLayout)v.findViewById(R.id.tabLayout);


        // Assign patient object from data provided.
        patient = db.patientDAO().getPatientById(Integer.parseInt(patientId));


        // Build navigation tabs at top of screen.
        String[] patientTabList = this.getResources().getStringArray(R.array.patientTabs);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this.getChildFragmentManager(), patientTabList);

        // Attach pager to adaptor.
        pager.setAdapter(adapter);

        //Assign pager into tab layout.
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
     *
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
     * Class for handling the fragments within the Tabs.
     */
    public static class TabsFragmentAdapter extends FragmentPagerAdapter {

        // Contains all tab titles.
        private final String[] tabTitles;

        TabsFragmentAdapter(FragmentManager manager, String[] tabTitles) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.tabTitles = tabTitles;
        }

        // Swap between fragments.
        @Override
        public Fragment getItem(int position) {

            Log.d("fragment_no", "fragment" + position);

            switch (position){
                case 1:
                    return new PatientMedicalDetailsFragment(ParentPatientDetailsFragment.patient);
                case 2:
                    return new PatientSessionsFragment(ParentPatientDetailsFragment.patient);
                default:
                    return new PatientBasicDetailsFragment(ParentPatientDetailsFragment.patient);


            }
        }

        @Override
        public int getCount() {
            return this.tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.tabTitles[position];
        }
    }
}
