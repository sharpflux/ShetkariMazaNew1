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

import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MySellerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mmContext;
    private List<SellOptions> sellOptions;
    public String id;
    private static int currentPosition = 0;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public MySellerAdapter(Context mContext, List<SellOptions> sellOptions) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_seller, parent, false);
            return new SellerViewHolder(mView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolderSeller(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SellerViewHolder)
            try {
                Picasso.get().load(sellOptions.get(position).getImage()).into(((SellerViewHolder) holder).mImage);
                ((SellerViewHolder) holder).mTitle.setText(sellOptions.get(position).getProductlist());
                ((SellerViewHolder) holder).ProductId = sellOptions.get(position).getProductId();
            } catch (Exception d) {

            }
        else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolderSeller) holder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return sellOptions.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return sellOptions.size();
    }

    private void showLoadingView(LoadingViewHolderSeller viewHolder, int position) {
        //ProgressBar would be displayed

    }

}


class SellerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImage;
    TextView mTitle;
    String ProductId;


    SellerViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.ivsellerplantLogo);
        mTitle = itemView.findViewById(R.id.ivsellerplanttype);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Context context = v.getContext();
        Intent intent;
        intent = new Intent(context, ProductInfoForSaleActivity.class);
        intent.putExtra("ProductId", ProductId);
        context.startActivity(intent);

    }


}

class LoadingViewHolderSeller extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolderSeller(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}