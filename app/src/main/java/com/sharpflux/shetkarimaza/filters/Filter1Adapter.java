package com.sharpflux.shetkarimaza.filters;

import android.content.Context;
import android.database.Cursor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SubCategoryFilter1;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;

import java.util.ArrayList;
import java.util.List;


public class Filter1Adapter extends RecyclerView.Adapter<Filter1ViewHolder> implements Filterable {

    private Context mContext;

    public static String ProductId;
    private static int currentPosition = 0;

    private List<SubCategoryFilter1> exampleList;
    private List<SubCategoryFilter1> exampleListFull;
    dbFilter myDatabase;

    public Filter1Adapter(Context mContext, List<SubCategoryFilter1> exampleList) {
        this.mContext = mContext;
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);

    }


    @Override
    public Filter1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter1_child_view, parent, false);
        return new Filter1ViewHolder(mView);
    }



    @Override
    public void onBindViewHolder(Filter1ViewHolder holder, final int position) {
        myDatabase = new dbFilter(mContext);
        holder.mlist = exampleList;
        holder.position = position;
        holder.tvFilterName.setText(exampleList.get(position).getName());
        holder.ProductId = exampleList.get(position).getId();
        ProductId = exampleList.get(position).getId();
        holder.FilterBy=exampleList.get(position).getFilterBy();
        holder.myDatabase=myDatabase;
        Cursor res = myDatabase.FilterGetByFilterName(holder.FilterBy);
        while (res.moveToNext()) {
            if(res.getString(0).equals(ProductId)) {
                holder.chkbox.setChecked(true);
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
            List<SubCategoryFilter1> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SubCategoryFilter1 item : exampleListFull) {
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

class Filter1ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //AppCompatCheckedTextView mTextView;
    String ProductId = "", FilterBy = "";
    int position = 0;
    List<SubCategoryFilter1> mlist;
    TextView tvFilterName;
    CheckBox chkbox;
    dbFilter myDatabase;
    Filter1ViewHolder(View itemView) {
        super(itemView);
        this.setIsRecyclable(false);
        tvFilterName = itemView.findViewById(R.id.tvFilterName);
        chkbox=itemView.findViewById(R.id.chkbox);
        chkbox.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        // int adapterPosition = getAdapterPosition();
        String pid = "";
        String n = "";
        Boolean value = chkbox.isChecked();
        if (value) {
            pid = ProductId;
            n = tvFilterName.getText().toString();
            Log.d(pid, n);
            Log.e(FilterBy,n);
            myDatabase.FilterInsert(FilterBy,pid);
            SubCategoryFilter1 filter = mlist.get(position);
            filter.setSelected(true);

        } else {
            pid = ProductId;
            n = tvFilterName.getText().toString();
            Log.e(pid, n);
            Log.e(FilterBy,n);
            myDatabase.DeleteRecord(FilterBy,pid);
            SubCategoryFilter1 filter = mlist.get(position);
            filter.setSelected(false);

        }

    }

}


