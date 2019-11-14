package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.fragment.DynamicFragment;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mmContext;
    private ArrayList<GroupData> sellOptions;


    public MyGroupAdapter(Context mContext, ArrayList<GroupData> sellOptions) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_layout, parent, false);
            return new GroupViewHolder1(mView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

/*

        Picasso.get().load(sellOptions.get(position).getImage()).resize(300, 300)
                .into(holder.mImage);
        holder.mTitle.setText(sellOptions.get(position).getName());
*/




    }



    @Override
    public int getItemCount() {
        return sellOptions.size();
    }



}


class GroupViewHolder1 extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    String ProductId;


    GroupViewHolder1(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.img_group);
        mTitle = itemView.findViewById(R.id.txt_groupName);


    }

}

