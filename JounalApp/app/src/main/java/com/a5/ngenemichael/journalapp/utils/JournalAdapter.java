package com.a5.ngenemichael.journalapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a5.ngenemichael.journalapp.R;
import com.a5.ngenemichael.journalapp.data.JournalContract;

/**
 * Created by Flawless on 6/25/2018.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private  Context mContext;
    private Cursor mCursor;

    public JournalAdapter (Context context){
        this.mContext = context;
    }

    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_modeler,null);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {

        int indexId  = mCursor.getColumnIndex(JournalContract.JournalEntry._ID);
        int dateIndex  = mCursor.getColumnIndex(JournalContract.JournalEntry.DATE);
        int titleIndex  = mCursor.getColumnIndex(JournalContract.JournalEntry.TITLE);
        int detail  = mCursor.getColumnIndex(JournalContract.JournalEntry.DETAIL);

        mCursor.moveToPosition(position);

        holder.itemView.setTag(mCursor.getInt(indexId));
        holder.dateText.setText(mCursor.getString(dateIndex));
        holder.titleText.setText(mCursor.getString(titleIndex));
        holder.detailText.setText(mCursor.getString(detail));

    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
        return 0;}
        else return mCursor.getCount();
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder{

        private TextView dateText, titleText, detailText;
        private ImageView mood;
        public JournalViewHolder(View itemView) {
            super(itemView);
            dateText = (TextView)itemView.findViewById(R.id.tv_date);
            titleText = (TextView)itemView.findViewById(R.id.tv_title);
            detailText = (TextView)itemView.findViewById(R.id.tv_detail);
            mood = (ImageView)itemView.findViewById(R.id.iv_mood);
        }
    }

    public Cursor swapCursor (Cursor cursor){
        if(mCursor == cursor){
            return  null;
        }
        Cursor temp = mCursor;
        this.mCursor = cursor;

        if(cursor != null){
            notifyDataSetChanged();
        }

        return temp;
    }
}
