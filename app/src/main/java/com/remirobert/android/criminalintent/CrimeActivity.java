package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.remirobert.android.criminalintent.crime_id";
    public static final String RES_CRIME_INDEX = "com.remirobert.android.criminalintent.crime_index";

    public static Intent createIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
