package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.filters.FilterActivity;
import com.sharpflux.shetkarimaza.fragment.DynamicFragment;
import com.sharpflux.shetkarimaza.fragment.GroupFragment;
import com.sharpflux.shetkarimaza.fragment.SecondFragment;
import com.sharpflux.shetkarimaza.fragment.SubCategoryFragment;
import com.sharpflux.shetkarimaza.fragment.ThirdFragment;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.model.SubCategory;
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
    String ItemTypeId;
    String Obj,SubCategoryName;
    private BuyerActivity mParent;
    GroupFragment frag;

    public MyGroupAdapter(Context mContext, ArrayList<GroupData> sellOptions, String Obj,GroupFragment frag,String PreviousCategoryId) {
        this.mmContext = mContext;
        this.sellOptions = sellOptions;
        this.PreviousCategoryId=PreviousCategoryId;
        this.Obj = Obj;
        this.frag=frag;
        mParent = new BuyerActivity();

    }

    @Override
    public GroupViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_layout, parent, false);
        return new GroupViewHolder1(mView);

    }

    @Override
    public void onBindViewHolder(GroupViewHolder1 holder, final int i) {


        Picasso.get().load(sellOptions.get(i).getImage()).resize(300, 300).into(holder.Image);
        holder.Title.setText(sellOptions.get(i).getName());
        holder.ItemTypeId = sellOptions.get(i).getItemTypeId();
        holder.PreviousCategoryId = PreviousCategoryId;
        holder.mContext = mmContext;
        holder.jsonObj = Obj;

        holder.lr_group.setOnClickListener(new View.OnClickListener() {
            int position=i;
            public void onClick(View view) {
                frag.GOPrevious( sellOptions.get(position).getItemTypeId(),"True",PreviousCategoryId,sellOptions.get(i).getName());
            }
        });



       /* holder.lr_group.setOnClickListener(new View.OnClickListener() {

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
        });*/
    }


    @Override
    public int getItemCount() {
        return sellOptions.size();
    }


}


class GroupViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView Image;
    TextView Title;
    LinearLayout lr_group;
    String ItemTypeId, PreviousCategoryId, jsonObj;
    Context mContext;



    GroupViewHolder1(View itemView) {
        super(itemView);
        Image = itemView.findViewById(R.id.ivsellerplantLogo);
        Title = itemView.findViewById(R.id.ivsellerplanttype);
        lr_group = itemView.findViewById(R.id.lr_group);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        //frag.GOPrevious(ItemTypeId,"True",PreviousCategoryId);

/*        Intent intent;
        intent = new Intent(view.getContext(), BuyerActivity.class);
        intent.putExtra("ProductId",ItemTypeId);
        intent.putExtra("PreviousCategoryId",PreviousCategoryId);
        view.getContext().startActivity(intent);*/

   /*     AppCompatActivity activity = (AppCompatActivity) view.getContext();
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup","True");
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        newFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, newFragment).commit();*/


      //  AppCompatActivity activity = (AppCompatActivity) view.getContext();
       /* DynamicFragment newFragment = new DynamicFragment();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("CategoryId", ItemTypeId);
        bundle.putString("PreviousCategoryId", PreviousCategoryId);
        newFragment.setArguments(bundle);
        transaction.replace(R.id.frame, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/


        /*activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        DynamicFragment frag = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CategoryId", ItemTypeId);
        bundle.putString("PreviousCategoryId", PreviousCategoryId);
        frag.setArguments(bundle);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, frag, "Dynamic");
        fragmentTransaction.addToBackStack("Dynamic");
        fragmentTransaction.commit();*/


        /*AppCompatActivity activity = (AppCompatActivity) view.getContext();
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup","True");
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        newFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, newFragment).addToBackStack(null).commit();*/

       /* SubCategoryFragment newFragment = new SubCategoryFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        newFragment.setArguments(bundle);
        String backStateName = newFragment.getClass().getName();
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame, newFragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commit();*/




         /*AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment newFragment,newFragment1,newFragment2;
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup","True");
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
            switch (view.getId()){
                case 7:
                    newFragment = new DynamicFragment();
                    newFragment.setArguments(bundle);
                    ft.replace(R.id.frame, newFragment);
                    ft.addToBackStack(null);
                    ft.commit();

                   */
         /* ft.replace(R.id.frame, newFragment, backStateName);
                    ft.addToBackStack(backStateName);
                    ft.commit();*/


    }

}





