package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListViewHolder> {

    private Context mContext;
    private List<SimilarList> mlist;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static int currentPosition = 0;

    public SimilarListAdapter(Context mContext, List<SimilarList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public SimilarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_similar_list, parent, false);
        return new SimilarListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SimilarListViewHolder holder, final int position) {
        Picasso.get().load(mlist.get(position).getImageUrl()).resize(300, 300).into(holder.mImage);
        holder.mfullname.setText(mlist.get(position).getFullName());
        holder.mMobNo.setText(mlist.get(position).getMobileNo());
        holder.mName.setText(mlist.get(position).getName());
        holder.mvarity.setText(mlist.get(position).getVarietyName());
        holder.mQuality.setText(mlist.get(position).getQuality().toString());
        holder.mPrice.setText(String.valueOf( mlist.get(position).getPrice()));

        holder.mMobNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",  holder.mMobNo.getText().toString(), null));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return   mlist == null ? 0 : mlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

}

class SimilarListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mMobNo;
    TextView mName,mfullname,mvarity,mQuality,mPrice;

    SimilarListViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.row_cartlist_ivProImg);
        mfullname = itemView.findViewById(R.id.row_cartlist_FarmerName);
        mMobNo = itemView.findViewById(R.id.row_cartlist_MobNo);
        mName = itemView.findViewById(R.id.row_cartlist_tvName);
        mvarity = itemView.findViewById(R.id.row_cartlist_tvVarity);
        mQuality = itemView.findViewById(R.id.row_cartlist_tvQuality);
        mPrice = itemView.findViewById(R.id.row_cartlist_tvPrice);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*Context context=v.getContext();
        Intent intent;
        intent =  new Intent(context, ProductDetailsForBuyerActivity.class);
        context.startActivity(intent);*/
      /*  Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("8605121954"));
        v.getContext().startActivity(callIntent);*/
    }
}/////