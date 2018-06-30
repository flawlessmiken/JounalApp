package com.a5.ngenemichael.journalapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.Activities.WelcomeActivity;
import com.a5.ngenemichael.journalapp.data.JournalContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirebaseUtils {


    private  static FirebaseAuth firebaseAuth;
    private static  FirebaseAuth.AuthStateListener  authStateListener;
    private static String userName;
    private static String email;
    private static Uri image_uri;
    private static Context mContext;



    public FirebaseUtils(Context context){
        mContext = context;
    }

    public void logOutUser(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        mContext.startActivity(new Intent(mContext.getApplicationContext(),WelcomeActivity.class));
        resetUser(mContext);

    }


    public static void setUpUser(){

        firebaseAuth = FirebaseAuth.getInstance();
        final Intent intent = new Intent(mContext,WelcomeActivity.class);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    mContext.startActivity(intent);
                }
            }
        };

        final FirebaseUser user  = firebaseAuth.getCurrentUser();
        assert user != null;
        userName = user.getDisplayName();
        email = user.getEmail();
        image_uri = user.getPhotoUrl();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(mContext.getString(R.string.pref_user_name_key),userName);
        editor.putString(mContext.getString(R.string.pref_user_email_key), email);
        editor.putString(mContext.getString(R.string.pref_user_image_key), image_uri.toString());
        editor.apply();
    }



    public static void resetUser(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.remove(mContext.getString(R.string.pref_user_name_key));
        editor.remove(mContext.getString(R.string.pref_user_email_key));
        editor.remove(mContext.getString(R.string.pref_user_image_key));
        editor.apply();
    }

    public static Bundle getUserDetails(Context context){
        Bundle bundle = new Bundle();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String userName = context.getString(R.string.pref_user_name_key);
        String userEmail = context.getString(R.string.pref_user_email_key);
        String userImage = context.getString(R.string.pref_user_image_key);

        bundle.putString(JournalContract.USER_NAME,sp.getString(userName,""));
        bundle.putString(JournalContract.USER_EMAIL,sp.getString(userEmail,""));
        bundle.putString(JournalContract.USER_IMAGE,sp.getString(userImage,""));

        return bundle;
    }


}
