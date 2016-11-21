package com.remirobert.android.criminalintent;

import android.app.Fragment;

/**
 * Created by remirobert on 21/11/2016.
 */

public class CrimeCreateActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeCreateFragment();
    }
}
