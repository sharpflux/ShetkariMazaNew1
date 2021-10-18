package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.Membership;
import com.sharpflux.shetkarimaza.model.CouponModel;

import java.util.ArrayList;

/* renamed from: ws.design.dailygrocery.adapter.CouponAdapter */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    Context context;
    ArrayList<CouponModel> models;
    int myPos = -1;

    public CouponAdapter(Context context2, ArrayList<CouponModel> models2) {
        this.context = context2;
        this.models = models2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txt_CouponDiscount.setText("₹ "+this.models.get(position).getTxt_CouponDiscount());
      //  holder.txt_CouponCode.setText(this.models.get(position).getTxt_CouponCode());
        holder.txt_CouponTitle.setText(this.models.get(position).getTxt_Descriptions());
        if (this.myPos == position) {
            holder.li_Coupon.setBackgroundResource(R.drawable.rect_dotted_coupon_selected);
        } else {
            holder.li_Coupon.setBackgroundResource(R.drawable.rect_dotted_coupon_unselected);
        }
        holder.li_Coupon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 CouponAdapter.this.myPos = position;
                    CouponAdapter.this.notifyDataSetChanged();
            }
        });

        holder.txt_CouponCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CouponAdapter.this.myPos = position;
                ((Membership)context).orderAPI(models.get(position).getTxt_CouponDiscount().toString(),models.get(position).getPackageId().toString());
            }
        });
    }






    public int getItemCount() {
        return this.models.size();
    }

    /* renamed from: ws.design.dailygrocery.adapter.CouponAdapter$ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout li_Coupon;
        TextView txt_CouponCode;
        TextView txt_CouponDiscount;
        TextView txt_CouponTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_CouponDiscount = (TextView) itemView.findViewById(R.id.txt_CouponDiscount);
            this.txt_CouponCode = (TextView) itemView.findViewById(R.id.txt_CouponCode);
            this.txt_CouponTitle = (TextView) itemView.findViewById(R.id.txt_CouponTitle);
            this.li_Coupon = (LinearLayout) itemView.findViewById(R.id.li_Coupon);
        }
    }
}
