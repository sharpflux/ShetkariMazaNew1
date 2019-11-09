package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.TransporterDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TransporterDetailsAdapter extends RecyclerView.Adapter<TransporterDetailsHolder>  {

    private Context mContext;
    public String id;
    private static int currentPosition = 0;


    private ArrayList<TransporterDetails> mList;

    public TransporterDetailsAdapter(Context mContext, ArrayList<TransporterDetails> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public TransporterDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_transporter_details, parent, false);
            return new TransporterDetailsHolder(mView);


    }

    @Override
    public void onBindViewHolder(TransporterDetailsHolder holder, final int position) {
              holder.transporterId.setText(mList.get(position).getId());
                     holder.transporterType.setText(mList.get(position).getVehicleType());
                     holder.transporterNo.setText(mList.get(position).getVehicleNo());
                     holder.transporterRate.setText(mList.get(position).getRate());

    }



    @Override
    public int getItemCount() {
        return  mList.size();
    }


}

class TransporterDetailsHolder extends RecyclerView.ViewHolder{


    TextView transporterId,transporterType,transporterNo,transporterRate;

    List<TransporterDetails> mlist;


    TransporterDetailsHolder(View itemView) {
        super(itemView);


        transporterId = itemView.findViewById(R.id.tv_transporterId);
        transporterType = itemView.findViewById(R.id.tv_transporterType);
        transporterNo = itemView.findViewById(R.id.tv_transporterNo);
        transporterRate = itemView.findViewById(R.id.tv_transporterRate);

    }
}
