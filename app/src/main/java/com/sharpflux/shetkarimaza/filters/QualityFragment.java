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
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.User;
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


public class QualityFragment extends Fragment {

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    TextView btn_next, btn_back, btnFilterData;
    String VarityId = "", itemTypeId = "", ItemTypeId, ItemName, categoryId = "";
    Bundle extras;
    Locale myLocale;
    SearchView searchView;
    VarietyAdapter myAdapter;

    StringBuilder quality_builder_id;

    dbLanguage mydatabase;
    String currentLanguage, language;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    public QualityFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quality, container, false);

        myLocale = getResources().getConfiguration().locale;

        recyclerView = view.findViewById(R.id.rcv_quality);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnFilterData);

        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        quality_builder_id = new StringBuilder();


        searchView = view.findViewById(R.id.searchViewQuality);


        mydatabase = new dbLanguage(getContext());

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);


        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        BundleAssign();
        AssignVariables();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("ItemName", ItemName);

                }

                FragmentTransaction transection = getFragmentManager().beginTransaction();
                VarietyFragment mfragment = new VarietyFragment();
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
                        quality_builder_id.append(filter.getId() + ",");
                    }
                }
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("QualityId", quality_builder_id.toString());
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("ItemName", ItemName);

                }


                    FragmentTransaction transection = getFragmentManager().beginTransaction();
                    StateFragment mfragment = new StateFragment();
                    mfragment.setArguments(extras);
                    transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                    transection.commit();



            }
        });


        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BundleAssign();
                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("ItemTypeId", itemTypeId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("QualityId", quality_builder_id.toString());
                intent.putExtra("ItemName", ItemName);
                startActivity(intent);

            }
        });


        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);

        return view;
    }

    private void BundleAssign() {
        extras = getArguments();

        if (extras != null) {

            VarityId = extras.getString("VarietyId");
            itemTypeId = extras.getString("ItemTypeId");
            categoryId = extras.getString("categoryId");
        }

    }

    private void AssignVariables() {
        ItemTypeId = "";

        VarityId = "";

    }

    private void SetDynamicDATA() {
        AssignVariables();
        BundleAssign();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_QUALITY + "?Language=" + currentLanguage,
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

                                                            userJson.getString("QualityId"),
                                                            userJson.getString("QualityType"),
                                                            Constant.QUALITY
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



