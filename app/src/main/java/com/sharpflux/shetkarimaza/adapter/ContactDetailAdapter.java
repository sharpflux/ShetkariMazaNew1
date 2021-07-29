package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ProductDetailsForBuyerActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ContactDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<ContactDetail> mlist;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    TextView txt_emptyView;


    public ContactDetailAdapter(Context mContext, List<ContactDetail> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contact_detail_list, parent, false);
            return new ContactDetaiViewHolder(mView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolderSeller(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContactDetaiViewHolder)
            try {
                //  Picasso.get().load(URLs.Main_URL+mlist.get(position).getImage()).resize(300, 300).into(holder.mImage);
                Glide.with(mContext).load(URLs.Main_URL+mlist.get(position).getImage())
                        .placeholder(R.drawable.kisanmaza).into(((ContactDetaiViewHolder) holder).mImage);

                ((ContactDetaiViewHolder) holder).mfullname.setText(mlist.get(position).getFullName());
                ((ContactDetaiViewHolder) holder).mAddress.setText(mlist.get(position).getAddress()+", "
                        + mlist.get(position).getTaluka() + ", "
                        + mlist.get(position).getDistrict() + ", "
                        + mlist.get(position).getState() + "");
                ((ContactDetaiViewHolder) holder).mMobNo.setText(mlist.get(position).getMobileNo());

                if(mlist.get(position).getTypeView()!=null) {
                    ((ContactDetaiViewHolder) holder).tvCompanyName.setText(mlist.get(position).getVehicalType());
                    ((ContactDetaiViewHolder) holder).tvPriceDetails.setText("₹" + mlist.get(position).getRates());
                }
                else {
                    ((ContactDetaiViewHolder) holder).tvCompanyName.setVisibility(View.GONE);
                    ((ContactDetaiViewHolder) holder).tvPriceDetails.setVisibility(View.GONE);
                    ((ContactDetaiViewHolder) holder).tvRateDescription.setVisibility(View.GONE);
                }
            } catch (Exception d) {

            }
        else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolderSeller) holder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    private void showLoadingView(LoadingViewHolderSeller viewHolder, int position) {
        //ProgressBar would be displayed

    }

}


class ContactDetaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImage;
    TextView mfullname,mAddress,mMobNo,mState,mDistrict,mTaluka,tvCompanyName,tvPriceDetails,tvRateDescription;
    String name,mobileNo,address,state,district,taluka,ImageUrl;
    LinearLayout linearRateView;
    ContactDetaiViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.img_contact_detail);
        mfullname = itemView.findViewById(R.id.tvName);
        mAddress = itemView.findViewById(R.id.tvAddress);
        mMobNo = itemView.findViewById(R.id.tv_MobNo);
        mState = itemView.findViewById(R.id.tvState);
        mDistrict = itemView.findViewById(R.id.tvDistrict);
        mTaluka = itemView.findViewById(R.id.tvTaluka);
        tvCompanyName=itemView.findViewById(R.id.tvCompanyName);
        tvPriceDetails=itemView.findViewById(R.id.tvPriceDetails);
        linearRateView=itemView.findViewById(R.id.linearRateView);
        tvRateDescription=itemView.findViewById(R.id.tvRateDescription);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Context context = v.getContext();
        Intent intent;
        intent = new Intent(context, ProductInfoForSaleActivity.class);
        intent.putExtra("ProductId", "0");
        context.startActivity(intent);

    }


}

class LoadingViewHolderTranporter extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolderTranporter(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}

/*
public class ContactDetailAdapter extends RecyclerView.Adapter<ContactDetaiViewHolder> {

    private Context mContext;
    private List<ContactDetail> mlist;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static int currentPosition = 0;
    TextView txt_emptyView;

    public ContactDetailAdapter(Context mContext, List<ContactDetail> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;

    }

    @Override
    public ContactDetaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contact_detail_list, parent, false);
        return new ContactDetaiViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ContactDetaiViewHolder holder, final int position) {
     //  Picasso.get().load(URLs.Main_URL+mlist.get(position).getImage()).resize(300, 300).into(holder.mImage);
        Glide.with(mContext).load(URLs.Main_URL+mlist.get(position).getImage())
                .placeholder(R.drawable.kisanmaza).into(holder.mImage);

        holder.mfullname.setText(mlist.get(position).getFullName());
        holder.mAddress.setText(mlist.get(position).getAddress()+", "
                + mlist.get(position).getTaluka() + ", "
                + mlist.get(position).getDistrict() + ", "
                + mlist.get(position).getState() + "");
        holder.mMobNo.setText(mlist.get(position).getMobileNo());

        if(mlist.get(position).getTypeView()!=null) {
            holder.tvCompanyName.setText(mlist.get(position).getVehicalType());
            holder.tvPriceDetails.setText("₹" + mlist.get(position).getRates());
        }
        else {
            holder.tvCompanyName.setVisibility(View.GONE);
            holder.tvPriceDetails.setVisibility(View.GONE);
            holder.tvRateDescription.setVisibility(View.GONE);
        }

//        holder.mState.setText(mlist.get(position).getState()+",");
      //  holder.mDistrict.setText(mlist.get(position).getDistrict()+",");
    //    holder.mTaluka.setText(mlist.get(position).getTaluka());
        holder.ImageUrl=URLs.Main_URL+mlist.get(position).getImage();
       */
/* holder.mMobNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",  holder.mMobNo.getText().toString(), null));
                mContext.startActivity(intent);
            }
        });*//*

    }

    @Override
    public int getItemCount() {
        return   mlist == null ? 0 : mlist.size();
    }

   */
/* @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
*//*

}

class ContactDetaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mfullname,mAddress,mMobNo,mState,mDistrict,mTaluka,tvCompanyName,tvPriceDetails,tvRateDescription;
    String name,mobileNo,address,state,district,taluka,ImageUrl;
    LinearLayout linearRateView;
    ContactDetaiViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.img_contact_detail);
        mfullname = itemView.findViewById(R.id.tvName);
        mAddress = itemView.findViewById(R.id.tvAddress);
        mMobNo = itemView.findViewById(R.id.tv_MobNo);
        mState = itemView.findViewById(R.id.tvState);
        mDistrict = itemView.findViewById(R.id.tvDistrict);
        mTaluka = itemView.findViewById(R.id.tvTaluka);
        tvCompanyName=itemView.findViewById(R.id.tvCompanyName);
        tvPriceDetails=itemView.findViewById(R.id.tvPriceDetails);
        linearRateView=itemView.findViewById(R.id.linearRateView);
        tvRateDescription=itemView.findViewById(R.id.tvRateDescription);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name=mfullname.getText().toString();
        mobileNo=mMobNo.getText().toString();
        address=mAddress.getText().toString();
      //  state=mState.getText().toString();
      //  district=mDistrict.getText().toString();
     ///   taluka=mTaluka.getText().toString();


        Context context = v.getContext();
        Intent intent;
        intent = new Intent(context, ProductDetailsForBuyerActivity.class);
        intent.putExtra("Name", name);
        intent.putExtra("MobileNo", mobileNo);
        intent.putExtra("Address", address);
       // intent.putExtra("State", state);
      //  intent.putExtra("District", district);
      //  intent.putExtra("Taluka", taluka);
        intent.putExtra("ImageUrl", ImageUrl);
        context.startActivity(intent);

       */
/*Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("8605121954"));
        v.getContext().startActivity(callIntent);*//*

    }
}*/
