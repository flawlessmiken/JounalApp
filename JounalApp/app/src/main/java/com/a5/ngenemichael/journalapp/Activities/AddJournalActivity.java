package com.a5.ngenemichael.journalapp.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.data.JournalContract;
import com.a5.ngenemichael.journalapp.utils.ImageUtils;

public class AddJournalActivity extends AppCompatActivity {

    private EditText mTitle , mDetails; Uri mUri;
    private int moodIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        setUp();

    }

    private void setUp(){

        final ImageUtils imageUtils = new ImageUtils(this);
        GridView gridView;
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageUtils(this));
        final ImageView mainImage = (ImageView)findViewById(R.id.image_main);
        final TextView mainText = (TextView)findViewById(R.id.text_mood);
        mainImage.setImageResource(ImageUtils.getIcons()[9]);
        mainText.setText(ImageUtils.getIcon_name()[9]);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainImage.setImageResource(ImageUtils.getIcons()[i]);
                mainText.setText(ImageUtils.getIcon_name()[i]);
                moodIndex = i;
            }
        });


        mTitle  =(EditText)findViewById(R.id.title_add);
        mDetails = (EditText)findViewById(R.id.detailAdd);

        Intent intent = getIntent();

        if(intent.getData() != null) {
            Uri uri = getIntent().getData();
            mUri = uri;

            Bundle b = intent.getBundleExtra(Intent.EXTRA_TEXT);
            mTitle.setText(b.getString(JournalContract.JournalEntry.TITLE));
            mDetails.setText(b.getString(JournalContract.JournalEntry.DETAIL));

        }
    }

    public void saveToEntry(View view) {


        if (!(mDetails.getText().toString().isEmpty()) && !(mTitle.getText().toString().isEmpty())) {

            if(mUri != null){
                UpdateEntry();
                return;
            }

            ContentValues contentValues = new ContentValues();
            // Put the task description and selected mPriority into the ContentValues
            contentValues.put(JournalContract.JournalEntry.DATE, "Friday Apr 4th 2018" );
            contentValues.put(JournalContract.JournalEntry.TITLE, mTitle.getText().toString());
            contentValues.put(JournalContract.JournalEntry.DETAIL, mDetails.getText().toString());
            contentValues.put(JournalContract.JournalEntry.MOOD,moodIndex );
            Uri uri = getContentResolver().insert(JournalContract.JournalEntry.CONTENT_URI, contentValues);
            if (uri != null) {
                Toast.makeText(getBaseContext(), String.valueOf(moodIndex), Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }

    private void UpdateEntry(){

        int id;
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(JournalContract.JournalEntry.DATE, "Friday Apr 4th 2018" );
        contentValues.put(JournalContract.JournalEntry.TITLE, mTitle.getText().toString());
        contentValues.put(JournalContract.JournalEntry.DETAIL, mDetails.getText().toString());
        contentValues.put(JournalContract.JournalEntry.MOOD, moodIndex);

        id = getContentResolver().update(mUri, contentValues,null,null);
        if (id != 0) {
            //Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
        finish();

    }

}
