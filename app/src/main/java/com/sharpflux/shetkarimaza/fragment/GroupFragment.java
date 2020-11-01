package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.sharpflux.shetkarimaza.Interface.RecyclerViewClickListener;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.SellerActivity;
import com.sharpflux.shetkarimaza.adapter.MyBuyerAdapter;
import com.sharpflux.shetkarimaza.adapter.MyGroupAdapter;
import com.sharpflux.shetkarimaza.adapter.MySellerAdapter;
import com.sharpflux.shetkarimaza.model.GroupData;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class GroupFragment extends Fragment implements RecyclerViewClickListener {
    RecyclerView rv_cstGrp;
    LinearLayoutManager mGridLayoutManager;
    SearchView searchView;
    TextView txt_group;
    private ArrayList<GroupData> sellOptionsList;
    RecyclerView mRecyclerView;
    Bundle bundle;
    MyGroupAdapter myAdapter2;
    String ItemTypeId,PreviousCategoryId;
        Button btnBack;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dynamic_fragment_group_layout_one, container, false);
        rv_cstGrp = view.findViewById(R.id.rv_cstGrp);
        mGridLayoutManager = new LinearLayoutManager(getContext());
        rv_cstGrp.setLayoutManager(mGridLayoutManager);
        //btnBack= view.findViewById(R.id.btnBack);

       /* btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DynamicFragment newFragment = new DynamicFragment();
                Bundle bundle=new Bundle();
                bundle.putString("CategoryId",ItemTypeId);
                bundle.putString("IsGroup","False");
                bundle.putString("PreviousCategoryId",ItemTypeId);
                newFragment.setArguments(bundle);
                String backStateName = newFragment.getClass().getName();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.frame, newFragment, backStateName);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        });*/

       //searchView = view.findViewById(R.id.searchViewHome);


     //  txt_group = view.findViewById(R.id.txt_group);

        sellOptionsList = new ArrayList<>();

        Bundle extras = getArguments();
        if (extras != null) {

            JSONArray obj = null;
            try {
                obj = new JSONArray(extras.getString("jsonObj").toString());

                for (int i = 0; i < obj.length(); i++) {
                    JSONObject userJson = null;
                    try {
                        userJson = obj.getJSONObject(i);

                        GroupData sellOptions =
                                new GroupData
                                        (userJson.getString("ImageUrl"),
                                                userJson.getString("ItemName"),
                                                userJson.getString("ItemTypeId")


                                        );
                        ItemTypeId= userJson.getString("ItemTypeId");
                        //PreviousCategoryId= extras.getString("PreviousCategoryId");

                        sellOptionsList.add(sellOptions);
                        myAdapter2 = new MyGroupAdapter(getContext(), sellOptionsList,extras.getString("jsonObj").toString(),GroupFragment.this,extras.getString("PreviousCategoryId").toString());
                        rv_cstGrp.setAdapter(myAdapter2);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





/* GroupFragment.AsyncTaskRunner runner = new GroupFragment.AsyncTaskRunner();
        String sleepTime = "10";
        runner.execute(sleepTime);*/

        return view;
    }

    @Override
    public void onClick(View view, int position) {
        AppCompatActivity activity = (AppCompatActivity)getActivity().getBaseContext();
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup","True");
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        newFragment.setArguments(bundle);
        String backStateName = newFragment.getClass().getName();
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame, newFragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commit();
    }

    public void GOPrevious(String ItemTypeId,String Group,String PreviousCategoryId,String SubCategoryName) {
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryId",ItemTypeId);
        bundle.putString("IsGroup",Group);
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        bundle.putString("SubCategoryName",SubCategoryName);
        newFragment.setArguments(bundle);
        String backStateName = newFragment.getClass().getName();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame, newFragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commit();
    }

   /* private void setDynamicFragmentToTabLayout() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_NAME + "?CategoryId=" + CategoryId + "&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    sellOptions = new SellOptions
                                            (userJson.getString("ImageUrl"),
                                                    userJson.getString("ItemName"),
                                                    userJson.getString("ItemTypeId"),
                                                    CategoryId);

                                    productlist.add(sellOptions);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myAdapter = new MyBuyerAdapter(getContext(), productlist);
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
                                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productlist.size() - 1) {

                                                productlist.add(null);
                                                myAdapter.notifyItemInserted(productlist.size() - 1);

                                                Handler handler = new Handler();

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        productlist.remove(productlist.size() - 1);
                                                        int scrollPosition = productlist.size();
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

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }*/


   /* private class AsyncTaskRunner extends AsyncTask<String, String, String> {

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

    }*/
}
