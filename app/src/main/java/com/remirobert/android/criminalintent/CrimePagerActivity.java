package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_crime_pager);

        mCrimeList = CrimeLab.get().getCrimeList();

        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);

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
