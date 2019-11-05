package com.sharpflux.shetkarimaza.filters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharpflux.shetkarimaza.R;

public class VillageFragment extends Fragment {
    Button btn_next,btn_back,btnFilterData;
    Bundle extras;
    String   StatesID="",DistrictId="",TalukaId="",VarityId="",QualityId="",itemTypeId="",priceids="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_village, container, false);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnFilterData);




        extras = getArguments();

        if (extras != null) {
            TalukaId = extras.getString("TalukaId");
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId=extras.getString("ItemTypeId");
            DistrictId=extras.getString("DistrictId");
            StatesID=extras.getString("StatesID");

        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                TalukaFragment  mfragment = new TalukaFragment ();
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });




        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                extras = new Bundle();

                if (extras != null) {
                    extras.putString("VarietyId",VarityId);
                    extras.putString("QualityId",QualityId);
                    extras.putString("TalukaId",TalukaId);
                    extras.putString("ItemTypeId",itemTypeId);
                    extras.putString("StatesID",StatesID);
                    extras.putString("DistrictId",DistrictId);



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
