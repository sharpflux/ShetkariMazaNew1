package com.sharpflux.shetkarimaza.filters;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ContactDetailActivity;
import com.sharpflux.shetkarimaza.activities.TransporterViewActivity;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;

import java.util.ArrayList;


public class Filter1Activity extends AppCompatActivity implements View.OnClickListener {

    TextView text;
    ImageView back, menu, bag, search;
    Bundle bundle;
    FrameLayout container;
    ArrayList<SubCategoryFilter> productlist;
    //Every layout and view is mentioned here with which the logic will work on screen....
    LinearLayout brands_linear, category_linear, price_linear, size_linear, discount_linear, rating_linear, colour_linear, verify_linear;

    LinearLayout brands_color, category_color, price_color, size_color, discount_color, rating_color, colour_color, verify_color;

    TextView brands_text, category_text, price_text, size_text, discount_text, rating_text, colour_text, verify_text, btnFilterData, btnClear;
    StringBuilder varity_builder_id;
    dbFilter myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter1);


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
        discount_linear = findViewById(R.id.discount_linear);


        brands_color = findViewById(R.id.brands_color);
        category_color = findViewById(R.id.category_color);
        size_color = findViewById(R.id.size_color);


        brands_text = findViewById(R.id.brands_text);
        category_text = findViewById(R.id.category_text);
        size_text = findViewById(R.id.size_text);


        brands_linear.setOnClickListener(this);
        category_linear.setOnClickListener(this);
        size_linear.setOnClickListener(this);


        //It is used to already have a selected fragment on the screen
        AppCompatActivity activity;
        activity = Filter1Activity.this;
        Fragment myFragment = new FilterFragment();


        bundle.putString("Filter", "STATE");
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();


        brands_color.setVisibility(View.VISIBLE);
        category_color.setVisibility(View.INVISIBLE);
        size_color.setVisibility(View.INVISIBLE);

       /*price_color.setVisibility(View.INVISIBLE);
       discount_color.setVisibility(View.INVISIBLE);
        rating_color.setVisibility(View.INVISIBLE);
        colour_color.setVisibility(View.INVISIBLE);
        verify_color.setVisibility(View.INVISIBLE);*/
        myDatabase = new dbFilter(getApplicationContext());

        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Filter1Activity.this, TransporterViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Search", "Filter");
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.DeleteRecordAll();
                Intent intent = new Intent(Filter1Activity.this, TransporterViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Search", "Filter");
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.states_linear:

                bundle.putString("Filter", "STATE");
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FilterFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();
                brands_color.setVisibility(View.VISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.District_linear:
                bundle.putString("Filter", "DISTRICT");
                AppCompatActivity activity2 = (AppCompatActivity) v.getContext();
                Fragment myFragment2 = new FilterFragment();
                myFragment2.setArguments(bundle);
                activity2.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment2).addToBackStack(null).commit();
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.VISIBLE);
                size_color.setVisibility(View.INVISIBLE);
                break;

            case R.id.taluka_linear:

                AppCompatActivity activity3 = (AppCompatActivity) v.getContext();
                Fragment myFragment3 = new FilterFragment();
                bundle.putString("Filter", "TALUKA");
                myFragment3.setArguments(bundle);
                activity3.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment3).addToBackStack(null).commit();
                brands_color.setVisibility(View.INVISIBLE);
                category_color.setVisibility(View.INVISIBLE);
                size_color.setVisibility(View.VISIBLE);
                break;


        }
    }
}
