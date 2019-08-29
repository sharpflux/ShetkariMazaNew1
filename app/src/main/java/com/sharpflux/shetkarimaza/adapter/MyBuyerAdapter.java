package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.filters.FilterActivity;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyBuyerAdapter extends RecyclerView.Adapter<FlowerViewHolder> {

    private Context mContext;
    private List<SellOptions> mList;
    public String id;
    private static int currentPosition = 0;

    public MyBuyerAdapter(Context mContext, List<SellOptions> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_buyer, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, final int position) {
        Picasso.get().load(mList.get(position).getImage()).resize(300, 300).into(holder.mImage);

        holder.mTitle.setText(mList.get(position).getProductlist());
        holder.ItemTypeId=mList.get(position).getProductId();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class FlowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mTitle;
    String ItemTypeId;

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