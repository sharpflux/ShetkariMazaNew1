package com.sharpflux.shetkarimaza.filters;

import android.content.Context;
import android.database.Cursor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class VarietyAdapter extends RecyclerView.Adapter<VarietytViewHolder> implements Filterable {

    private Context mContext;

    public static String ProductId, FilterBy;
    private static int currentPosition = 0;

    private List<SubCategoryFilter> exampleList;
    private List<SubCategoryFilter> exampleListFull;
    dbBuyerFilter myDatabase;

    public VarietyAdapter(Context mContext, List<SubCategoryFilter> exampleList) {
        this.mContext = mContext;
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        myDatabase = new dbBuyerFilter(mContext);

    }


    @Override
    public VarietytViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_child_view, parent, false);
        return new VarietytViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(VarietytViewHolder holder, final int position) {
        holder.mlist = exampleList;
        holder.position = position;
        holder.mTextView.setText(exampleList.get(position).getName());
        holder.ProductId = exampleList.get(position).getId();
        holder.FilterBy = exampleList.get(position).getFilterBy();
        ProductId = exampleList.get(position).getId();
        holder.myDatabase = myDatabase;
        if (holder.FilterBy != null) {
            switch (holder.FilterBy) {
                case "VARIETY":
                    Cursor VARIETYCursor = myDatabase.FilterGetByFilterName("VARIETY");
                    while (VARIETYCursor.moveToNext()) {
                        if (VARIETYCursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
                case "QUALITY":
                    Cursor QUALITYCursor = myDatabase.FilterGetByFilterName("QUALITY");
                    while (QUALITYCursor.moveToNext()) {
                        if (QUALITYCursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
                case "AGE":
                    Cursor AGECursor = myDatabase.FilterGetByFilterName("AGE");
                    while (AGECursor.moveToNext()) {
                        if (AGECursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
                case "STATE":
                    Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                    while (STATECursor.moveToNext()) {
                        if (STATECursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
                case "DISTRICT":
                    Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                    while (DISTRICTCursor.moveToNext()) {
                        if (DISTRICTCursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
                case "TALUKA":
                    Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");
                    while (TALUKACursor.moveToNext()) {
                        if (TALUKACursor.getString(0).equals(holder.ProductId)) {
                            holder.mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
                            holder.mTextView.setChecked(true);
                        }
                    }
                    break;
            }
        }

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
            List<SubCategoryFilter> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SubCategoryFilter item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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


class VarietytViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    AppCompatCheckedTextView mTextView;
    String ProductId = "", childName = "", FilterBy = "";
    int position = 0;
    List<SubCategoryFilter> mlist;
    dbBuyerFilter myDatabase;

    VarietytViewHolder(View itemView) {
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

            myDatabase.DeleteRecord(FilterBy, ProductId);
            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_outline_blank_black_24dp);
            mTextView.setChecked(false);
            SubCategoryFilter filter = mlist.get(position);
            filter.setSelected(false);
            //Toast.makeText(c, pid, Toast.LENGTH_LONG).show();
        } else {

            pid = ProductId;
            n = childName;
            Log.e(pid, n);
            SubCategoryFilter filter = mlist.get(position);
            filter.setSelected(true);
            myDatabase.FilterInsert(FilterBy, ProductId);
            // set check mark drawable and set checked property to true
            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
            mTextView.setChecked(true);
        }

    }

}