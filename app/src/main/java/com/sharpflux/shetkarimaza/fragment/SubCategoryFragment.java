package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.Interface.RecyclerViewClickListener;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyBuyerAdapter;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.adapter.SubCategoryAdapter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.model.SubCategory;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
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


public class SubCategoryFragment extends Fragment {

    SubCategory myCategoryType;
    Locale myLocale;
    ArrayList<SubCategory> productlist;
    RecyclerView mRecyclerView;
    String CategoryId = "1";
    SearchView searchView;
    SubCategoryAdapter myAdapter;
    boolean isLoading = false;
    int currentItems,totalItems,scrollOutItems;
    GridLayoutManager mGridLayoutManager;

    dbLanguage mydatabase;
    String currentLanguage,language;

    TextView txt_nurseryName;



    public static SubCategoryFragment newInstance() {
        return new SubCategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerviewsub1);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        CategoryId = String.valueOf(getArguments().getString("CategoryId"));
        searchView = view.findViewById(R.id.searchViewHome);


        mydatabase = new dbLanguage(getContext());

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
        txt_nurseryName = view.findViewById(R.id.txt_group);
        productlist = new ArrayList<>();
        myLocale = getResources().getConfiguration().locale;


        if (getArguments() != null) {
            CategoryId = getArguments().getString("CategoryId").toString();

        }
        //setDynamicFragmentToTabLayout();



        SubCategoryFragment.AsyncTaskRunner runner = new SubCategoryFragment.AsyncTaskRunner();
        String sleepTime = "10";
        runner.execute(sleepTime);

        return view;

    }


    private void setDynamicFragmentToTabLayout(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_NAME+"?CategoryId="+CategoryId +"&Language="+currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {
                                    myCategoryType = new SubCategory
                                            (       userJson.getString("ImageUrl"),
                                                    userJson.getString("CategoryId"),
                                                    userJson.getString("ItemName"));


                                    productlist.add(myCategoryType);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myAdapter = new SubCategoryAdapter(getContext(), productlist);
                                mRecyclerView.setAdapter(myAdapter);


                            }
                          /*  parentShimmerLayout.stopShimmerAnimation();
                            parentShimmerLayout.setVisibility(View.GONE);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
//
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
        protected void onPostExecute(String result)
        {
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
