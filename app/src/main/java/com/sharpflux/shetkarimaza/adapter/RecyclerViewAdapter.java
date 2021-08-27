package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    public  List<ContactDetail> mItemList;

    public RecyclerViewAdapter(Context mContext, List<ContactDetail> itemList) {
        this.mContext = mContext;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contact_detail_list, parent, false);
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


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView tvItem;
        TextView mfullname,mAddress,mMobNo,mState,mDistrict,mTaluka,tvCompanyName,tvPriceDetails,tvRateDescription;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvName);
            mImage = itemView.findViewById(R.id.img_contact_detail);
            mfullname = itemView.findViewById(R.id.tvName);
            mAddress = itemView.findViewById(R.id.tvAddress);
            mMobNo = itemView.findViewById(R.id.tv_MobNo);
            mState = itemView.findViewById(R.id.tvState);
            mDistrict = itemView.findViewById(R.id.tvDistrict);
            mTaluka = itemView.findViewById(R.id.tvTaluka);
            tvCompanyName=itemView.findViewById(R.id.tvCompanyName);
            tvPriceDetails=itemView.findViewById(R.id.tvPriceDetails);
            tvRateDescription=itemView.findViewById(R.id.tvRateDescription);
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
        viewHolder.tvItem.setText(mItemList.get(position).getFullName());

        Glide.with(mContext).load(URLs.Main_URL+mItemList.get(position).getImage())
                .placeholder(R.drawable.kisanmaza).into(viewHolder.mImage);

       viewHolder.mfullname.setText(mItemList.get(position).getFullName());
       viewHolder.mAddress.setText(mItemList.get(position).getAddress()+", "
                + mItemList.get(position).getTaluka() + ", "
                + mItemList.get(position).getDistrict() + ", "
                + mItemList.get(position).getState() + "");
        viewHolder.mMobNo.setText(mItemList.get(position).getMobileNo());

        if(mItemList.get(position).getTypeView()!=null) {
           viewHolder.tvCompanyName.setText(mItemList.get(position).getVehicalType());
           viewHolder.tvPriceDetails.setText("₹" + mItemList.get(position).getRates());
        }
        else {
            viewHolder.tvCompanyName.setVisibility(View.GONE);
            viewHolder.tvPriceDetails.setVisibility(View.GONE);
            viewHolder.tvRateDescription.setVisibility(View.GONE);
        }
        viewHolder.mMobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mItemList.get(position).getMobileNo(), null));
                mContext.startActivity(intent);
            }
        });


    }


}