package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;

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
