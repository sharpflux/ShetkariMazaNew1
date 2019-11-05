package com.sharpflux.shetkarimaza.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MySellerAdapter;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.utils.CheckDeviceIsOnline;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class SellerActivity extends AppCompatActivity {

    private List<SellOptions> sellOptionsList;

    private RecyclerView mRecyclerView;
    Locale myLocale;
    boolean isLoading = false;
    MySellerAdapter myAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        myLocale = getResources().getConfiguration().locale;

        if (!CheckDeviceIsOnline.isNetworkConnected(this)/*||!CheckDeviceIsOnline.isWifiConnected(this)*/)

        { AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Check Internet Connection!");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("Internet not availlable check your Intrnet Connctivity And Try Again!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }


        setTitle(R.string.whatareyouoffering);
        mRecyclerView = findViewById(R.id.rvMain);


        LinearLayoutManager mGridLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        sellOptionsList = new ArrayList<>();

        // setDynamicFragmentToTabLayout();
        SellerActivity.AsyncTaskRunner runner = new SellerActivity.AsyncTaskRunner();
        String sleepTime = "100";
        runner.execute(sleepTime);

    }


    private void setDynamicFragmentToTabLayout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_RECYCLER+myLocale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++)
                            {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SellOptions sellOptions=
                                            new SellOptions
                                                    (userJson.getString("ImageUrl"),
                                                            userJson.getString("CategoryName_EN"),
                                                            userJson.getString("CategoryId"));

                                    sellOptionsList.add(sellOptions);
                                } else {
                                    Toast.makeText(SellerActivity.this, response, Toast.LENGTH_SHORT).show();
                                }

                               myAdapter = new MySellerAdapter(SellerActivity.this, sellOptionsList);
                                mRecyclerView.setAdapter(myAdapter);

                                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                    }

                                    @Override
                                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);

                                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                                        if (!isLoading) {
                                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == sellOptionsList.size() - 1) {

                                                sellOptionsList.add(null);
                                                myAdapter.notifyItemInserted(sellOptionsList.size() - 1);

                                                Handler handler = new Handler();

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        sellOptionsList.remove(sellOptionsList.size() - 1);
                                                        int scrollPosition = sellOptionsList.size();
                                                        myAdapter.notifyItemRemoved(scrollPosition);
                                                        int currentSize = scrollPosition;
                                                        int nextLimit = currentSize + 3;

                                                        while (currentSize - 1 < nextLimit) {
                                                            // productlist.add(sellOptions);
                                                            currentSize++;
                                                        }

                                                        myAdapter.notifyDataSetChanged();
                                                        isLoading = false;
                                                    }
                                                }, 1000);

                                                isLoading = true;
                                            }
                                        }
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SellerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        VolleySingleton.getInstance(SellerActivity.this).addToRequestQueue(stringRequest);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


                setDynamicFragmentToTabLayout();
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
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            // finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SellerActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }

}
