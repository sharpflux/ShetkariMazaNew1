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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class AgeFragment extends Fragment {


    private RecyclerView rcv_age;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    TextView btn_next,btn_back,btnFilterData;
    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;
    SearchView searchViewAge;
    VarietyAdapter myAdapter;
    String VarityId="",itemTypeId="",ItemTypeId,ItemName,QualityId,categoryId="";
    Bundle extras;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    StringBuilder age_builder_id;



    public AgeFragment() {

    }

    String varietyid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_age, container, false);
        myLocale = getResources().getConfiguration().locale;

        rcv_age = view.findViewById(R.id.rcv_age);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnFilterData);

        productlist = new ArrayList<>();
        mydatabase = new dbLanguage(getContext());

        layoutManager = new LinearLayoutManager(getContext());
        rcv_age.setLayoutManager(layoutManager);
       age_builder_id = new StringBuilder();


        searchViewAge = view.findViewById(R.id.searchViewAge);



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
        BundleAssign();
        AssignVariables();



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extras = new Bundle();
                if (extras != null) {
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("VarietyId", VarityId);
                    extras.putString("ItemName", ItemName);
                    extras.putString("categoryId", categoryId);
                }

                FragmentTransaction transection = getActivity().getSupportFragmentManager().beginTransaction();
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
                        age_builder_id.append(filter.getId() + ",");
                    }
                }

                if (extras != null) {
                    extras.putString("VarietyId", VarityId);
                    extras.putString("ItemTypeId", itemTypeId);
                    extras.putString("categoryId", categoryId);
                }


                FragmentTransaction transection = getActivity().getSupportFragmentManager().beginTransaction();
                StateFragment mfragment = new StateFragment();
                mfragment.setArguments(extras);
                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                transection.commit();

            }
        });
        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
                intent.putExtra("ItemTypeId", itemTypeId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("ItemName", ItemName);
                intent.putExtra("categoryId", categoryId);

                startActivity(intent);
            }
        });

        AgeFragment.AsyncTaskRunner runner = new AgeFragment.AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);


        return view;
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


    private void SetDynamicDATA() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_AGE + "Language="+currentLanguage,
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

                                                            userJson.getString("AgeGroupId"),
                                                            userJson.getString("AgeGroupName"),
                                                            Constant.AGE
                                                    );

                                    productlist.add(sellOptions);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }
                                myAdapter = new VarietyAdapter(getContext(), productlist);
                                rcv_age.setAdapter(myAdapter);

                                searchViewAge.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    private void BundleAssign()
    {
        extras = getArguments();

        if (extras != null) {
            categoryId = extras.getString("categoryId");
            VarityId = extras.getString("VarietyId");
            itemTypeId = extras.getString("ItemTypeId");
            QualityId = extras.getString("QualityId");
        }

    }

    private void AssignVariables()
    {
        ItemTypeId = "";
        VarityId = "";
        QualityId = "";
        categoryId = "";

    }


}



