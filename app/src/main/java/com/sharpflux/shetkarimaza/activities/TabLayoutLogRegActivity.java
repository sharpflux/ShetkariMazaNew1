package com.sharpflux.shetkarimaza.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.PagerTabAdapter;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

public class
TabLayoutLogRegActivity extends AppCompatActivity  {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_log_reg);

        viewPager_details = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager_details.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerTabAdapter pagerTabAdapter = new PagerTabAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager_details.setAdapter(pagerTabAdapter);



        viewPager_details.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager_details.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }


    void tabLayout()
    {

/*

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ChooseActivity.class));
        }

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(R.string.loginphere));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.signuphere));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



*/






/*


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //Creating our pager adapter
        PagerTabAdapter adapter = new PagerTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

*/

    }

/*
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
*/

    @Override
    public void onBackPressed() {
     final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();



    }
}
