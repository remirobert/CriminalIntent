package com.remirobert.android.criminalintent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
//        if (requestCode == REQUEST_CRIME) {
//            int crimeIndex = data.getIntExtra(CrimeActivity.RES_CRIME_INDEX, 0);
//            mCrimeAdapter.notifyItemChanged(crimeIndex);
//        }
    }

    private void updateSubtitle() {
        int crimeCount = CrimeLab.get().getCrimeList().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Intent intent = new Intent(getActivity(), CrimeCreateActivity.class);
                startActivity(intent);
//                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId(), true);
/*
                Intent intent = CrimeFragment.newInstance(crime.getId(), true);
                                startActivity(intent);
*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCrimeAdapter.notifyDataSetChanged();
        updateSubtitle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        setHasOptionsMenu(true);
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
        Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
        startActivity(intent);
    }
}
