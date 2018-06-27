package com.a5.ngenemichael.journalapp.frame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5.ngenemichael.journalapp.MainActivity;
import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.utils.JournalAdapter;

/**
 * Created by Flawless on 6/26/2018.
 */

public class ActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private  JournalAdapter mJournalAdapter;
    //private Context mContext = MainActivity.mainActivityContext();


    public ActivityFragment(){
       // this.mContext = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.content,null);



        return rootView;
    }
}
