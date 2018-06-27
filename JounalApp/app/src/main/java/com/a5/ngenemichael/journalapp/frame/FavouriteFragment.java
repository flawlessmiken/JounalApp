package com.a5.ngenemichael.journalapp.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5.ngenemichael.journalapp.R;

/**
 * Created by Flawless on 6/26/2018.
 */

public class FavouriteFragment extends Fragment {

    public FavouriteFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.fragment_fav,null);
       // return null;
    }
}
