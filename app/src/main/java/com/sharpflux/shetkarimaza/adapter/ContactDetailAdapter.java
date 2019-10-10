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
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactDetailAdapter extends RecyclerView.Adapter<ContactDetaiViewHolder> {

    private Context mContext;
    private List<ContactDetail> mlist;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static int currentPosition = 0;

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
       Picasso.get().load(mlist.get(position).getImage()).resize(300, 300).into(holder.mImage);
        holder.mfullname.setText(mlist.get(position).getFullName());
        holder.mAddress.setText(mlist.get(position).getAddress());
        holder.mMobNo.setText(mlist.get(position).getMobileNo());

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

   /* @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
*/
}

class ContactDetaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImage;
    TextView mfullname,mAddress,mMobNo;

    ContactDetaiViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.img_contact_detail);
        mfullname = itemView.findViewById(R.id.tvName);
        mAddress = itemView.findViewById(R.id.tvAddress);
        mMobNo = itemView.findViewById(R.id.tv_MobNo);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*Context context=v.getContext();
        Intent intent;
        intent =  new Intent(context, ProductDetailsForBuyerActivity.class);
        context.startActivity(intent);*/
       Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("8605121954"));
        v.getContext().startActivity(callIntent);
    }
}