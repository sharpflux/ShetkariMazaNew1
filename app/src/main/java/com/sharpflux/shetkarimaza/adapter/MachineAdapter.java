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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.activities.GovtDepartmentActivity;
import com.sharpflux.shetkarimaza.activities.MachinHireActivity;
import com.sharpflux.shetkarimaza.activities.Membership;
import com.sharpflux.shetkarimaza.activities.Scroll;
import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.activities.TransporterVehicleAddActivity;
import com.sharpflux.shetkarimaza.activities.TransporterViewActivity;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.model.CouponModel;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.ArrayList;
import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {
    private Context mContext;
    public String id;
    private static int currentPosition = 0;


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private ArrayList<MyCategoryType> mList;


    public MachineAdapter(Context mContext, ArrayList<MyCategoryType> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_merchant, parent, false);
            return new ViewHolder(mView);
        } else {
            View nview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new ViewHolder(nview);
        }

    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        View view;
        String categoryId = "";

        if (holder instanceof ViewHolder)
            try {

                Glide.with(mContext)  .load(URLs.Main_URL+mList.get(position).getImage()).placeholder(R.drawable.kisanmaza).into(holder.mImage);
                ((ViewHolder) holder).categoryId = mList.get(position).getCategoryTypeId();
                ((ViewHolder) holder).mTitle.setText(mList.get(position).getCategoryTypeName());
                ((ViewHolder) holder).CategoryName=mList.get(position).getCategoryTypeName();
                ((ViewHolder) holder).ItemTypeId=mList.get(position).getUserRegistrationTypeId();

            } catch (Exception d) {

            }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class LoadingViewHolderSeller1 extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolderSeller1(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }


    }

    /* renamed from: ws.design.dailygrocery.adapter.CouponAdapter$ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImage;
        TextView mTitle;
        String categoryId = "",CategoryName="",ItemTypeId;
        List<SubCategoryFilter> mlist;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, Scroll.class);
            intent.putExtra("ProductId", ItemTypeId);
            intent.putExtra("RegistrationSubTypeId", categoryId);
            intent.putExtra("IsSubCategory", true);
            intent.putExtra("Name", CategoryName);
            intent.putExtra("Heading", mTitle.getText().toString());
            context.startActivity(intent);
        }
    }



}
