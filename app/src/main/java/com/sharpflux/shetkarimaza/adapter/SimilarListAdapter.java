package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.customviews.CustomDialogViewSimilarList;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarListViewHolder> {

    public Context mContext;
    public List<SimilarList> mlist;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static int currentPosition = 0;

    public SimilarListAdapter(Context mContext, List<SimilarList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public SimilarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_similar_list, parent, false);
        return new SimilarListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SimilarListViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(mlist.get(position).getImageUrl()).placeholder(R.drawable.kisanmaza)
                .into(holder.mImage);

        //Picasso.get().load(mlist.get(position).getImageUrl()).resize(300, 300).into(holder.mImage);
        holder.mfullname.setText(mlist.get(position).getFullName());
        holder.mMobNo.setText(mlist.get(position).getMobileNo());
        //holder.mName.setText(mlist.get(position).getName());
        holder.mvarity.setText(mlist.get(position).getVarietyName()); //"VARIETY :"+
        //holder.mQuality.setText("QUALITY :"+mlist.get(position).getQuality().toString());
        //holder.mPrice.setText(String.valueOf("₹ "+mlist.get(position).getPrice()));
        holder.tvFarmerAddress.setText(mlist.get(position).getFarm_address()+", "+mlist.get(position).getState()+", "+mlist.get(position).getTaluka());

        //holder.tvAvailableQty.setText(mlist.get(position).getQuantity().toString());
        //holder.tvExpectedPrice.setText( String.valueOf(Double.valueOf(mlist.get(position).getPrice())/ Double.valueOf(mlist.get(position).getQuantity())  ));
        holder.tvPriceDetails.setText(mlist.get(position).getUnit().toString());
//"Quantity avaialable in "
        holder.mMobNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",  holder.mMobNo.getText().toString(), null));
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

    CircleImageView mImage;
    CardView cardView;
    TextView mMobNo;
    TextView textView_viewMore,mName,mfullname,mvarity,mQuality,mPrice,tvFarmerAddress,tvAvailableQty,tvExpectedPrice,tvPriceDetails;

    SimilarListViewHolder(View itemView) {
        super(itemView);


        textView_viewMore = itemView.findViewById(R.id.textView_viewMore);
        cardView = itemView.findViewById(R.id.cardView);
        mImage = itemView.findViewById(R.id.row_cartlist_ivProImg);
        mfullname = itemView.findViewById(R.id.row_cartlist_FarmerName);
        mMobNo = itemView.findViewById(R.id.row_cartlist_MobNo);
        //mName = itemView.findViewById(R.id.row_cartlist_tvName);
        mvarity = itemView.findViewById(R.id.row_cartlist_tvVarity);
        //mQuality = itemView.findViewById(R.id.row_cartlist_tvQuality);
        //mPrice = itemView.findViewById(R.id.row_cartlist_tvPrice);
        tvFarmerAddress=itemView.findViewById(R.id.tvFarmerAddress);
        //tvAvailableQty=itemView.findViewById(R.id.tvAvailableQty);
        //tvExpectedPrice=itemView.findViewById(R.id.tvExpectedPrice);
        tvPriceDetails=itemView.findViewById(R.id.tvPriceDetails);
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