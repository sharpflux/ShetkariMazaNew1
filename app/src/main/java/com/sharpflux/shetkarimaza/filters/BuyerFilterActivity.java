package com.sharpflux.shetkarimaza.filters;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.fragment.FirstFragment;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.utils.Constant;

import java.util.ArrayList;


public class BuyerFilterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text;
    ImageView back, menu, bag, search;
    Bundle bundle;
    FrameLayout container;
    ArrayList<SubCategoryFilter> productlist;
    //Every layout and view is mentioned here with which the logic will work on screen....
    LinearLayout brands_linear, category_linear,size_linear, varity_linear,linearAvailableMonth,  quality_linear,age_linear;

    LinearLayout brands_color, category_color, size_color, varity_color, quality_color,month_color,age_color;

    TextView brands_text, category_text, size_text, varity_text, quality_text, colour_text, verify_text, btnFilterData, btnClear,month_text,age_text;
    StringBuilder varity_builder_id;
    dbBuyerFilter myDatabase;
    String ItemName="",ItemTypeId = "",TalukaId = "",VarityId = "",QualityId = "",StatesID = "",DistrictId = "",priceids = "",categoryId="";
    boolean IsVarietyAvailable,IsGroup,IsAge,IsQuality,ShowFirst;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_filter);

        ShowFirst=true;
        btnFilterData = findViewById(R.id.btnFilterData);
        btnClear = findViewById(R.id.btnClear);
        container = findViewById(R.id.container);
        bundle = new Bundle();
        back = findViewById(R.id.back);

       /* text = findViewById(R.id.textcattoolbar);
        menu = findViewById(R.id.menu);
        search = findViewById(R.id.search);
       bag = findViewById(R.id.bag);*/
        productlist = new ArrayList<>();
        varity_builder_id = new StringBuilder();

        //Above mentioned names are connected here with XML id's....
        brands_linear = findViewById(R.id.states_linear);
        category_linear = findViewById(R.id.District_linear);
        size_linear = findViewById(R.id.taluka_linear);
        varity_linear = findViewById(R.id.varity_linear);
        quality_linear = findViewById(R.id.quality_linear);
        age_linear = findViewById(R.id.age_linear);
        linearAvailableMonth= findViewById(R.id.linearAvailableMonth);

        brands_color = findViewById(R.id.brands_color);
        category_color = findViewById(R.id.category_color);
        size_color = findViewById(R.id.size_color);
        varity_color = findViewById(R.id.varity_color);
        quality_color = findViewById(R.id.quality_color);
        age_color = findViewById(R.id.age_color);
        month_color = findViewById(R.id.month_color);

        brands_text = findViewById(R.id.brands_text);
        category_text = findViewById(R.id.category_text);
        size_text = findViewById(R.id.size_text);
        varity_text = findViewById(R.id.varity_text);
        quality_text = findViewById(R.id.quality_text);
        age_text = findViewById(R.id.age_text);
        month_text = findViewById(R.id.month_text);

        brands_linear.setOnClickListener(this);
        category_linear.setOnClickListener(this);
        size_linear.setOnClickListener(this);
        varity_linear.setOnClickListener(this);
        quality_linear.setOnClickListener(this);
        age_linear.setOnClickListener(this);
        linearAvailableMonth.setOnClickListener(this);


        varity_color.setVisibility(View.GONE);
        quality_color.setVisibility(View.INVISIBLE);
        category_color.setVisibility(View.INVISIBLE);
        size_color.setVisibility(View.INVISIBLE);
        brands_color.setVisibility(View.INVISIBLE);
        month_color.setVisibility(View.INVISIBLE);
        age_color.setVisibility(View.INVISIBLE);

        quality_linear.setVisibility(View.GONE);



        //It is used to already have a selected fragment on the screen
        AppCompatActivity activity;
        activity = BuyerFilterActivity.this;
        Fragment myFragment = new BuyerFilterFragment();


        bundle = getIntent().getExtras();
        // bundle.getString("ItemTypeId");
        if (bundle != null) {
            ItemTypeId = bundle.getString("ItemTypeId");
            TalukaId = bundle.getString("TalukaId");
            VarityId = bundle.getString("VarietyId");
            QualityId = bundle.getString("QualityId");
            StatesID = bundle.getString("StatesID");
            DistrictId = bundle.getString("DistrictId");
            priceids=bundle.getString("priceids");
            IsVarietyAvailable=bundle.getBoolean("IsVarietyAvailable");
            IsAge=bundle.getBoolean("IsAge");
            IsQuality=bundle.getBoolean("IsQuality");
            categoryId=bundle.getString("categoryId");
            ItemName=bundle.getString("ItemName");



            if(IsVarietyAvailable)
            {
                varity_linear.setVisibility(View.VISIBLE);
                varity_color.setVisibility(View.VISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                bundle.putString("Filter",Constant.VARIETY);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                ShowFirst=false;
            }
            else {
                varity_linear.setVisibility(View.GONE);
            }
            if(IsAge)
            {
                age_linear.setVisibility(View.VISIBLE);
                if(ShowFirst) {
                    bundle.putString("Filter", Constant.AGE);
                    varity_color.setVisibility(View.INVISIBLE);
                    quality_color.setVisibility(View.INVISIBLE);
                    brands_color.setVisibility(View.INVISIBLE);
                    category_color.setVisibility(View.INVISIBLE);
                    size_color.setVisibility(View.INVISIBLE);
                    month_color.setVisibility(View.INVISIBLE);
                    age_color.setVisibility(View.VISIBLE);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }
            }
            else {
                age_linear.setVisibility(View.GONE);
            }

            if(IsQuality)
            {
                quality_linear.setVisibility(View.VISIBLE);
                if(ShowFirst) {
                    varity_color.setVisibility(View.INVISIBLE);
                    quality_color.setVisibility(View.VISIBLE);
                    brands_color.setVisibility(View.INVISIBLE);
                    category_color.setVisibility(View.INVISIBLE);
                    size_color.setVisibility(View.INVISIBLE);
                    month_color.setVisibility(View.INVISIBLE);
                    age_color.setVisibility(View.INVISIBLE);
                    bundle.putString("Filter", Constant.QUALITY);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }
            }
            else {
                quality_linear.setVisibility(View.GONE);
            }

/*
            if (categoryId.equals("1") || categoryId.equals("2") || categoryId.equals("3") || categoryId.equals("4") || categoryId.equals("6") || categoryId.equals("17")|| categoryId.equals("42")) {
                age_linear.setVisibility(View.GONE);


                if(!bundle.getBoolean("IsVarietyAvailable")){
                    varity_linear.setVisibility(View.GONE);
                    varity_color.setVisibility(View.GONE);

                    quality_color.setVisibility(View.VISIBLE);
                    bundle.putString("Filter", Constant.QUALITY);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }
                else {
                    varity_color.setVisibility(View.VISIBLE);
                    bundle.putString("Filter",Constant.VARIETY);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }

            }

            else {


                if(!bundle.getBoolean("IsVarietyAvailable")){
                    age_linear.setVisibility(View.VISIBLE);
                    varity_linear.setVisibility(View.GONE);
                    age_color.setVisibility(View.VISIBLE);
                    bundle.putString("Filter",Constant.AGE);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();        }
                else {
                    varity_color.setVisibility(View.VISIBLE);
                    bundle.putString("Filter",Constant.VARIETY);
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }

            }*/

        }





       /*price_color.setVisibility(View.INVISIBLE);
       discount_color.setVisibility(View.INVISIBLE);
        rating_color.setVisibility(View.INVISIBLE);
        colour_color.setVisibility(View.INVISIBLE);
        verify_color.setVisibility(View.INVISIBLE);*/
        myDatabase = new dbBuyerFilter(getApplicationContext());

        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle != null) {
                    ItemTypeId = bundle.getString("ItemTypeId");
                    TalukaId = bundle.getString("TalukaId");
                    VarityId = bundle.getString("VarietyId");
                    QualityId = bundle.getString("QualityId");
                    StatesID = bundle.getString("StatesID");
                    DistrictId = bundle.getString("DistrictId");
                    priceids=bundle.getString("priceids");
                    categoryId=bundle.getString("categoryId");
                    ItemName=bundle.getString("ItemName");



                }

                Intent intent = new Intent(getApplicationContext(), AllSimilarDataActivity.class);
                intent.putExtra("Search", "Filter");
                intent.putExtra("SortBy", "ASC");
                intent.putExtra("ItemTypeId", ItemTypeId);
                intent.putExtra("TalukaId", TalukaId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("QualityId", QualityId);
                intent.putExtra("StatesID", StatesID);
                intent.putExtra("DistrictId", DistrictId);
                intent.putExtra("priceids", priceids);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("IsVarietyAvailable",IsVarietyAvailable);
                intent.putExtra("IsQuality",IsQuality);
                intent.putExtra("IsAge",IsAge);
                intent.putExtra("ItemName",ItemName);
                startActivity(intent);
                finish();

            }
        });
      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactDetailActivity.class);
               *//* intent.putExtra("Search","Filter");
                intent.putExtra("SortBy", "ASC");*//*
                startActivity(intent);
                finish();
            }
        });*/
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.delete();
                if (bundle != null) {
                    ItemTypeId = bundle.getString("ItemTypeId");
                    TalukaId = bundle.getString("TalukaId");
                    VarityId = bundle.getString("VarietyId");
                    QualityId = bundle.getString("QualityId");
                    StatesID = bundle.getString("StatesID");
                    DistrictId = bundle.getString("DistrictId");
                    priceids=bundle.getString("priceids");
                    categoryId=bundle.getString("categoryId");
                    ItemName=bundle.getString("ItemName");
                }

                Intent intent = new Intent(getApplicationContext(), AllSimilarDataActivity.class);
                intent.putExtra("Search", "Filter");
                intent.putExtra("SortBy", "ASC");
                intent.putExtra("ItemTypeId", ItemTypeId);
                intent.putExtra("TalukaId", TalukaId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("QualityId", QualityId);
                intent.putExtra("StatesID", StatesID);
                intent.putExtra("DistrictId", DistrictId);
                intent.putExtra("priceids", priceids);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("IsVarietyAvailable",IsVarietyAvailable);
                intent.putExtra("IsQuality",IsQuality);
                intent.putExtra("IsAge",IsAge);
                intent.putExtra("ItemName",ItemName);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AllSimilarDataActivity.class);
        intent.putExtra("Search", "Filter");
        intent.putExtra("SortBy", priceids);
        intent.putExtra("ItemTypeId", ItemTypeId);
        intent.putExtra("TalukaId", TalukaId);
        intent.putExtra("VarietyId", VarityId);
        intent.putExtra("QualityId", QualityId);
        intent.putExtra("StatesID", StatesID);
        intent.putExtra("DistrictId", DistrictId);
        intent.putExtra("priceids", priceids);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("IsVarietyAvailable",IsVarietyAvailable);
        intent.putExtra("IsQuality",IsQuality);
        intent.putExtra("IsAge",IsAge);
        intent.putExtra("ItemName",ItemName);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.varity_linear:
                bundle.putString("Filter", Constant.VARIETY);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new BuyerFilterFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();
                varity_color.setVisibility(View.VISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.quality_linear:

                bundle.putString("Filter", Constant.QUALITY);
                AppCompatActivity activity1 = (AppCompatActivity) v.getContext();
                Fragment myFragment1 = new BuyerFilterFragment();
                myFragment1.setArguments(bundle);
                activity1.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment1).addToBackStack(null).commit();
                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.VISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.age_linear:

                bundle.putString("Filter", Constant.AGE);
                AppCompatActivity activity33 = (AppCompatActivity) v.getContext();
                Fragment myFragment33 = new BuyerFilterFragment();
                myFragment33.setArguments(bundle);
                activity33.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment33).addToBackStack(null).commit();
                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.VISIBLE);
                break;

            case R.id.linearAvailableMonth:

                bundle.putString("Filter", Constant.AVAILABLEMONTH);
                AppCompatActivity activity2 = (AppCompatActivity) v.getContext();
                Fragment myFragment2 = new BuyerFilterFragment();
                myFragment2.setArguments(bundle);
                activity2.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment2).addToBackStack(null).commit();
                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.VISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.states_linear:

                bundle.putString("Filter", Constant.STATE);
                AppCompatActivity activity44 = (AppCompatActivity) v.getContext();
                Fragment myFragment44 = new BuyerFilterFragment();
                myFragment44.setArguments(bundle);
                activity44.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment44).addToBackStack(null).commit();
                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.VISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.District_linear:
                bundle.putString("Filter", Constant.DISTRICT);
                AppCompatActivity activity3 = (AppCompatActivity) v.getContext();
                Fragment myFragment3 = new BuyerFilterFragment();
                myFragment3.setArguments(bundle);
                activity3.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment3).addToBackStack(null).commit();

                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.VISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.taluka_linear:

                AppCompatActivity activity4 = (AppCompatActivity) v.getContext();
                Fragment myFragment4 = new BuyerFilterFragment();
                bundle.putString("Filter",Constant.TALUKA);
                myFragment4.setArguments(bundle);
                activity4.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment4).addToBackStack(null).commit();
                varity_color.setVisibility(View.INVISIBLE);
                quality_color.setVisibility(View.INVISIBLE);
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.VISIBLE);
                month_color.setVisibility(View.INVISIBLE);
                age_color.setVisibility(View.INVISIBLE);
                break;


        }
    }
}



/*
        if (categoryId.equals("1") || categoryId.equals("2") || categoryId.equals("3") || categoryId.equals("4") || categoryId.equals("6") || categoryId.equals("17")|| categoryId.equals("42")) {
                age_linear.setVisibility(View.GONE);

                if(!IsVarietyAvailable){
                varity_linear.setVisibility(View.GONE);
                varity_color.setVisibility(View.GONE);
                bundle.putString("Filter", Constant.QUALITY);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).
                commit();
                }
                else {
                varity_color.setVisibility(View.VISIBLE);
                bundle.putString("Filter",Constant.VARIETY);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }

                }

                else {
                age_linear.setVisibility(View.VISIBLE);
                varity_linear.setVisibility(View.GONE);
                age_color.setVisibility(View.VISIBLE);
                bundle.putString("Filter",Constant.AGE);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                }


                if (categoryId.equals("1") || categoryId.equals("2") || categoryId.equals("3")||categoryId.equals("4") || categoryId.equals("14") || categoryId.equals("17") || categoryId.equals("42") || categoryId.equals("7") || categoryId.equals("35")) {
                if(!IsVarietyAvailable) {
                quality_color.setVisibility(View.VISIBLE);
                quality_linear.setVisibility(View.VISIBLE);
                }
                else{

                bundle.putString("Filter",Constant.VARIETY);
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                varity_linear.setVisibility(View.VISIBLE);
                varity_color.setVisibility(View.VISIBLE);
                quality_color.setVisibility(View.GONE);
                age_color.setVisibility(View.GONE);
                }

                }
*/

