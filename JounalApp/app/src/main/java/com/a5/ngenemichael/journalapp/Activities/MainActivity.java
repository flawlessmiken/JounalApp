package com.a5.ngenemichael.journalapp.Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.data.JournalContract;
import com.a5.ngenemichael.journalapp.utils.FirebaseUtils;
import com.a5.ngenemichael.journalapp.data.JournalAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        JournalAdapter.JournalAdapterClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int JOURNAL_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseUtils mFirebaseUtils;

    JournalAdapter mJournalAdapter;



    // private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(2.5f);

        /// inspect and correct
        firebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUtils = new FirebaseUtils(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);
        TextView email = (TextView) headerView.findViewById(R.id.nav_email);
        //ImageView image = (ImageView)headerView.findViewById(R.id.nav_image);
        Bundle bundle = FirebaseUtils.getUserDetails(this);
        if (bundle != null) {
            name.setText(bundle.getString(JournalContract.USER_NAME));
            email.setText(bundle.getString(JournalContract.USER_EMAIL));
            // image.setImageURI(Uri.parse(bundle.getString(JournalContract.USER_IMAGE)));
        }


        //mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //setUpViewPager(mViewPager,getSupportFragmentManager());

        //mTabLayout = (TabLayout)findViewById(R.id.tabs);
        //mTabLayout.setupWithViewPager(mViewPager);

        //mTabLayout = (TabLayout)findViewById(R.id.tabs);
        //mTabLayout.setupWithViewPager(mViewPager);

        //setUpIcons();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddActivity = new Intent(MainActivity.this, AddJournalActivity.class);
                startActivity(startAddActivity);

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mJournalAdapter = new JournalAdapter(this, this);

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mJournalAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int) viewHolder.itemView.getTag();

                Uri uri = JournalContract.JournalEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();

                getContentResolver().delete(uri, null, null);

                getSupportLoaderManager().restartLoader(JOURNAL_LOADER_ID, null, MainActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);


        getSupportLoaderManager().initLoader(JOURNAL_LOADER_ID, null, this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mCursor = null;

            @Override
            protected void onStartLoading() {

                super.onStartLoading();
                if (mCursor != null) {
                    deliverResult(mCursor);
                } else {
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

    private void loadDummyData() {

    }

    @Override
    protected void onResume() {
        super.onResume();


        getSupportLoaderManager().restartLoader(JOURNAL_LOADER_ID, null, this);
    }

    @Override
    public void onClick(int id) {
        Uri uri = JournalContract.JournalEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        Intent openSingleActivity = new Intent(MainActivity.this, SingleDisplayActivity.class);
        openSingleActivity.setData(uri);
        startActivity(openSingleActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            mFirebaseUtils.logOutUser();
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


   /* public void setUpIcons(){
        for (int i = 0; i <icons.length ; i++) {
            mTabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }*/
}
