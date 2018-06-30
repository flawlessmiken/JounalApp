package com.a5.ngenemichael.journalapp.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.a5.ngenemichael.journalapp.R;

public class ImageUtils extends BaseAdapter {


    private static Context mContext;

    public ImageUtils(Context context) {
        mContext = context;
    }

    private static final int[] icons =
            {
                    R.drawable.ill,
                    R.drawable.happy,
                    R.drawable.sad,
                    R.drawable.angry,
                    R.drawable.confused,
                    R.drawable.bored,
                    R.drawable.crying,
                    R.drawable.surprised,
                    R.drawable.quiet,
                    R.drawable.ninja

            };


    private static String[] icon_name = {"Ill",
            "Happy",
            "Sad",
            "Angry",
            "Confused",
            "Bored",
            "Crying",
            "Surprised",
            "Quiet",
            "Ninja"
    };

    public static int[] getIcons() {
        return icons;
    }

    public static String[] getIcon_name() {
        return icon_name;
    }

    @Override
    public int getCount() {
        return getIcons().length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(75, 75));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) view;
        }
        imageView.setImageResource(icons[i]);
        return imageView;
    }

    public int getSingleImage(int position) {
        return getIcons()[position];
    }

}
