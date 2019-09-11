package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.filters.FilterActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyBuyerAdapter extends RecyclerView.Adapter<FlowerViewHolder> implements Filterable {

    private Context mContext;

    public String id;
    private static int currentPosition = 0;

    private List<SellOptions> mList;
    private List<SellOptions> exampleListFull;

    public MyBuyerAdapter(Context mContext, List<SellOptions> mList) {
        this.mContext = mContext;
        this.mList = mList;
        exampleListFull = new ArrayList<>(mList);

    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_buyer, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, final int position) {
        //Log.d("Image Path", mList.get(position).getImage());
        try {
            if (mList.get(position).getImage() != null) {
                Picasso.get().load(mList.get(position).getImage()).resize(300, 300).into(holder.mImage);
                holder.mTitle.setText(mList.get(position).getProductlist());
                holder.ItemTypeId = mList.get(position).getProductId();
            }
        }
        catch(Exception d){

        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SellOptions> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SellOptions item : exampleListFull) {
                    if (item.getProductlist().toLowerCase().contains(filterPattern)) {
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
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

class FlowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mTitle;
    String ItemTypeId;
    List<SubCategoryFilter> mlist;

    FlowerViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context=v.getContext();
        Intent intent;

        intent =  new Intent(context, FilterActivity.class);
        intent.putExtra("ItemTypeId",ItemTypeId);
        context.startActivity(intent);
    }
}