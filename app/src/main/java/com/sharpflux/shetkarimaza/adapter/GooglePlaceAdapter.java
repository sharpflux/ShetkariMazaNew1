package com.sharpflux.shetkarimaza.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.model.GooglePlaceModel;

import java.util.ArrayList;

public class GooglePlaceAdapter extends BaseAdapter {

    Context context;
    ArrayList<GooglePlaceModel> arrayList;

    public GooglePlaceAdapter(Context context, ArrayList<GooglePlaceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.place_list, parent, false);
        }
        TextView name,tvAddressDetail;
        name = (TextView) convertView.findViewById(R.id.name);
        tvAddressDetail = (TextView) convertView.findViewById(R.id.tvAddressDetail);
        name.setText(arrayList.get(position).getPlaceName());
        tvAddressDetail.setText(arrayList.get(position).getSecondary_text());
        return convertView;
    }
}