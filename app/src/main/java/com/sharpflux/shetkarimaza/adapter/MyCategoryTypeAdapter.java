package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCategoryTypeAdapter extends RecyclerView.Adapter<MyCategoryTypeHolder> {

    private Context mContext;
    public String id;
    private static int currentPosition = 0;


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private ArrayList<MyCategoryType> mList;

    public MyCategoryTypeAdapter(Context mContext, ArrayList<MyCategoryType> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public MyCategoryTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_merchant, parent, false);
            return new MyCategoryTypeHolder(mView);
        } else {
            View nview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new MyCategoryTypeHolder(nview);
        }


    }

    @Override
    public void onBindViewHolder(MyCategoryTypeHolder holder, final int position) {
        View view;
        String categoryId = "";

        if (holder instanceof MyCategoryTypeHolder)
            try {
                //Picasso.get().load(mList.get(position).getImage()).into(((MyCategoryTypeHolder) holder).mImage);

                Glide.with(mContext)
                        .load(URLs.Main_URL+mList.get(position).getImage()).placeholder(R.drawable.kisanmaza)
                        .into(holder.mImage);


                ((MyCategoryTypeHolder) holder).mTitle.setText(mList.get(position).getCategoryTypeName());
                ((MyCategoryTypeHolder) holder).categoryId = mList.get(position).getCategoryTypeId();
            } catch (Exception d) {

            }
      /*  else if (holder instanceof LoadingViewHolderSeller1)
        {
            showLoadingView1((LoadingViewHolderSeller1) holder, position);
        }*/


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private void showLoadingView1(LoadingViewHolderSeller1 viewHolder, int position) {
    }
}

class MyCategoryTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImage;
    TextView mTitle;
    String categoryId = "";
    List<SubCategoryFilter> mlist;

    MyCategoryTypeHolder(View itemView) {
        super(itemView);

        //mImage = itemView.findViewById(R.id.ivImageCategory);
       // mTitle = itemView.findViewById(R.id.tvTitleCategory);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();


        if (categoryId .equals("2")) {
            Intent intent = new Intent(context, SellerActivity.class);
            intent.putExtra("ProductId", categoryId);
            context.startActivity(intent);
        } else if (categoryId .equals("3") ) {
            Intent intent = new Intent(context, BuyerActivity.class);
            intent.putExtra("ProductId", categoryId);
            context.startActivity(intent);
        } else
        {
            Intent intent = new Intent(context, ContactDetailActivity.class);
            intent.putExtra("ProductId", categoryId);
            context.startActivity(intent);
        }
    }


}


class LoadingViewHolderSeller1 extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolderSeller1(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}


