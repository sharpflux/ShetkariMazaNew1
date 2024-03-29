package com.sharpflux.shetkarimaza.filters;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class PriceFragment extends Fragment {

    StringBuilder priceids_builder_id;

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<SubCategoryFilter> productlist;
    TextView btn_next,btn_back;
    String   StatesID="",DistrictId="",TalukaId="",VarityId="",QualityId="",itemTypeId="",priceids="",ItemName="",categoryId="";
    Bundle extras;
    dbBuyerFilter myDatabase;
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

        myDatabase = new dbBuyerFilter(getContext());


        extras = getArguments();

        if (extras != null) {
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId=extras.getString("ItemTypeId");
            DistrictId=extras.getString("DistrictId");
            StatesID=extras.getString("StatesID");
            TalukaId = extras.getString("TalukaId");
            ItemName = extras.getString("ItemName");
            categoryId = extras.getString("categoryId");
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* Cursor VARIETYCursor = myDatabase.FilterGetByFilterName("VARIETY");
                Cursor QUALITYCursor = myDatabase.FilterGetByFilterName("QUALITY");
                Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");*/
                Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");




                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("DistrictId", DistrictId);
                    extras.putString("TalukaId", TalukaId);
                    extras.putString("ItemName", ItemName);
                    extras.putString("categoryId", categoryId);
                    extras.putString("Search", "Filter");
                }


                if(TALUKACursor.getCount()==0&&DISTRICTCursor.getCount()==0) {
                    FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                    StateFragment mfragment = new StateFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();
                }
                else if(TALUKACursor.getCount()==0){
                    FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                    DistrictFragment mfragment = new DistrictFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();
                }
                /*else {
                    FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                    VillageFragment mfragment = new VillageFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();
                }*/

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
                intent.putExtra("ItemName",ItemName);
                intent.putExtra("Search", "Filter");
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
