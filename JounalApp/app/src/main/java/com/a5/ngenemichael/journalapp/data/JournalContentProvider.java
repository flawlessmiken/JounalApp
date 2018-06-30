package com.a5.ngenemichael.journalapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.a5.ngenemichael.journalapp.data.JournalContract.JournalEntry.TABLE_NAME;

/**
 * Created by Flawless on 6/25/2018.
 */

public class JournalContentProvider extends ContentProvider{

    private JournalDbHelper mJournalDbHelper;
    private static final  int JOURNAL = 100;
    private static final int JOURNAL_WITH_ID = 101;
    private static  final UriMatcher sUrimatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mJournalDbHelper = new JournalDbHelper(context);
        return true;
    }

    private  static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(JournalContract.AUTHORITY, JournalContract.JOURNAL_PATH, JOURNAL);
        uriMatcher.addURI(JournalContract.AUTHORITY, JournalContract.JOURNAL_PATH + "/#", JOURNAL_WITH_ID);

        return uriMatcher;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                         String[] selectionArgs, String sortOrder) {


        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mJournalDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUrimatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case JOURNAL:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case JOURNAL_WITH_ID:

                String index = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{index};

                retCursor = db.query(TABLE_NAME,
                        projection,
                        JournalContract.JournalEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder
                        );

                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mJournalDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        Uri returnUri;

        switch (match){
            case JOURNAL:
                long id = db.insert(TABLE_NAME, null, contentValues);

                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(JournalContract.JournalEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default: throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mJournalDbHelper.getWritableDatabase();

        int match = sUrimatcher.match(uri);
        // Keep track of the number of deleted tasks
        int journalDelete; // starts as 0

        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single
            // item case, recognized by the ID included in the URI path
            case JOURNAL_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                journalDelete = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (journalDelete != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return journalDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

            final SQLiteDatabase database = mJournalDbHelper.getWritableDatabase();

            int match = sUrimatcher.match(uri);

            int journalUpdate;

            switch (match){
                case JOURNAL_WITH_ID:
                    String id  = uri.getPathSegments().get(1);
                    journalUpdate = database.update(TABLE_NAME,contentValues,"_id=?",new String[]{id});
                    break;

                default:
                    throw new UnsupportedOperationException("Unknown uri:" + uri );
            }

            if(journalUpdate != 0){
                getContext().getContentResolver().notifyChange(uri, null);
            }


        return journalUpdate;
    }
}
