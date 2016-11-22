package com.remirobert.android.criminalintent;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by remirobert on 06/11/2016.
 */

public class CrimeHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTextView;
    private CheckBox mSolvedCheckBox;
    private TextView mDateTextView;

    public CrimeHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
        mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
        mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        mSolvedCheckBox.setClickable(false);
    }

    public void configure(Crime crime) {
        mTitleTextView.setText(crime.getTitle());
        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, crime.getDate()).toString();
        mDateTextView.setText(dateString);
        mSolvedCheckBox.setChecked(crime.isSolved());
    }
}
