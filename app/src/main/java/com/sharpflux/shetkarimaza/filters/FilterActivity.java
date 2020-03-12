package com.sharpflux.shetkarimaza.filters;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.sharpflux.shetkarimaza.R;


public class FilterActivity extends AppCompatActivity {

    Bundle bundle;
    String itemTypeId, categoryId, Variety, ItemName;
    Boolean IsVarietyAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        bundle = getIntent().getExtras();

        if (bundle != null) {

            itemTypeId = bundle.getString("ItemTypeId");
            categoryId = bundle.getString("ProductId");
            ItemName = bundle.getString("ItemName");
            IsVarietyAvailable = bundle.getBoolean("IsVarietyAvailable");

        }
        setTitle(ItemName);
        if (categoryId.equals("4") || categoryId.equals("3") || categoryId.equals("10") || categoryId.equals("43")) {

            StateFragment vfm = new StateFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            Bundle b = new Bundle();
            if (b != null) {
                b.putString("ItemTypeId", itemTypeId);
                b.putString("ProductId", categoryId);
                b.putString("ItemName", ItemName);
            }

            vfm.setArguments(b);
            fragmentTransaction.add(R.id.dynamic_fragment_frame_layout_variety, vfm);
            fragmentTransaction.commit();

        } else if (IsVarietyAvailable) {

            VarietyFragment vfm = new VarietyFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            Bundle b = new Bundle();
            if (b != null) {
                b.putString("ItemTypeId", itemTypeId);
                b.putString("ProductId", categoryId);
                b.putString("ItemName", ItemName);
            }

            vfm.setArguments(b);
            fragmentTransaction.add(R.id.dynamic_fragment_frame_layout_variety, vfm);
            fragmentTransaction.commit();
        } else if (categoryId.equals("6")) {
            AgeFragment vfm = new AgeFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            Bundle b = new Bundle();
            if (b != null) {
                b.putString("ItemTypeId", itemTypeId);
                b.putString("ProductId", categoryId);
                b.putString("ItemName", ItemName);
            }

            vfm.setArguments(b);
            fragmentTransaction.add(R.id.dynamic_fragment_frame_layout_variety, vfm);
            fragmentTransaction.commit();
        } else {
            QualityFragment vfm = new QualityFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            Bundle b = new Bundle();
            if (b != null) {
                b.putString("ItemTypeId", itemTypeId);
                b.putString("ProductId", categoryId);
                b.putString("ItemName", ItemName);
            }

            vfm.setArguments(b);
            fragmentTransaction.add(R.id.dynamic_fragment_frame_layout_variety, vfm);
            fragmentTransaction.commit();
        }


    }

}