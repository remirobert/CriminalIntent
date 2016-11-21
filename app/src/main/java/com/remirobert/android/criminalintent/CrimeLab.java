package com.remirobert.android.criminalintent;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by remirobert on 03/11/2016.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Realm mRealm;
    private List<Crime> mCrimeList;

    public List<Crime>getCrimeList() {
        return mCrimeList;
    }

    public Crime getCrime(String uuid) {
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

    public void deleteCrime(Crime crime) {
        mCrimeList.remove(crime);
        mRealm.beginTransaction();
        crime.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public void addCrime(Crime crime) {
        mCrimeList.add(crime);
        mRealm.beginTransaction();
        mRealm.copyToRealm(crime);
        mRealm.commitTransaction();
    }

    public CrimeLab() {
        mRealm = Realm.getDefaultInstance();
        RealmQuery<Crime> query = mRealm.where(Crime.class);
        RealmResults<Crime> realmResults = query.findAll();
        realmResults = realmResults.sort("mDate", Sort.DESCENDING);
        mCrimeList = new ArrayList<>(realmResults);
    }
}
