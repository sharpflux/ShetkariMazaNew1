package com.sharpflux.shetkarimaza.filters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.Product;

import java.util.ArrayList;
import java.util.List;


public class VarietyAdapter extends RecyclerView.Adapter<VarietytViewHolder> implements Filterable {

    private Context mContext;

    public static String ProductId;
    private static int currentPosition = 0;

    private List<SubCategoryFilter> exampleList;
    private List<SubCategoryFilter> exampleListFull;


    public VarietyAdapter(Context mContext, List<SubCategoryFilter> exampleList) {
        this.mContext = mContext;
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);

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
        ProductId = exampleList.get(position).getId();
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
    String ProductId = "", childName = "";
    int position = 0;
    List<SubCategoryFilter> mlist;

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
            // set check mark drawable and set checked property to false

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
            // set check mark drawable and set checked property to true
            mTextView.setCheckMarkDrawable(R.drawable.ic_check_box_black_24dp);
            mTextView.setChecked(true);
        }

    }

}/////