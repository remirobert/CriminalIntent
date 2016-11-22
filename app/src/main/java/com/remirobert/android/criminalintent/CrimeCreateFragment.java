package com.remirobert.android.criminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by remirobert on 21/11/2016.
 */

public class CrimeCreateFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private EditText mTitleEditText;
    private Button mDateButton;
    private Date mSelectedDate;
    private Button mSaveButton;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            String dateFormat = "EEE, MMM dd";
            String dateString = DateFormat.format(dateFormat, date).toString();
            mDateButton.setText(dateString);
            mSelectedDate = date;
            setState((mTitleEditText.getText().toString().length() > 0 && mSelectedDate != null));
        }
    }

    private void setState(boolean enabled) {
        mSaveButton.setAlpha((enabled) ? 1 : 0.3f);
        mSaveButton.setEnabled(enabled);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_create, container, false);

        mTitleEditText = (EditText) view.findViewById(R.id.crime_title);
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mSaveButton = (Button) view.findViewById(R.id.crime_save);

        setState(false);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crime crime = new Crime();
                crime.setDate(mSelectedDate);
                crime.setTitle(mTitleEditText.getText().toString());
                CrimeLab.get().addCrime(crime);
                getActivity().finish();
            }
        });

        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setState((charSequence.length() > 0 && mSelectedDate != null));
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date());
                datePickerFragment.setTargetFragment(CrimeCreateFragment.this, REQUEST_DATE);
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });
        return view;
    }
}
