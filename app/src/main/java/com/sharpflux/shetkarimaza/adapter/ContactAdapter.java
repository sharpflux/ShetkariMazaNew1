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
import com.sharpflux.shetkarimaza.activities.Contacts;
import com.sharpflux.shetkarimaza.model.PostItem;
import com.sharpflux.shetkarimaza.model.Result;
import com.sharpflux.shetkarimaza.model.contacts;
import com.sharpflux.shetkarimaza.utils.PaginationAdapterCallback;
import com.sharpflux.shetkarimaza.viewholder.BaseViewHolder;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<contacts> mPostItems;
    private Context mContext;

    public ContactAdapter(Context mContext,List<contacts> postItems) {
        this.mContext = mContext;
        this.mPostItems = postItems;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ContactAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registration, parent, false));
            case VIEW_TYPE_LOADING:
                return new ContactAdapter.ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_new, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }



    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void addItems(List<contacts> postItems) {
        mPostItems.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        mPostItems.add(new contacts());
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;

        if(position>0) {
            contacts item = getItem(position);
            if (item != null) {
                mPostItems.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void clear() {
        mPostItems.clear();
        notifyDataSetChanged();
    }

    contacts getItem(int position) {
        return mPostItems.get(position);
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView mImage;
        TextView mfullname,mAddress,mMobNo,mState,mDistrict,mTaluka,tvCompanyName,tvPriceDetails,tvRateDescription;
        String name,mobileNo,address,state,district,taluka,ImageUrl;
        LinearLayout linearRateView;


        ViewHolder(View itemView) {
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

        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            contacts item = mPostItems.get(position);
            mfullname.setText(item.getFullName());
            mAddress.setText(item.getAddress());

            Glide.with(mContext).load(URLs.Main_URL+item.getImage())
                    .placeholder(R.drawable.kisanmaza).into(mImage);

            mfullname.setText(item.getFullName());
            mAddress.setText(item.getAddress()+", "
                    + item.getTaluka() + ", "
                    + item.getDistrict() + ", "
                    + item.getState() + "");
            mMobNo.setText(item.getMobileNo());

            mMobNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.getMobileNo(), null));
                    mContext.startActivity(intent);
                }
            });

        }
    }

    public class ProgressHolder extends BaseViewHolder {

        ProgressBar progressBar;

        ProgressHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        protected void clear() {
        }
    }


    private void populateItemRows(ContactAdapter.ViewHolder viewHolder, int position) {
        Glide.with(mContext).load(URLs.Main_URL+mPostItems.get(position).getImage())
                .placeholder(R.drawable.kisanmaza).into(viewHolder.mImage);

        viewHolder.mfullname.setText(mPostItems.get(position).getFullName());
        viewHolder.mAddress.setText(mPostItems.get(position).getAddress()+", "
                + mPostItems.get(position).getTaluka() + ", "
                + mPostItems.get(position).getDistrict() + ", "
                + mPostItems.get(position).getState() + "");
        viewHolder.mMobNo.setText(mPostItems.get(position).getMobileNo());

        viewHolder.mMobNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mPostItems.get(position).getMobileNo(), null));
                mContext.startActivity(intent);
            }
        });


    }

}
