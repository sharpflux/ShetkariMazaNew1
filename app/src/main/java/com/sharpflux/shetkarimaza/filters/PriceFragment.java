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

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<SubCategoryFilter> productlist;
    Button btn_next,btn_back;
    String   TalukaId="",VarityId="",QualityId="",itemTypeId="";
    Bundle extras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_price, container, false);

        recyclerView = view.findViewById(R.id.rcv_vriety);
        btn_next = view.findViewById(R.id.btnnextPrice);
        btn_back = view.findViewById(R.id.btnbackPrice);

        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        extras = getArguments();

        if (extras != null) {
            TalukaId = extras.getString("TalukaId");
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId=extras.getString("ItemTypeId");

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                TalukaFragment mfragment = new TalukaFragment();
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("TalukaId",TalukaId);
                intent.putExtra("VarietyId",VarityId);
                intent.putExtra("QualityId",QualityId);
                intent.putExtra("ItemTypeId",itemTypeId);
                startActivity(intent);
            }
        });

        ArrayList<SubCategoryFilter> mFlowerList = new ArrayList<>();

        SubCategoryFilter mFlowerData1 = new SubCategoryFilter("Price low to high","Price low to high");
        mFlowerList.add(mFlowerData1);

        SubCategoryFilter mFlowerData2 = new SubCategoryFilter("Price high to low","Price high to low");
        mFlowerList.add(mFlowerData2);

        VarietyAdapter myAdapter = new VarietyAdapter(getContext(), mFlowerList);
        recyclerView.setAdapter(myAdapter);





        return view;
    }








}
