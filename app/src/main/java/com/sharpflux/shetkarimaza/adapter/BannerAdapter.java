package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.ListModel;

import java.util.ArrayList;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    Context context;
    ArrayList<ListModel> models;

    public BannerAdapter(Context context2, ArrayList<ListModel> models2) {
        this.context = context2;
        this.models = models2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_vegType.setText(this.models.get(position).getList());
    }

    public int getItemCount() {
        return this.models.size();
    }

    /* renamed from: ws.design.dailygrocery.adapter.BannerAdapter$ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_vegType;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_vegType = (TextView) itemView.findViewById(R.id.txt_vegType);
        }
    }
}