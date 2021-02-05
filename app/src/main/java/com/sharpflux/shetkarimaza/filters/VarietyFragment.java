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

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
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
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
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

public class VarietyFragment extends Fragment {

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<SubCategoryFilter> productlist;
    TextView btn_next, btn_back, btnFilterData;
    String itemTypeId = "", categoryId = "",ItemName="";
    Bundle extras;
    StringBuilder varity_builder_id;
    TextView errortv;
    DataAdapter dataAdapter;
    SearchView searchView;
    VarietyAdapter myAdapter;
    Locale myLocale;
    dbBuyerFilter myDatabasefilter;

    dbLanguage mydatabase;
    String currentLanguage,language;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    public VarietyFragment() {

    }

    String varietyid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_variety, container, false);
        myLocale = getResources().getConfiguration().locale;

        recyclerView = view.findViewById(R.id.rcv_vriety);
        btn_next = view.findViewById(R.id.btnnextVariety);
        btn_back = view.findViewById(R.id.btnbackVariety);
        btnFilterData = view.findViewById(R.id.btnFilterData);
        //errortv = view.findViewById(R.id.errortv);

        productlist = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        varity_builder_id = new StringBuilder();
        searchView = view.findViewById(R.id.searchView);

        myDatabasefilter = new dbBuyerFilter(getContext());



        mydatabase = new dbLanguage(getContext());

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);



        Cursor cursorid = myDatabasefilter.GetAllCategory();

        while (cursorid.moveToNext()) {
            categoryId=cursorid.getString(0);
            itemTypeId=cursorid.getString(1);

        }



        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }



      /*  extras = new Bundle();

        if (extras != null) {
            itemTypeId = getArguments().getString("ItemTypeId");
            categoryId = getArguments().getString("ProductId");
            ItemName = getArguments().getString("ItemName");
        }*/




        extras = new Bundle();
        if (extras != null) {
            extras.putString("VarietyId", varity_builder_id.toString());
            extras.putString("ItemTypeId", itemTypeId);
           // extras.putString("ItemName", ItemName);
          //  extras.putString("categoryId", categoryId);

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabasefilter.DeleteRecordAll();
                myDatabasefilter.DeleteRecordCategory();
                Intent in = new Intent(getContext(), BuyerActivity.class);
                in.putExtra("ItemTypeId", itemTypeId);
                startActivity(in);


            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                for (int i = 0; i < productlist.size(); i++) {
                    SubCategoryFilter filter = productlist.get(i);
                    if (filter.getSelected()) {
                        varity_builder_id.append(filter.getId() + ",");
                    }
                }

                if(categoryId!=null) {
                    if (categoryId.equals("3") || categoryId.equals("4") || categoryId.equals("6") ||
                            categoryId.equals("10") || categoryId.equals("43")) {
                        FragmentTransaction transection = getFragmentManager().beginTransaction();
                        StateFragment mfragment = new StateFragment();
                        mfragment.setArguments(extras);
                        transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                        transection.commit();
                    } else if (categoryId.equals("15") || categoryId.equals("35")) {
                        FragmentTransaction transection = getFragmentManager().beginTransaction();
                        AgeFragment mfragment = new AgeFragment();
                        mfragment.setArguments(extras);
                        transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                        transection.commit();
                    } else {
                        FragmentTransaction transection = getFragmentManager().beginTransaction();
                        QualityFragment mfragment = new QualityFragment();
                        mfragment.setArguments(extras);
                        transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                        transection.commit();
                    }
                }

            }
        });
        btnFilterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < productlist.size(); i++) {
                    SubCategoryFilter filter = productlist.get(i);
                    if (filter.getSelected()) {
                        varity_builder_id.append(filter.getId() + ",");
                    }
                }
              //  myDatabase.DeleteRecordAll();
                Intent intent = new Intent(getContext(), AllSimilarDataActivity.class);
               /* intent.putExtra("TalukaId","0");
                   intent.putExtra("QualityId","0");*/
                intent.putExtra("ItemTypeId", itemTypeId);
                intent.putExtra("VarietyId", varity_builder_id.toString());
                intent.putExtra("Search","Filter");
              //  intent.putExtra("ItemName",ItemName);
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
                URLs.URL_VARIATY + itemTypeId + "&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);

                           /* if(obj.length()==0){
                                FragmentTransaction transection = getFragmentManager().beginTransaction();
                                QualityFragment mfragment = new QualityFragment();
                                mfragment.setArguments(extras);
                                transection.replace(R.id.dynamic_fragment_frame_layout_variety, mfragment);
                                transection.commit();

                            }*/
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    SubCategoryFilter sellOptions =
                                            new SubCategoryFilter
                                                    (
                                                            userJson.getString("VarietyId"),
                                                            userJson.getString("VarietyName"),
                                                            Constant.VARIETY
                                                            );

                                    productlist.add(sellOptions);


                                    varietyid = userJson.getString("VarietyId");
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



