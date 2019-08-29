package com.sharpflux.shetkarimaza.filters;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sharpflux.shetkarimaza.R;


public class FilterActivity extends AppCompatActivity {

    Bundle bundle;
    String itemTypeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);



        bundle=getIntent().getExtras();

        if(bundle!=null) {

            itemTypeId=bundle.getString("ItemTypeId");


        }


        VarietyFragment vfm =new VarietyFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

        Bundle b = new Bundle();
        if (b != null) {
            b.putString("ItemTypeId", itemTypeId);
            // Log.e("IDS",districtIds);
        }

        vfm.setArguments(b);
        fragmentTransaction.add(R.id.dynamic_fragment_frame_layout_variety, vfm);
        fragmentTransaction.commit();




    }

}