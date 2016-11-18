package com.remirobert.android.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by remirobert on 03/11/2016.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimeList;

    public List<Crime>getCrimeList() {
        return mCrimeList;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime crime : mCrimeList) {
            if (crime.getId().equals(uuid)) {
                return crime;
            }
        }
        return null;
    }

    public int getIndexCrime(Crime crime) {
        return mCrimeList.indexOf(crime);
    }

    public static CrimeLab get() {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab();
        }
        return sCrimeLab;
    }

    public void addCrime(Crime crime) {
        mCrimeList.add(crime);
    }

    public CrimeLab() {
        mCrimeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("#" + i);
            crime.setSolved(i % 2 == 0);
            mCrimeList.add(crime);
        }
    }
}
