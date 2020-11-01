package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder>  {

    private Context mContext;
    public String id;
    private static int currentPosition = 0;


    private ArrayList<SubCategory> mList;

    public SubCategoryAdapter(Context mContext, ArrayList<SubCategory> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public SubCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_category_one, parent, false);
            return new SubCategoryHolder(mView);


    }

    @Override
    public void onBindViewHolder(SubCategoryHolder holder, final int position) {
        View view;

                String categoryId="";
                    Picasso.get().load(mList.get(position).getImage()).resize(300, 300)
                            .into(holder.mImage);
                     holder.mTitle.setText(mList.get(position).getName());
                     holder.categoryId = mList.get(position).getId();


    }



    @Override
    public int getItemCount() {
        return  mList.size();
    }


}

class SubCategoryHolder extends RecyclerView.ViewHolder{

    ImageView mImage;
    TextView mTitle;
    String categoryId="";
    List<SubCategory> mlist;

    SubCategoryHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImageCategory);
        mTitle = itemView.findViewById(R.id.tvTitleCategory);

    }



}
