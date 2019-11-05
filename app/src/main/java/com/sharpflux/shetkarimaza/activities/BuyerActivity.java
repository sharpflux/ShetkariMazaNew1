package com.sharpflux.shetkarimaza.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.DynamicFragmentAdapter;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuyerActivity extends AppCompatActivity implements  TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    Locale myLocale;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        initViews();


    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        setDynamicFragmentToTabLayout();

    }

    private void setDynamicFragmentToTabLayout() {

        //    CustomProgressDialog.showSimpleProgressDialog(getContext(), "Loading...", "Fetching data", false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_RECYCLER + "en",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            // CustomProgressDialog.removeSimpleProgressDialog();
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    mTabLayout.addTab(mTabLayout.newTab().setText(userJson.getString("CategoryName_EN")));

                                    DynamicFragmentAdapter mDynamicFragmentAdapter = new   DynamicFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(),
                                            obj,BuyerActivity.this);

                                    viewPager.setAdapter(mDynamicFragmentAdapter);

                                    viewPager.setCurrentItem(0);
                                } else {
                                    Toast.makeText(BuyerActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BuyerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", "");
                params.put("Password", "");
                return params;
            }
        };

        VolleySingleton.getInstance(BuyerActivity.this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}