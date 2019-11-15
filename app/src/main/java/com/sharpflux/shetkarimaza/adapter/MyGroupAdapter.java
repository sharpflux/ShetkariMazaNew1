package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.sharpflux.shetkarimaza.fragment.GroupFragment;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyGroupAdapter extends RecyclerView.Adapter<GroupViewHolder1> {

    private Context mmContext;
    private ArrayList<GroupData> sellOptions;


    public MyGroupAdapter(Context mContext, ArrayList<GroupData> sellOptions) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
    }

    @Override
    public GroupViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_layout, parent, false);
            return new GroupViewHolder1(mView);

    }

    @Override
    public void onBindViewHolder(GroupViewHolder1 holder, int i) {
        Picasso.get().load(sellOptions.get(i).getImage()).resize(300, 300)
                .into(holder.Image);
        holder.Title.setText(sellOptions.get(i).getName());

    }


    @Override
    public int getItemCount() {
        return sellOptions.size();
    }



}


class GroupViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView Image;
    TextView Title;
    String ProductId;


    GroupViewHolder1(View itemView) {
        super(itemView);
        Image = itemView.findViewById(R.id.ivsellerplantLogo);
        Title = itemView.findViewById(R.id.ivsellerplanttype);

    }

    @Override
    public void onClick(View view) {

       /* FragmentManager fragmentManager =;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DynamicFragment NAME = new DynamicFragment();
        fragmentTransaction.replace(R.id.frame, NAME);
        fragmentTransaction.commit();
*/
    }

}

