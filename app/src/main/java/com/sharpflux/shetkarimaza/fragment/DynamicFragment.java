package com.sharpflux.shetkarimaza.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.sharpflux.shetkarimaza.Interface.RecyclerViewClickListener;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.BuyerActivity;
import com.sharpflux.shetkarimaza.activities.SubGroupActivity;
import com.sharpflux.shetkarimaza.adapter.MyBuyerAdapter;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.EndlessRecyclerViewScrollListener;
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


public class DynamicFragment extends Fragment implements RecyclerViewClickListener {

    SellOptions sellOptions;
    Locale myLocale;
    ArrayList<SellOptions> productlist, tempProductList;
    RecyclerView mRecyclerView;
    String CategoryId = "1";
    SearchView searchView;
    MyBuyerAdapter myAdapter;
    boolean isLoading = false;
    int currentItems,totalItems,scrollOutItems;
    GridLayoutManager mGridLayoutManager;

    dbLanguage mydatabase;
    String currentLanguage,language;

    TextView txt_nurseryName,txtSubCategoryName;
    ProgressBar progressBar;
    String sleepTime;
    int page;
    LinearLayout subcategoryLinear;
    String SubCategoryName="";
    ImageView imgBack;



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

        mRecyclerView = view.findViewById(R.id.recyclerviewsub);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        productlist = new ArrayList<>();
        tempProductList = new ArrayList<>();
        myAdapter = new MyBuyerAdapter(getContext(), productlist, tempProductList);
        mRecyclerView.setAdapter(myAdapter);
        progressBar = view.findViewById(R.id.progressBar);
        CategoryId = String.valueOf(getArguments().getString("CategoryId"));

        searchView = view.findViewById(R.id.searchViewone);
        subcategoryLinear=view.findViewById(R.id.subcategoryLinear);
        txtSubCategoryName=view.findViewById(R.id.txtSubCategoryName);
        imgBack=view.findViewById(R.id.imgBack);


        mydatabase = new dbLanguage(getContext());
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);

        if(getArguments().getString("IsGroup")!=null){
            if(getArguments().getString("IsGroup").contains("True")) {
                subcategoryLinear.setVisibility(View.VISIBLE);
                txtSubCategoryName.setText(getArguments().getString("SubCategoryName"));
                searchView.setVisibility(View.VISIBLE);
            }
        }

        subcategoryLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments().getString("IsGroup")!=null){
                    if(getArguments().getString("IsGroup").contains("True")) {
                        ShowDynamicAgain(getArguments().getString("PreviousCategoryId"));
                       // setDynamicFragmentToTabLayout(1);
                        subcategoryLinear.setVisibility(View.GONE);
                    }
                }
            }
        });

       /* imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments().getString("IsGroup")!=null){
                    if(getArguments().getString("IsGroup").contains("True")) {
                        ShowDynamicAgain(getArguments().getString("CategoryId"));
                       // setDynamicFragmentToTabLayout(1);
                    }
                }
            }
        });*/


        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }
        txt_nurseryName=view.findViewById(R.id.txt_group);

        myLocale = getResources().getConfiguration().locale;

        DynamicFragment.AsyncTaskRunner runner = new DynamicFragment.AsyncTaskRunner();
        runner.execute("1");


        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                isLoading = true;
                int currentSize = myAdapter.getItemCount();
                AsyncTaskRunner runner = new AsyncTaskRunner();
                sleepTime = String.valueOf(page+1);
                runner.execute(sleepTime);
            }
        });



        // setDynamicFragmentToTabLayout();


        return view;
    }

    public void setDynamicFragmentToTabLayout(Integer PageSize) {

     //   Handler handler = new Handler();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_NAME +PageSize+"&PageSize=500&CategoryId=" + CategoryId + "&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);
                            progressBar.setVisibility(View.VISIBLE);

                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                progressBar.setVisibility(View.VISIBLE);

                                if (!userJson.getBoolean("error"))
                                {
                                    if(userJson.getBoolean("IsGroup"))
                                    {


                                        //String SubCategoryName=userJson.getString("ItemName");
                                        //txtSubCategoryName.setText(userJson.getString("ItemName"));
                                        searchView.setVisibility(View.INVISIBLE);

                                        showGroupFragment(obj,CategoryId);
                                        break;
                                    }
                                    else {


                                        sellOptions = new SellOptions
                                                (userJson.getString("ImageUrl"),
                                                        userJson.getString("ItemName"),
                                                        userJson.getString("ItemTypeId"),
                                                        CategoryId);

                                        productlist.add(sellOptions);
                                        tempProductList.add(sellOptions);


                                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                            @Override
                                            public boolean onQueryTextSubmit(String s) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onQueryTextChange(String s) {
                                                myAdapter.getFilter().filter(s);
                                                myAdapter.notifyDataSetChanged();
                                                return false;

                                            }
                                        });


                                      /*  Handler handler = new Handler(Looper.getMainLooper());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                myAdapter.notifyDataSetChanged();
                                            }
                                        });*/

                                        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                                    isLoading = true;
                                                }


                                            }

                                            @Override
                                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                                super.onScrolled(recyclerView, dx, dy);

                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


                                                //LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                                                if (!isLoading) {
                                                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productlist.size() - 1) {

                                                        productlist.add(null);
                                                        myAdapter.notifyItemInserted(productlist.size() - 1);

                                                        Handler handler = new Handler();

                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                //productlist.remove(productlist.size() - 1);
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
                                        });*/
                                      /*  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                            @Override
                                            public boolean onQueryTextSubmit(String query) {

                                                return false;
                                            }

                                            @Override
                                            public boolean onQueryTextChange(String newText) {

                                                myAdapter.getFilter().filter(newText);
                                                return false;

                                            }
                                        });*/










                                    }
                                }

                                else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                            }

                            myAdapter.notifyDataSetChanged();
                            isLoading = false;
                            progressBar.setVisibility(View.GONE);







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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }





    @Override
    public void onClick(View view, int position) {
        setDynamicFragmentToTabLayout(page);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                /*setDynamicFragmentToTabLayout();
                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";*/

                setDynamicFragmentToTabLayout(Integer.valueOf( params[0]));
                Thread.sleep(10);
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


/*    private void swapFragment(JSONArray obj,String PreviousCategoryId){


        AppCompatActivity activity = (AppCompatActivity) getActivity().getBaseContext();
        GroupFragment categoryFragment = new GroupFragment();
        Bundle bundle=new Bundle();
        bundle.putString("jsonObj",obj.toString());
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        categoryFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, categoryFragment).addToBackStack(null).commit();





       *//* GroupFragment categoryFragment = new GroupFragment();
        Bundle bundle=new Bundle();
        bundle.putString("jsonObj",obj.toString());
        bundle.putString("PreviousCategoryId",PreviousCategoryId);
        categoryFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, categoryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*//*
    }*/


    private void showGroupFragment(JSONArray obj,String CategoryId) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        GroupFragment fragment = new GroupFragment();
        Bundle bundle=new Bundle();
        bundle.putString("jsonObj",obj.toString());
       // bundle.putString("SubCategoryName",SubCategoryName);
        bundle.putString("PreviousCategoryId",CategoryId);
        fragment.setArguments(bundle);
        ft.replace(R.id.frame, fragment);
        ft.addToBackStack(null);
        ft.commit();



    }

    public void  ShowDynamicAgain(String CategoryId){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DynamicFragment newFragment = new DynamicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("CategoryName_EN","CategoryName_EN");
        bundle.putString("CategoryId",CategoryId);
        newFragment.setArguments(bundle);
        ft.remove(newFragment);
        ft.replace(R.id.frame, newFragment);
        ft.addToBackStack(null);
        ft.commit();
       // setDynamicFragmentToTabLayout(1);
    }

}
