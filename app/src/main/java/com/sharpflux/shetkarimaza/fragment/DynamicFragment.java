package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyBuyerAdapter;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DynamicFragment extends Fragment {

    Locale myLocale;
    List<SellOptions> productlist;
    RecyclerView mRecyclerView;
    String CategoryId="1";
    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        CategoryId=  String.valueOf(getArguments().getString("CategoryId"));


        productlist = new ArrayList<>();
        myLocale = getResources().getConfiguration().locale;
        // setDynamicFragmentToTabLayout();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "10";
        runner.execute(sleepTime);

        return view;
    }

    private void setDynamicFragmentToTabLayout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_NAME+"?CategoryId="+CategoryId+"&Language="+myLocale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SellOptions sellOptions =
                                            new SellOptions
                                                    (userJson.getString("ImageUrl"),
                                                            userJson.getString("ItemName"),
                                                            userJson.getString("ItemTypeId"),
                                                            CategoryId);

                                    productlist.add(sellOptions);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }
                                MyBuyerAdapter myAdapter = new MyBuyerAdapter(getContext(), productlist);
                                mRecyclerView.setAdapter(myAdapter);
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

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
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
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

    }
}
