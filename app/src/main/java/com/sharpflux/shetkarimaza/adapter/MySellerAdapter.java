package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MySellerAdapter extends RecyclerView.Adapter<SellerViewHolder > {

    private Context mmContext;
    private List<SellOptions> sellOptions;
    public String id;
    private static int currentPosition = 0;

    public MySellerAdapter(Context mContext, List<SellOptions> sellOptions) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
    }

    @Override
    public SellerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_seller, parent, false);
        return new SellerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SellerViewHolder holder, final int position) {

        Picasso.get().load(sellOptions.get(position).getImage()).into(holder.mImage);
        holder.mTitle.setText(sellOptions.get(position).getProductlist());
        holder.ProductId=sellOptions.get(position).getProductId();

    }

    @Override
    public int getItemCount() {
        return sellOptions.size();
    }


}



class SellerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

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

        Context context=v.getContext();
        Intent intent;
        intent =  new Intent(context, ProductInfoForSaleActivity.class);
        intent.putExtra("ProductId",ProductId);
        context.startActivity(intent);

    }


}