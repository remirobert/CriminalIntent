package com.remirobert.android.criminalintent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by remirobert on 06/11/2016.
 */

public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

    private List<Crime> mCrimeList;
    private Context mContext;
    private PublishSubject<Crime> mCrimeObservable = PublishSubject.create();

    public CrimeAdapter(List<Crime> crimeList, Context context) {
        mCrimeList = crimeList;
        mContext = context;
    }

    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
        return new CrimeHolder(view);
    }

    @Override
    public void onBindViewHolder(CrimeHolder holder, int position) {
        final Crime crime = mCrimeList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCrimeObservable.onNext(crime);
            }
        });
        holder.configure(crime);
    }

    public PublishSubject<Crime> getCrimeObservable() {
        return mCrimeObservable;
    }

    @Override
    public int getItemCount() {
        return mCrimeList.size();
    }
}
