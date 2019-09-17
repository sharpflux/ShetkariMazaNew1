package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.RateData;


import java.util.List;

public class RateAdapter extends RecyclerView.Adapter<RateAdapterViewHolder> {
    private Context mContext;
    private List<RateData> mlist;

    private static int currentPosition = 0;

    public RateAdapter(Context mContext, List<RateData> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public RateAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_similar_list, viewGroup, false);
        return new RateAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RateAdapterViewHolder holder, int i) {
        holder.mfrom.setText(mlist.get(i).getFromdate());
        holder.mto.setText(mlist.get(i).getTodate());
        holder.mrate.setText(mlist.get(i).getRate());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class RateAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView mfrom,mto,mrate;
    public RateAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        mfrom = itemView.findViewById(R.id.tv_from);
        mto = itemView.findViewById(R.id.tv_to);
        mrate = itemView.findViewById(R.id.rate);

    }
}
