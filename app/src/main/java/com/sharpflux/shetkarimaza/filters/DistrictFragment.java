package com.sharpflux.shetkarimaza.filters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.Constant;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DistrictFragment extends Fragment {

    JSONObject userJson;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    TextView btn_next,btn_back,btnFilterData;
    String stateId= "";
    TextView hideDistrictId;
    StringBuilder district_builder_id;
    String districtIds;
    Bundle extras;
    String VarityId="",QualityId="",itemTypeId="",StatesID="",ItemName="",categoryId="";
    SearchView searchView;
    VarietyAdapter myAdapter;
    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;
    dbBuyerFilter myDatabaseFilter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_district, container, false);
        myLocale = getResources().getConfiguration().locale;

        recyclerView = view.findViewById(R.id.rcv_vriety);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData= view.findViewById(R.id.btnFilterData);


        district_builder_id = new StringBuilder();
        hideDistrictId = view.findViewById(R.id.hideDistrictId);
        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        searchView = view.findViewById(R.id.searchViewDistrict);


        mydatabase = new dbLanguage(getContext());
        myDatabaseFilter = new dbBuyerFilter(getContext());

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);


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
        extras = getArguments();




        if (extras != null) {
            StatesID=extras.getString("StatesID");
            VarityId = extras.getString("VarietyId");
            QualityId = extras.getString("QualityId");
            itemTypeId=extras.getString("ItemTypeId");
            ItemName=extras.getString("ItemName");
            categoryId = extras.getString("categoryId");
        }

        Cursor STATECursor = myDatabaseFilter.FilterGetByFilterName("STATE");

        while (STATECursor.moveToNext()) {
            StatesID = StatesID + STATECursor.getString(0) + ",";
        }


        districtIds="";


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                extras = new Bundle();

                if (extras != null) {

                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("DistrictId", district_builder_id.toString());
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("ItemName", ItemName);
                    extras.putString("categoryId", categoryId);
                    extras.putString("Search", "Filter");
                }

                FragmentTransaction transection = getActivity().getSupportFragmentManager().beginTransaction();
                StateFragment mfragment = new StateFragment();
                mfragment.setArguments(extras);
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
                        district_builder_id.append(filter.getId() + ",");
                        districtIds = districtIds + filter.getId() + ",";

                    }
                }


                extras = new Bundle();

                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("DistrictId", district_builder_id.toString());
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("ItemName", ItemName);
                    extras.putString("categoryId", categoryId);
                    extras.putString("Search", "Filter");
                }



                if (district_builder_id.toString().equals("")) {
                    FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                    PriceFragment mfragment = new PriceFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();

                } else {
                    FragmentTransaction transection = getActivity().getSupportFragmentManager().beginTransaction();
                    TalukaFragment mfragment = new TalukaFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();
                }



            }
        });

        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("ItemTypeId",itemTypeId);
                intent.putExtra("VarietyId",VarityId);
                intent.putExtra("QualityId",QualityId);
                intent.putExtra("StatesID",StatesID);
                intent.putExtra("ItemName",ItemName);
                intent.putExtra("DistrictId", district_builder_id.toString());
                startActivity(intent);

            }
        });





        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);

        return view;
    }


    private void SetDynamicDATA() {

        String states=StatesID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_DISTRICT + StatesID +"&Language="+ currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SubCategoryFilter sellOptions =
                                            new SubCategoryFilter
                                                    (
                                                            userJson.getString("DistrictId"),
                                                            userJson.getString("DistrictName"),
                                                            Constant.DISTRICT
                                                    );

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
