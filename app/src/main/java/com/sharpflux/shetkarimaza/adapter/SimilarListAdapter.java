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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.customviews.CustomDialogViewSimilarList;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SimilarListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    public List<SimilarList> mlist;

    public SimilarListAdapter(Context mContext, List<SimilarList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_similar_list, parent, false);
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
        return mlist == null ? 0 : mlist.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mImage;
        CardView cardView;
        TextView mMobNo;
        TextView textView_viewMore, mName, mfullname, mvarity, mQuality, mPrice, tvFarmerAddress, tvAvailableQty, tvExpectedPrice, tvPriceDetails,tvAvailableInMonth;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_viewMore = itemView.findViewById(R.id.textView_viewMore);
            cardView = itemView.findViewById(R.id.cardView);
            mImage = itemView.findViewById(R.id.row_cartlist_ivProImg);
            mfullname = itemView.findViewById(R.id.row_cartlist_FarmerName);
            mMobNo = itemView.findViewById(R.id.row_cartlist_MobNo);
            mvarity = itemView.findViewById(R.id.row_cartlist_tvVarity);
            tvFarmerAddress = itemView.findViewById(R.id.tvFarmerAddress);
            tvPriceDetails = itemView.findViewById(R.id.tvPriceDetails);
            tvAvailableInMonth=itemView.findViewById(R.id.tvAvailableInMonth);
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

    private void populateItemRows(ItemViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mlist.get(position).getImageUrl()).placeholder(R.drawable.kisanmaza)
                .into(holder.mImage);


        holder.mfullname.setText(mlist.get(position).getFullName());
        holder.mMobNo.setText(mlist.get(position).getMobileNo());

        holder.mvarity.setText(mlist.get(position).getVarietyName());

        holder.tvFarmerAddress.setText(mlist.get(position).getFarm_address() + ", " + mlist.get(position).getState() + ", " + mlist.get(position).getTaluka());

        holder.tvPriceDetails.setText(mlist.get(position).getPerUnitPrice() + " per " + mlist.get(position).getUnit().toString());

        holder.tvAvailableInMonth.setText(mlist.get(position).getAvailable_month());

        holder.mMobNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", holder.mMobNo.getText().toString(), null));
                mContext.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogViewSimilarList customDialogViewSimilarList = new CustomDialogViewSimilarList(mContext, mlist.get(position));
                customDialogViewSimilarList.show();
            }
        });
        holder.textView_viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogViewSimilarList customDialogViewSimilarList = new CustomDialogViewSimilarList(mContext, mlist.get(position));
                customDialogViewSimilarList.show();

            }
        });


    }
}
