package com.sharpflux.shetkarimaza.filters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;

import java.util.ArrayList;
import java.util.List;


public class PriceFragment extends Fragment {

    StringBuilder priceids_builder_id;

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<SubCategoryFilter> productlist;
    Button btn_next,btn_back;
    String   StatesID="",DistrictId="",TalukaId="",VarityId="",QualityId="",itemTypeId="",priceids="";
    Bundle extras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_price, container, false);

        recyclerView = view.findViewById(R.id.rcv_price);
        btn_next = view.findViewById(R.id.btnnextPrice);
        btn_back = view.findViewById(R.id.btnbackPrice);

        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        priceids_builder_id = new StringBuilder();




        extras = getArguments();

        if (extras != null) {
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId=extras.getString("ItemTypeId");
            DistrictId=extras.getString("DistrictId");
            StatesID=extras.getString("StatesID");
            TalukaId = extras.getString("TalukaId");

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
                }

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                VillageFragment mfragment = new VillageFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < productlist.size(); i++) {
                    SubCategoryFilter filter = productlist.get(i);
                    if (filter.getSelected()) {
                        priceids_builder_id.append(filter.getId());
                        priceids = priceids + filter.getId();

                    }
                }


                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);

                intent.putExtra("VarietyId",VarityId);
                intent.putExtra("QualityId",QualityId);
                intent.putExtra("ItemTypeId",itemTypeId);
                intent.putExtra("DistrictId",DistrictId);
                intent.putExtra("StatesID",StatesID);
                intent.putExtra("TalukaId",TalukaId);
                intent.putExtra("priceids",priceids);
                startActivity(intent);
            }
        });



        productlist.add(new SubCategoryFilter("ASC","Price low to high"));
        productlist.add(new SubCategoryFilter("DESC","Price high to low"));

       VarietyAdapter myAdapter = new VarietyAdapter(getContext(), productlist);
        recyclerView.setAdapter(myAdapter);


        return view;
    }

}
