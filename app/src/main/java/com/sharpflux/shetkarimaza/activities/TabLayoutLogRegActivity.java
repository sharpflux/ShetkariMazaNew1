package com.sharpflux.shetkarimaza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.PagerTabAdapter;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;

public class
TabLayoutLogRegActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_log_reg);


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

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        PagerTabAdapter adapter = new PagerTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);


    }

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
}
