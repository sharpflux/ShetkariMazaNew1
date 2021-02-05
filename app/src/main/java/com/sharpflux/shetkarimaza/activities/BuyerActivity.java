package com.sharpflux.shetkarimaza.activities;

import android.database.Cursor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.DynamicFragmentAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BuyerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    Locale myLocale;
    private ViewPager viewPager;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    dbLanguage mydatabase;
    String currentLanguage, language;
    dbFilter myDatabase;

    dbBuyerFilter myDatabaseCategoryItemTypeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        mydatabase = new dbLanguage(getApplicationContext());

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        initViews();
        myDatabase = new dbFilter(getApplicationContext());
        myDatabaseCategoryItemTypeId = new dbBuyerFilter(getApplicationContext());
        myDatabase.DeleteRecordAll();
        myDatabaseCategoryItemTypeId.DeleteRecordAll();
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

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("500");

    }

    private class  AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;


        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


                setDynamicFragmentToTabLayout();
                Thread.sleep(500);


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(BuyerActivity.this);
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }

    private void setDynamicFragmentToTabLayout() {

        //    CustomProgressDialog.showSimpleProgressDialog(getContext(), "Loading...", "Fetching data", false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_RECYCLER + currentLanguage,
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


                                    DynamicFragmentAdapter mDynamicFragmentAdapter =
                                            new DynamicFragmentAdapter(getSupportFragmentManager(),
                                                    mTabLayout.getTabCount(), obj, BuyerActivity.this);

                                    viewPager.setAdapter(mDynamicFragmentAdapter);

                                    viewPager.setCurrentItem(0);
                                } else {
                                    Toast.makeText(BuyerActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                customDialogLoadingProgressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            customDialogLoadingProgressBar.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BuyerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        customDialogLoadingProgressBar.dismiss();
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
