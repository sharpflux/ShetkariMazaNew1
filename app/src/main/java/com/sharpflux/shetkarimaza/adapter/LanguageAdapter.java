package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharpflux.shetkarimaza.R;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapterViewHolder> {

    private Context mContext;
    private List<String> mlist;
    public static String ProductId;
    private static int currentPosition = 0;

    public LanguageAdapter(Context mContext, List<String> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;

    }


    @Override
    public LanguageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_child_view, parent, false);
        return new LanguageAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(LanguageAdapterViewHolder holder, final int position) {
        holder.mlist = mlist;
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

class LanguageAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    AppCompatCheckedTextView mTextView;
    int position = 0;
    List<String> mlist;

    LanguageAdapterViewHolder(View itemView) {
        super(itemView);
        this.setIsRecyclable(false);
        mTextView = itemView.findViewById(R.id.tv_movies);
        mTextView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        Boolean value = mTextView.isChecked();
        if (value) {

            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
            mTextView.setChecked(false);
            String filter = mlist.get(position);
            //Toast.makeText(c, pid, Toast.LENGTH_LONG).show();
        } else {

            String filter = mlist.get(position);

            // set check mark drawable and set checked property to true
            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
            mTextView.setChecked(true);
        }

    }

}/////