package com.a5.ngenemichael.journalapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.data.JournalContract;
import com.a5.ngenemichael.journalapp.utils.FirebaseUtils;
import com.a5.ngenemichael.journalapp.utils.ImageUtils;

public class SingleDisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final int ID_DETAIL_LOADER = 353;

    private TextView date_tv, title_tv, detail_tv, mood_tv;
    private ImageView image_mood;

    private String  title, detail;
    private int image_index;

    private ImageUtils mImageUtils;

    FirebaseUtils mFirebaseUtils;

    private Uri mUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_display);

        mFirebaseUtils = new FirebaseUtils(this);
        mImageUtils = new ImageUtils(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(2.5f);

        date_tv =(TextView)findViewById(R.id.date_tv);
        title_tv= (TextView)findViewById(R.id.title_tv);
        detail_tv  = (TextView)findViewById(R.id.detail_tv);
        mood_tv  = (TextView)findViewById(R.id.mood_tv);
        image_mood  = (ImageView) findViewById(R.id.icon);


        mUri = getIntent().getData();
        if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null");

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_DETAIL_LOADER:

                return new CursorLoader(this,
                        mUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            boolean hasValidData = false;

            if(data != null && data.moveToFirst()){
                hasValidData = true;
            }
            if(!hasValidData){
                return;
            }

            title = data.getString(data.getColumnIndex(JournalContract.JournalEntry.TITLE));
            detail = data.getString(data.getColumnIndex(JournalContract.JournalEntry.DETAIL));
            image_index= data.getInt(data.getColumnIndex(JournalContract.JournalEntry.MOOD));



            date_tv.setText(data.getString(data.getColumnIndex(JournalContract.JournalEntry.DATE)));
        title_tv.setText(title);
        detail_tv.setText(detail);

        image_mood.setImageResource(mImageUtils.getIcons()[image_index]);
        mood_tv.setText(mImageUtils.getIcon_name()[image_index]);



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.action_logout) {
            mFirebaseUtils.logOutUser();
            finish();
            return true;
        }

        if (i == R.id.action_delete) {
            getContentResolver().delete(mUri,null,null);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openUpdate(View view) {

       Intent intent = new Intent(SingleDisplayActivity.this, AddJournalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JournalContract.JournalEntry.TITLE,title);
        bundle.putString(JournalContract.JournalEntry.DETAIL,detail);
        intent.setData(mUri);
        intent.putExtra(Intent.EXTRA_TEXT,bundle);
        startActivity(intent);

        finish();
    }


}
