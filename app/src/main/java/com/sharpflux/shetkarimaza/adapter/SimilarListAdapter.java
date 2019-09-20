package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.fragment.RateDialogFragment;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListViewHolder> {

    private Context mContext;
    private List<SimilarList> mlist;

    private static int currentPosition = 0;

    public SimilarListAdapter(Context mContext, List<SimilarList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public SimilarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_similar_list, parent, false);
        return new SimilarListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SimilarListViewHolder holder, final int position) {
        Picasso.get().load(mlist.get(position).getImageUrl()).resize(300, 300).into(holder.mImage);
        holder.mName.setText(mlist.get(position).getName());
        holder.mvarity.setText(mlist.get(position).getVarietyName());
        holder.mQuality.setText(mlist.get(position).getQuality().toString());
        holder.mPrice.setText(String.valueOf( mlist.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

class SimilarListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mName,mvarity,mQuality,mPrice;

    SimilarListViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.row_cartlist_ivProImg);
        mName = itemView.findViewById(R.id.row_cartlist_tvName);
        mvarity = itemView.findViewById(R.id.row_cartlist_tvVarity);
        mQuality = itemView.findViewById(R.id.row_cartlist_tvQuality);
        mPrice = itemView.findViewById(R.id.row_cartlist_tvPrice);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*Context context=v.getContext();
        Intent intent;
        intent =  new Intent(context, ProductDetailsForBuyerActivity.class);
        context.startActivity(intent);*/
    }
}/////