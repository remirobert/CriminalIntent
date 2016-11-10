package com.remirobert.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by remirobert on 31/10/2016.
 */

public class Crime {
    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;

    Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
