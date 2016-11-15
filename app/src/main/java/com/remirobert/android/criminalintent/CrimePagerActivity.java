package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by remirobert on 11/11/2016.
 */

public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimeList;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mCrimeList = CrimeLab.get().getCrimeList();

        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);
        mViewPager.setCurrentItem(0);

        FragmentManager fragmentManager = getFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeList.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });
    }
}
