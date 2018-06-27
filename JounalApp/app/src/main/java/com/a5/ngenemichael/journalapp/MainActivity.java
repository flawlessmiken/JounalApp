package com.a5.ngenemichael.journalapp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.a5.ngenemichael.journalapp.data.JournalContract;
import com.a5.ngenemichael.journalapp.frame.ActivityFragment;
import com.a5.ngenemichael.journalapp.frame.CalanderFragment;
import com.a5.ngenemichael.journalapp.frame.FavouriteFragment;
import com.a5.ngenemichael.journalapp.frame.HomeFragment;
import com.a5.ngenemichael.journalapp.utils.JournalAdapter;
import com.a5.ngenemichael.journalapp.utils.ToolbarUtils;
import com.a5.ngenemichael.journalapp.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int JOURNAL_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;

    JournalAdapter mJournalAdapter;

    private static int [] icons = {R.drawable.ic_home,
            R.drawable.ic_list,
            R.drawable.ic_love,
            R.drawable.ic_calendar,
            };


   // private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(2.5f);

        //mViewPager = (ViewPager) findViewById(R.id.viewpager);
       //setUpViewPager(mViewPager,getSupportFragmentManager());

        //mTabLayout = (TabLayout)findViewById(R.id.tabs);
        //mTabLayout.setupWithViewPager(mViewPager);

        //mTabLayout = (TabLayout)findViewById(R.id.tabs);
        //mTabLayout.setupWithViewPager(mViewPager);

        //setUpIcons();

        loadDummyData();

        mJournalAdapter = new JournalAdapter(this);

       /* mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);


        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mJournalAdapter);

        */

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mJournalAdapter = new JournalAdapter(this);

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mJournalAdapter);


        getSupportLoaderManager().initLoader(JOURNAL_LOADER_ID, null, this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mCursor = null;
            @Override
            protected void onStartLoading() {

                super.onStartLoading();
                if (mCursor != null){
                    deliverResult(mCursor);
                }else {
                    forceLoad();
                }


            }

            @Override
            public Cursor loadInBackground() {

                try {
                    return getContentResolver().query(JournalContract.JournalEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            JournalContract.JournalEntry._ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }

              //return null;

            }

            @Override
            public void deliverResult(Cursor data) {
                super.deliverResult(data);
            }
        };


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mJournalAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mJournalAdapter.swapCursor(null);
    }

    private void loadDummyData(){
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(JournalContract.JournalEntry.DATE, "Friday, 28 Apr 2017");
        contentValues.put(JournalContract.JournalEntry.TITLE, "I have Big plan for the weekend");
        contentValues.put(JournalContract.JournalEntry.DETAIL, getString(R.string.dummy));
        contentValues.put(JournalContract.JournalEntry.MOOD,1);
        Uri uri = getContentResolver().insert(JournalContract.JournalEntry.CONTENT_URI, contentValues);
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        getSupportLoaderManager().restartLoader(JOURNAL_LOADER_ID, null, this);
    }


   /* public  void setUpViewPager(ViewPager viewPager, FragmentManager fm) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm);
        adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new ActivityFragment(),"Activity");
        adapter.addFragment(new FavouriteFragment(),"Favourite");
        adapter.addFragment(new CalanderFragment(),"Calender");
        //mViewPager.setAdapter(adapter);

    }






    public void setUpIcons(){
        for (int i = 0; i <icons.length ; i++) {
            mTabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }*/
}
