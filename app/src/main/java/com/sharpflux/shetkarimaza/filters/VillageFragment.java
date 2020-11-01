package com.sharpflux.shetkarimaza.filters;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sharpflux.shetkarimaza.R;

public class VillageFragment extends Fragment {
    Button btn_next, btn_back, btnFilterData;
    Bundle extras;
    String StatesID = "", DistrictId = "", TalukaId = "", VarityId = "", QualityId = "", itemTypeId = "", priceids = "",ItemName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_village, container, false);
        btn_next = view.findViewById(R.id.btn_nextVillage);
        btn_back = view.findViewById(R.id.btn_backVillage);
        btnFilterData = view.findViewById(R.id.btnFilterData);


        extras = getArguments();

        if (extras != null) {
            TalukaId = extras.getString("TalukaId");
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId = extras.getString("ItemTypeId");
            DistrictId = extras.getString("DistrictId");
            StatesID = extras.getString("StatesID");
            ItemName = extras.getString("ItemName");

        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("DistrictId", DistrictId);
                    extras.putString("TalukaId", TalukaId);
                    extras.putString("ItemName", ItemName);
                }

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                TalukaFragment mfragment = new TalukaFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("DistrictId", DistrictId);
                    extras.putString("TalukaId", TalukaId);
                    extras.putString("ItemName", ItemName);
                }
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                PriceFragment mfragment = new PriceFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();


            }
        });
        return view;
    }
}
