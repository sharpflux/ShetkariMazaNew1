package com.sharpflux.shetkarimaza.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MachineAdapter;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MachinHireActivity extends AppCompatActivity {
    Bundle bundle;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    String currentLanguage;
    dbLanguage mydatabase;
    MachineAdapter myCategoryTypeAdapter;
    ArrayList<MyCategoryType> categoryList;
    MyCategoryType myCategoryType;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    String  ItemTypeId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machin_hire);


        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(MachinHireActivity.this);
        categoryList = new ArrayList<>();


        mydatabase = new dbLanguage(MachinHireActivity.this);
        Cursor cursor = mydatabase.LanguageGet("language");
        mydatabase = new dbLanguage(MachinHireActivity.this);

        bundle = getIntent().getExtras();
        ItemTypeId="0";
        if (bundle != null) {
            ItemTypeId = bundle.getString("ProductId");

        }

        if(ItemTypeId.equals("36"))
            setTitle(getResources().getString(R.string.HiringMachinery));
        if(ItemTypeId.equals("37"))
            setTitle(getResources().getString(R.string.LabourforAgri));

        if(cursor.getCount()==0) {
            currentLanguage="en";
        }
        else{
            while (cursor.moveToNext()) {
                currentLanguage = cursor.getString(0);
                if( currentLanguage==null)
                {
                    currentLanguage="en";
                }

            }
        }




        mRecyclerView = findViewById(R.id.recyclerview_categorytype);
        mGridLayoutManager = new GridLayoutManager(MachinHireActivity.this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);


        setDynamicFragmentToTabLayout(1);
    }

    private void setDynamicFragmentToTabLayout(Integer PageSize) {
        //SUPPLIED PageSize=99 for detect show the categories whose are usefull for Home Page (IsHomePageCategory)
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  URLs.URL_MasterSubCategoriesGET+"Language="+currentLanguage+"&CategoryId="+ItemTypeId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);



                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    myCategoryType = new MyCategoryType
                                            (      userJson.getString("ImageUrl"),
                                                    userJson.getString("SubCategoriesName"),
                                                    userJson.getString("MasterSubCategoriesId"),
                                                    "0"
                                            );
                                    categoryList.add(myCategoryType);
                                } else {
                                    Toast.makeText(MachinHireActivity.this, response, Toast.LENGTH_SHORT).show();
                                }

                                myCategoryTypeAdapter = new MachineAdapter(MachinHireActivity.this, categoryList);
                                mRecyclerView.setAdapter(myCategoryTypeAdapter);

                            }

                            myCategoryTypeAdapter.notifyDataSetChanged();
                            customDialogLoadingProgressBar.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            customDialogLoadingProgressBar.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customDialogLoadingProgressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(MachinHireActivity.this).addToRequestQueue(stringRequest);
    }
}