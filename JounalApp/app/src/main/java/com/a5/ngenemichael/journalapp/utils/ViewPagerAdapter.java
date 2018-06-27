package com.a5.ngenemichael.journalapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String>mStringList = new ArrayList<>();
    private List<RecyclerView> mRecyclerViews = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {

        if(mFragmentList.size()!= 0)
        return mFragmentList.size();
        else return 0;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return mStringList.get(position);
        return null;
    }

    public void addFragment(Fragment fragment, String  title){
        mFragmentList.add(fragment);
        mStringList.add(title);
    }
}
