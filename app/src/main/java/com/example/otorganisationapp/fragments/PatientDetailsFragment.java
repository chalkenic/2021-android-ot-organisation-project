package com.example.otorganisationapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.otorganisationapp.R;
import com.example.otorganisationapp.fragments.patientTabFragments.PatientBasicDetailsFragment;
import com.example.otorganisationapp.fragments.patientTabFragments.PatientMedicalDetailsFragment;
import com.example.otorganisationapp.fragments.patientTabFragments.PatientSessionsDetailsFragment;
import com.google.android.material.tabs.TabLayout;

public class PatientDetailsFragment extends Fragment {

    public PatientDetailsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("DETAILS_1", "i reached PatientDetailsFragment!");

        View v = inflater.inflate(R.layout.fragment_patienttabs, container, false);

        String[] patientTabList = this.getResources().getStringArray(R.array.patientTabs);

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this.getChildFragmentManager(), patientTabList);

        ViewPager vp = (ViewPager) v.findViewById(R.id.patientPager);
        vp.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vp);

        return v;
    }


    public class TabsFragmentAdapter extends FragmentPagerAdapter {

        private String[] tabTitles;

        TabsFragmentAdapter(FragmentManager manager, String[] tabTitles) {
            super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.tabTitles = tabTitles;
        }

        @Override
        public Fragment getItem(int position) {


            if(position == 0) {
                position = 1;
            }

            switch (position){
                case 1:
                    return new PatientBasicDetailsFragment();
                case 2:
                    Log.d("hello", "medical Details was called?");
                    return new PatientMedicalDetailsFragment();

                case 3:
                    Log.d("hello", "session Details was called?");
                    return new PatientSessionsDetailsFragment();

                default:
                    Log.d("getitem", "nothing was called?");
                    return null;
            }

//
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
