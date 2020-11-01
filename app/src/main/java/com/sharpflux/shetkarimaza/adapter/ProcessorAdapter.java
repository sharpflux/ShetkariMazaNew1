package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.MyProcessor;

import java.util.ArrayList;
import java.util.List;


public class ProcessorAdapter extends RecyclerView.Adapter<ProcessorViewHolder> implements Filterable {

    private Context mContext;

    public static String ProductId;
    private static int currentPosition = 0;

    private List<MyProcessor> exampleList;
    private List<MyProcessor> exampleListFull;


    public ProcessorAdapter(Context mContext, List<MyProcessor> exampleList) {

        this.mContext = mContext;
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);

    }


    @Override
    public ProcessorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_child_view, parent, false);
        return new ProcessorViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ProcessorViewHolder holder, final int position) {
        holder.mlist = exampleList;
        holder.position = position;

        holder.mTextView.setText(exampleList.get(position).getItemName());

        holder.ProductId = exampleList.get(position).getItemTypeId();
        ProductId = exampleList.get(position).getItemTypeId();
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MyProcessor> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MyProcessor item : exampleListFull) {
                    if (item.getItemName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}


class ProcessorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    AppCompatCheckedTextView mTextView;

    String ProductId = "", childName = "";
    int position = 0;
    List<MyProcessor> mlist;

    ProcessorViewHolder(View itemView) {
        super(itemView);
        this.setIsRecyclable(false);
        mTextView = itemView.findViewById(R.id.tv_movies);
        mTextView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        // int adapterPosition = getAdapterPosition();
        String pid = "";
        String n = "";
        Boolean value = mTextView.isChecked();
        if (value) {
            // set check mark drawable and set checked property to false

            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
            mTextView.setChecked(false);
            MyProcessor filter = mlist.get(position);
            filter.setSelected(false);
            //Toast.makeText(c, pid, Toast.LENGTH_LONG).show();
        } else {

            pid = ProductId;
            n = childName;
            Log.e(pid, n);
            MyProcessor filter = mlist.get(position);
            filter.setSelected(true);
            // set check mark drawable and set checked property to true
            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
            mTextView.setChecked(true);
        }

    }

}/////