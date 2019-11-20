package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.Interface.RecyclerViewClickListener;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.filters.FilterActivity;
import com.sharpflux.shetkarimaza.fragment.DynamicFragment;
import com.sharpflux.shetkarimaza.fragment.GroupFragment;
import com.sharpflux.shetkarimaza.fragment.ThirdFragment;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGroupAdapter extends RecyclerView.Adapter<GroupViewHolder1> {

    private Context mmContext;
    private ArrayList<GroupData> sellOptions;
    RecyclerViewClickListener rv;
    String PreviousCategoryId;
    String Obj;

    public MyGroupAdapter(Context mContext, ArrayList<GroupData> sellOptions,String PreviousCategoryId,String Obj) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
        this.PreviousCategoryId=PreviousCategoryId;
        this.Obj=Obj;

    }

    @Override
    public GroupViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_layout, parent, false);
            return new GroupViewHolder1(mView);

    }

    @Override
    public void onBindViewHolder(GroupViewHolder1 holder, int i) {
        Picasso.get().load(sellOptions.get(i).getImage()).resize(300, 300)
                .into(holder.Image);
        holder.Title.setText(sellOptions.get(i).getName());
        holder.ItemTypeId = sellOptions.get(i).getTypeId();
        holder.PreviousCategoryId=PreviousCategoryId;
        holder.jsonObj=Obj;
    }


    @Override
    public int getItemCount() {
        return sellOptions.size();
    }



}


class GroupViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView Image;
    TextView Title;

    String ItemTypeId,PreviousCategoryId,jsonObj;


    GroupViewHolder1(View itemView) {
        super(itemView);
        Image = itemView.findViewById(R.id.ivsellerplantLogo);
        Title = itemView.findViewById(R.id.ivsellerplanttype);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup","True");
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        newFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, newFragment).addToBackStack(null).commit();




    }

}
