package com.remirobert.android.criminalintent;

import android.app.Fragment;

/**
 * Created by remirobert on 05/11/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
