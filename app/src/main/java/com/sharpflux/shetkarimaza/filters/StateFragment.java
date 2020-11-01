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

import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
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

public class StateFragment extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    Button btn_next, btn_back, btnFilterData;
    Locale myLocale;
    String VarityId = "", QualityId = "", DistrictId = "", itemTypeId = "", StatesID = "", ItemName;
    StringBuilder state_builder_id;
    String StateIds = "";
    Bundle extras;
    SearchView searchView;
    VarietyAdapter myAdapter;
    dbBuyerFilter myDatabase;
    dbLanguage mydatabase;
    String currentLanguage, language;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_state, container, false);
        myLocale = getResources().getConfiguration().locale;
        recyclerView = view.findViewById(R.id.rcv_vriety);
        productlist = new ArrayList<>();

        state_builder_id = new StringBuilder();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnFilterData);

        searchView = view.findViewById(R.id.searchViewState);

        mydatabase = new dbLanguage(getContext());

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);
        myDatabase = new dbBuyerFilter(getContext());

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        BundleAssign();
        extras = getArguments();
        if (QualityId.equals(null)&&VarityId.equals(null)) {
            QualityId.equals("0");
            VarityId.equals("0");
        }
        if (extras != null) {

            //VarityId = extras.getString("VarietyId");
            //QualityId = extras.getString("QualityId");
            itemTypeId = extras.getString("ItemTypeId");
            ItemName = extras.getString("ItemName");

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BundleAssign();
                FragmentTransaction transection = getFragmentManager().beginTransaction();
                QualityFragment mfragment = new QualityFragment();
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
                        state_builder_id.append(filter.getId() + ",");
                    }
                }
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("StatesID", state_builder_id.toString());
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("ItemName", ItemName);
                }




                Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                //Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
               // Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");


                if (STATECursor.getCount()==0) {
                    FragmentTransaction transection = getFragmentManager().beginTransaction();
                    PriceFragment mfragment = new PriceFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();

                } else {
                    FragmentTransaction transection = getFragmentManager().beginTransaction();
                    DistrictFragment mfragment = new DistrictFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();
                }
            }
        });


        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BundleAssign();
                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("ItemTypeId", itemTypeId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("QualityId", QualityId);
                intent.putExtra("StatesID", state_builder_id.toString());
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("Search", "Filter");
                startActivity(intent);

            }
        });


        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);


        return view;

    }

    private void BundleAssign() {
        extras = new Bundle();
        if (extras != null) {
            extras.putString("VarietyId", VarityId);
            extras.putString("QualityId", QualityId);
            extras.putString("StatesID", state_builder_id.toString());
            extras.putString("ItemTypeId", itemTypeId);
        }

    }


    private void SetDynamicDATA() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_STATE + "?Language=" + currentLanguage,
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
                                                            userJson.getString("StatesID"),
                                                            userJson.getString("StatesName"),
                                                            Constant.STATE
                                                    );

                                    productlist.add(sellOptions);

                                    StatesID = userJson.getString("StatesID");
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
                        customDialogLoadingProgressBar.dismiss();
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

        }

        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }


}
