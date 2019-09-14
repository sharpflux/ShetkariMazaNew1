package com.sharpflux.shetkarimaza.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.SimilarListAdapter;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllSimilarDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_similar_data);

        recyclerView = findViewById(R.id.similar_rvProductList);
        productlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<SimilarList> mList = new ArrayList<>();
        SimilarListAdapter myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, mList);
        recyclerView.setAdapter(myAdapter);

        AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
        String sleepTime = "10";
        runner.execute(sleepTime);
    }


    private void SetDynamicDATA() {

        bundle = getIntent().getExtras();
        bundle.getString("ItemTypeId");
        if (bundle != null) {
            ItemTypeId = bundle.getString("ItemTypeId");
            TalukaId = bundle.getString("TalukaId");
            VarityId = bundle.getString("VarietyId");
            QualityId = bundle.getString("QualityId");
        }
        if (bundle != null) {

            if (TalukaId.equals(""))
                TalukaId = "0";
            if (VarityId.equals(""))
                VarityId = "0";
            if (QualityId.equals(""))
                QualityId = "0";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_REQESTS + "?StartIndex=1&PageSize=50&ItemTypeId=" + ItemTypeId + "&VarityId=" + VarityId + "&StateId=0&DistrictId=0&QualityId=" + QualityId + "&TalukaId=" + TalukaId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray obj = new JSONArray(response);
                                for (int i = 0; i < obj.length(); i++) {
                                    JSONObject userJson = obj.getJSONObject(i);
                                    if (!userJson.getBoolean("error")) {
                                        SimilarList sellOptions;
                                        sellOptions = new SimilarList
                                                (
                                                        userJson.getString("ImageUrl"),
                                                        userJson.getString("ItemName"),
                                                        userJson.getString("VarietyName"),
                                                        String.valueOf(userJson.getDouble("AvailableQuantity")),
                                                        "₹" + String.valueOf(userJson.getDouble("ExpectedPrice")),
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "0"

                                                );

                                        productlist.add(sellOptions);
                                    } else {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    }

                                    SimilarListAdapter myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, productlist);
                                    recyclerView.setAdapter(myAdapter);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                SetDynamicDATA();
                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllSimilarDataActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

    }
}
