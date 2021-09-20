package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.GovtSchemeDetailsActivity;
import com.sharpflux.shetkarimaza.activities.GovtSchemes;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.List;

public class GovtSchemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    public List<ContactDetail> mItemList;

    public GovtSchemeAdapter(Context mContext, List<ContactDetail> itemList) {
        this.mContext = mContext;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_govt_schemes, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {

        ImageView imgGovtScheme;
        TextView tvSchemeTitle;
        LinearLayout linearRateView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGovtScheme = itemView.findViewById(R.id.imgGovtScheme);
            tvSchemeTitle=itemView.findViewById(R.id.tvSchemeTitle);
        }


        @Override
        public void onClick(View view) {
            Toast.makeText(mContext," error.getMessage()", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        Glide.with(mContext).load(URLs.Main_URL+mItemList.get(position).getImage())
                .placeholder(R.drawable.kisanmaza).into(viewHolder.imgGovtScheme);

        viewHolder.tvSchemeTitle.setText(mItemList.get(position).getFullName());


        viewHolder.imgGovtScheme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GovtSchemeDetailsActivity.class);
                intent.putExtra("ProductId", "categoryId");
                mContext.startActivity(intent);
            }
        });
        viewHolder.tvSchemeTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GovtSchemeDetailsActivity.class);
                intent.putExtra("ProductId", "categoryId");
                mContext.startActivity(intent);
            }
        });


    }


}


