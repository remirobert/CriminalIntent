package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.functions.Consumer;

/**
 * Created by remirobert on 05/11/2016.
 */

public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CRIME) {
            int crimeIndex = data.getIntExtra(CrimeActivity.RES_CRIME_INDEX, 0);
            mCrimeAdapter.notifyItemChanged(crimeIndex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCrimeAdapter = new CrimeAdapter(CrimeLab.get().getCrimeList(), getActivity());
        mRecyclerView.setAdapter(mCrimeAdapter);

        mCrimeAdapter.getCrimeObservable().subscribe(new Consumer<Crime>() {
            @Override
            public void accept(Crime crime) throws Exception {
                onClick(crime);
            }
        });

        return view;
    }

    private void onClick(Crime crime) {
        Intent intent = CrimeActivity.createIntent(getActivity(), crime.getId());
        startActivityForResult(intent, REQUEST_CRIME);
    }
}
