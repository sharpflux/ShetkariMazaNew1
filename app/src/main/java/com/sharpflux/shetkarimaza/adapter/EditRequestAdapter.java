package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditRequestAdapter extends RecyclerView.Adapter<EditRequesViewHolder> {

    private Context mContext;
    private List<SimilarList> mlist;

    private static int currentPosition = 0;

    public EditRequestAdapter(Context mContext, List<SimilarList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public EditRequesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_request_view, parent, false);
        return new EditRequesViewHolder(mView) {
            @Override
            public void onClick(View view) {

            }
        };
    }

    @Override
    public void onBindViewHolder(final EditRequesViewHolder holder,final int currentPosition) {

        if (!mlist.get(currentPosition).getImageUrl().equals(""))
        Picasso.get().load(mlist.get(currentPosition).getImageUrl()).resize(300, 300).into(holder.mImage);
        holder.mName.setText(mlist.get(currentPosition).getName());
        holder.mvarity.setText(mlist.get(currentPosition).getVarietyName());
        holder.mQuality.setText(String.valueOf(  mlist.get(currentPosition).getQuality()));
        holder.Imageupload=mlist.get(currentPosition).getImageUrl();

        holder.tvFarmerAddress.setText(mlist.get(currentPosition).getFarm_address() + ", " + mlist.get(currentPosition).getState() + ", " + mlist.get(currentPosition).getTaluka());

        double priceperunit=0.00;
        double quant=0.00;

        try {
            quant = new Double(mlist.get(currentPosition).getQuantity());
        } catch (NumberFormatException e) {
            quant = 0; // your default value
        }
        //quant = Double.parseDouble(edtAQuantity.getText().toString());


        //String expectedPrice = edtExpectedPrice.getText().toString();

        try {
            priceperunit = new Double(mlist.get(currentPosition).getPrice());
        } catch (NumberFormatException e) {
            priceperunit = 0; // your default value
        }
        //   priceperunit = Double.parseDouble(edtExpectedPrice.getText().toString());

        double total = quant * priceperunit;
        holder.mPrice.setText(Double.toString(total));




        //holder.mPrice.setText(String.valueOf( mlist.get(currentPosition).getPrice()));

        if(mlist.get(currentPosition).getVarietyName().equals("") )
            holder.mvarity.setText("Not Available");
        if( mlist.get(currentPosition).getQuality().equals(""))
            holder.mQuality.setText("Not Available");

      holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
                Intent i= new Intent(context, ProductInfoForSaleActivity.class);
                i.putExtra("Type","flag");
                i.putExtra("ImageUrl",mlist.get(currentPosition).getImageUrl());
                i.putExtra("ItemName",mlist.get(currentPosition).getName());
                i.putExtra("VarietyName",mlist.get(currentPosition).getVarietyName());
                i.putExtra("QualityType",mlist.get(currentPosition).getQuality());
                i.putExtra("AvailableQuantity",mlist.get(currentPosition).getQuantity());
                i.putExtra("MeasurementType",mlist.get(currentPosition).getUnit());
                i.putExtra("ExpectedPrice",mlist.get(currentPosition).getPrice());
                i.putExtra("AvailableMonths",mlist.get(currentPosition).getAvailable_month());
                i.putExtra("FarmAddress",mlist.get(currentPosition).getFarm_address());
                i.putExtra("StatesName",mlist.get(currentPosition).getState());
                i.putExtra("DistrictName",mlist.get(currentPosition).getDistrict());
                i.putExtra("TalukaName",mlist.get(currentPosition).getTaluka());
                i.putExtra("VillageName",mlist.get(currentPosition).getVillage());
                i.putExtra("Hector",mlist.get(currentPosition).getHector());
                i.putExtra("ItemTypeId",mlist.get(currentPosition).getItemTypeId());
                i.putExtra("VarietyId",mlist.get(currentPosition).getVarietyId());
                i.putExtra("QualityId",mlist.get(currentPosition).getQualityId());
                i.putExtra("MeasurementId",mlist.get(currentPosition).getMeasurementId());
                i.putExtra("StateId",mlist.get(currentPosition).getStateId());
                i.putExtra("DistrictId",mlist.get(currentPosition).getDistrictId());
                i.putExtra("TalukaId",mlist.get(currentPosition).getTalukaId());
                i.putExtra("RequstId",mlist.get(currentPosition).getRequstId());
                i.putExtra("ProductId",mlist.get(currentPosition).getCategoryId());
                i.putExtra("SurveyNo",mlist.get(currentPosition).getSurveyNo());
                i.putExtra("BotanicalName",mlist.get(currentPosition).getBotanicalName());
                i.putExtra("Organic",mlist.get(currentPosition).getOrganic());
                i.putExtra("IsEdit",true);
                context.startActivity(i);
            }
        });
        /*holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent i= new Intent(context, HomeActivity.class);
                context.startActivity(i);
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

abstract class EditRequesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImage;
    TextView mName,mvarity,mQuality,mPrice,tvFarmerAddress;
    TextView btn_update,btnCancel;
    String ItemName,VarietyName,QualityType,AvailableQuantity,
            MeasurementType,ExpectedPrice,AvailableMonths,FarmAddress,
            SurveyNo,StatesName,DistrictName,TalukaName,VillageName,Hector,Imageupload;
    EditRequesViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.row_cartlist_ivProImg);
        mName = itemView.findViewById(R.id.row_cartlist_tvName);
        mvarity = itemView.findViewById(R.id.row_cartlist_tvVarity);
        mQuality = itemView.findViewById(R.id.row_cartlist_tvQuality);
        mPrice = itemView.findViewById(R.id.row_cartlist_tvPrice);
        tvFarmerAddress=itemView.findViewById(R.id.tvFarmerAddress);
        btn_update = itemView.findViewById(R.id.btn_update);
       // btnCancel = itemView.findViewById(R.id.btnCancel);
        itemView.setOnClickListener(this);


    }

}
