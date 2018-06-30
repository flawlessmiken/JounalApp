package com.a5.ngenemichael.journalapp.data;

import android.net.Uri;
import android.provider.BaseColumns;



public class JournalContract {
    public static final String AUTHORITY = "com.a5.ngenemichael.journalapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String JOURNAL_PATH = "journal";

    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_IMAGE = "user_image";




    public static class JournalEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(JOURNAL_PATH).build();


        public static final String DATE = "date";

        public static final String  TABLE_NAME = "journal";

        public static final String MOOD = "mood";


        public static final String TITLE = "title";

        public static final String DETAIL = "detail";


    }
}
