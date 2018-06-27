package com.a5.ngenemichael.journalapp.data;

import android.content.Context;
import com.a5.ngenemichael.journalapp.data.JournalContract.JournalEntry;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Flawless on 6/25/2018.
 */

public class JournalDbHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "journalDB.db";

    private static final int DATABASE_VERSION = 3;
    public JournalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*final String CREATE_TABLE = "CREATE TABLE "  + TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_PRIORITY    + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);*/

        final String CREATE_TABLE = "CREATE TABLE " + JournalEntry.TABLE_NAME + " (" +
                JournalEntry._ID            + " INTEGER PRIMARY KEY, "+
                JournalEntry.DATE           + " INTEGER NOT NULL, " +
                JournalEntry.MOOD           + " INTEGER NOT NULL, "+
                JournalEntry.TITLE          + " TEXT NOT NULL, " +
                JournalEntry.DETAIL         + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ JournalEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
