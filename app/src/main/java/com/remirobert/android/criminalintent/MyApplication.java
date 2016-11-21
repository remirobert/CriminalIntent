package com.remirobert.android.criminalintent;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by remirobert on 21/11/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
