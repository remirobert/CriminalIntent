package com.remirobert.android.criminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by remirobert on 31/10/2016.
 */

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 0;

    private EditText mEditText;
    private Crime mCrime;
    private Button mDateButton;


    public static Fragment newInstance(String uuid) {
        Bundle args = new Bundle();
        args.putString(ARG_CRIME_ID, uuid);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    private void setDateFormat(Date date) {
        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, date).toString();
        mDateButton.setText(dateString);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(ARG_CRIME_ID);
        mCrime = CrimeLab.get().getCrime(id);

        Intent intent = new Intent();
        intent.putExtra(CrimeActivity.RES_CRIME_INDEX, CrimeLab.get().getIndexCrime(mCrime));
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            setDateFormat(date);
            Realm.getDefaultInstance().beginTransaction();
            mCrime.setDate(date);
            Realm.getDefaultInstance().commitTransaction();
        }
        else if (requestCode == REQUEST_CONTACT) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.get().deleteCrime(mCrime);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }
        String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);
        return report;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        setHasOptionsMenu(true);
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        setDateFormat(mCrime.getDate());

        Button suspectButton = (Button) view.findViewById(R.id.crime_suspect);
        suspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

        Button reportButton = (Button) view.findViewById(R.id.crime_report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                String chooserTitle = getString(R.string.crime_report_chooser_title);
                startActivity(Intent.createChooser(intent, chooserTitle));
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        CheckBox solvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        solvedCheckBox.setChecked(mCrime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Realm.getDefaultInstance().beginTransaction();
                mCrime.setSolved(b);
                Realm.getDefaultInstance().commitTransaction();
            }
        });

        mEditText = (EditText) view.findViewById(R.id.crime_title);
        mEditText.setText(mCrime.getTitle());
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Realm.getDefaultInstance().beginTransaction();
                mCrime.setTitle(charSequence.toString());
                Realm.getDefaultInstance().commitTransaction();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }
}
