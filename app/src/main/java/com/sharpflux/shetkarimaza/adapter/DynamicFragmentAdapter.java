package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.fragment.DynamicFragment;
import com.sharpflux.shetkarimaza.model.SellOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    public int mNumOfTabs;
    public JSONArray arrayObj ;
    List<SellOptions> productlist;
    RecyclerView mRecyclerView;
    Context context;

    public DynamicFragmentAdapter(FragmentManager fm, int NumOfTabs, JSONArray array, Context ctx) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        arrayObj=array;
        this.context=ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);

        try {
            JSONObject jObject = arrayObj.getJSONObject(position);
            b.putString("CategoryName_EN", jObject.getString("CategoryName_EN"));;
            b.putString("CategoryId",jObject.getString("CategoryId"));
            b.putBoolean("IsVarietyApplicable",jObject.getBoolean("IsVarietyApplicable"));
            b.putBoolean("IsQuality",jObject.getBoolean("IsQuality"));
            b.putBoolean("IsAge",jObject.getBoolean("IsAge"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Fragment frag = DynamicFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
