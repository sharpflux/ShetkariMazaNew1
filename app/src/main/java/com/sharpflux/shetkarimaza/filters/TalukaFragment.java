package com.sharpflux.shetkarimaza.filters;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TalukaFragment extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    Button btn_next,btn_back,btnFilterData;
    String  DistrictId="", TalukaId="",VarityId="",QualityId="",itemTypeId="";
    Bundle extras;
    StringBuilder taluka_builder_id = new StringBuilder();
    SearchView searchView;
    VarietyAdapter myAdapter;



    public TalukaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taluka, container, false);

        recyclerView = view.findViewById(R.id.rcv_vriety);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnbackVariety);

        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        searchView = view.findViewById(R.id.searchViewTaluka);

        extras = getArguments();


        if (extras != null) {
            DistrictId = extras.getString("DistrictId");
            VarityId = extras.getString("VarietyId");
            itemTypeId=extras.getString("ItemTypeId");
            QualityId = extras.getString("QualityId");
        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                DistrictFragment  mfragment = new DistrictFragment ();
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });




        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < productlist.size(); i++) {
                    SubCategoryFilter filter = productlist.get(i);
                    if (filter.getSelected()) {
                        taluka_builder_id.append(filter.getId() + ",");
                    }
                }


                extras = new Bundle();

                if (extras != null) {
                    extras.putString("VarietyId",VarityId);
                    extras.putString("QualityId",QualityId);
                    extras.putString("TalukaId",taluka_builder_id.toString());
                    extras.putString("ItemTypeId",itemTypeId);


                }
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                PriceFragment mfragment = new PriceFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });

        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                startActivity(intent);

            }
        });




        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);

        return view;
    }


    private void SetDynamicDATA() {

      StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_TALUKA+DistrictId+"&Language=en",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SubCategoryFilter sellOptions =
                                            new SubCategoryFilter
                                                    (
                                                            userJson.getString("TalukasId"),
                                                            userJson.getString("TalukaName"));

                                    productlist.add(sellOptions);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myAdapter = new VarietyAdapter(getContext(), productlist);
                                recyclerView.setAdapter(myAdapter);

                                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {

                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {

                                        myAdapter.getFilter().filter(newText);
                                        return false;

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


                SetDynamicDATA();
                Thread.sleep(500);

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
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }


}
