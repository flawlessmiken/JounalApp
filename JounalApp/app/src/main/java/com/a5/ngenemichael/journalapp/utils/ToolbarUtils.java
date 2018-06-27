package com.a5.ngenemichael.journalapp.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.frame.ActivityFragment;
import com.a5.ngenemichael.journalapp.frame.CalanderFragment;
import com.a5.ngenemichael.journalapp.frame.FavouriteFragment;
import com.a5.ngenemichael.journalapp.frame.HomeFragment;


public class ToolbarUtils {


/*
    private static int [] icons = {R.drawable.ic_list,
            R.drawable.ic_love,
            R.drawable.ic_calendar,
            R.drawable.ic_home};

    public  static void setUpIcons(TabLayout mTabLayout){
        for (int i = 0; i <icons.length ; i++) {
            mTabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
|*/

   /* public static ViewPager setUpViewPager(ViewPager viewPager, FragmentManager fm, Context context) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm);
        adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new ActivityFragment(context),"Activity");
        adapter.addFragment(new FavouriteFragment(),"Favourite");
        adapter.addFragment(new CalanderFragment(),"Calender");
        viewPager.setAdapter(adapter);
        return viewPager;






        mRecyclerView = (RecyclerView)rootView. findViewById(R.id.recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mJournalAdapter = new JournalAdapter(this);

        //mRecyclerView.setHasFixedSize(true);
       mRecyclerView.setLayoutManager(manager);
       mRecyclerView.setAdapter(mJournalAdapter);
    }
    */


}
