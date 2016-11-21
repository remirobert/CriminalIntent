package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by remirobert on 11/11/2016.
 */

public class CrimePagerActivity extends AppCompatActivity {

    public static final String EXTRA_CRIME_ID = "com.remirobert.android.criminalintent.crime_id_pager";
    private ViewPager mViewPager;
    private List<Crime> mCrimeList;

    public static Intent newIntent(Context context, String idCrime) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(CrimePagerActivity.EXTRA_CRIME_ID, idCrime);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String idCrime = getIntent().getStringExtra(EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimeList.size(); i++) {
            if (mCrimeList.get(i).getId().equals(idCrime)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
