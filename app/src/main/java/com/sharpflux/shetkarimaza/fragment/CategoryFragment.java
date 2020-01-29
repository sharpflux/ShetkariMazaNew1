package com.sharpflux.shetkarimaza.fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    MyCategoryTypeAdapter myCategoryTypeAdapter;
    ArrayList<MyCategoryType> categoryList;
    Locale myLocale;
    String language;
    MyCategoryType myCategoryType;
    dbLanguage mydatabase;
    String currentLanguage;
    boolean isLoading = false;
    ProgressBar progressBar;

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

   // ShimmerFrameLayout parentShimmerLayout;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_categorytype);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mydatabase = new dbLanguage(getContext());

        categoryList = new ArrayList<>();
       // parentShimmerLayout =view.findViewById(R.id.shimmer_view_container);

     /*   myLocale = getResources().getConfiguration().locale;
        Context context = getContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "shetkariMaza", Context.MODE_PRIVATE);
        String language = sharedPref.getString("KEY_LANGUAGE", null);*/




        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();
        progressBar = view.findViewById(R.id.progressBar);
        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }



       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mGridLayoutManager.getChildCount();
                totalItems = mGridLayoutManager.getItemCount();
                scrollOutItems = mGridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
                    setDynamicFragmentToTabLayout();


                }
            }
        });*/

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("1");


        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                isLoading = true;
                int currentSize = myCategoryTypeAdapter.getItemCount();



                CategoryFragment.AsyncTaskRunner runner = new CategoryFragment.AsyncTaskRunner();
                String sleepTime = String.valueOf(page+1);
                runner.execute(sleepTime);


            }
        });






        //parentShimmerLayout.startShimmerAnimation();
      // setDynamicFragmentToTabLayout();

        return view;
    }








    private void setDynamicFragmentToTabLayout(Integer PageSize) {

       StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_RType+PageSize+"&PageSize=15&Language="+currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);


                            progressBar.setVisibility(View.VISIBLE);

                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {
                                    myCategoryType = new MyCategoryType
                                            (       userJson.getString("ImageUrl"),
                                                    userJson.getString("RegistrationType"),
                                                    userJson.getString("RegistrationTypeId"));


                                    categoryList.add(myCategoryType);
                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myCategoryTypeAdapter = new MyCategoryTypeAdapter(getContext(), categoryList);
                                mRecyclerView.setAdapter(myCategoryTypeAdapter);



                                /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                    }

                                    @Override
                                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);

                                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                                        if (!isLoading) {
                                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == categoryList.size() - 1) {

                                                categoryList.add(null);
                                                myCategoryTypeAdapter.notifyItemInserted(categoryList.size() - 1);

                                                Handler handler = new Handler();

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        categoryList.remove(categoryList.size() - 1);
                                                        int scrollPosition = categoryList.size();
                                                        myCategoryTypeAdapter.notifyItemRemoved(scrollPosition);
                                                        int currentSize = scrollPosition;
                                                        int nextLimit = currentSize + 3;

                                                        while (currentSize - 1 < nextLimit) {
                                                            // productlist.add(sellOptions);
                                                            currentSize++;
                                                        }

                                                        myCategoryTypeAdapter.notifyDataSetChanged();
                                                        isLoading = false;
                                                    }
                                                }, 1000);

                                                isLoading = true;
                                            }
                                        }
                                    }
                                });*/






                            }

                            myCategoryTypeAdapter.notifyDataSetChanged();
                            isLoading = false;
                            progressBar.setVisibility(View.GONE);



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

               /* setDynamicFragmentToTabLayout();
                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";*/

                /*int time = Integer.parseInt(params[0]) * 1000;
                setDynamicFragmentToTabLayout();
                Thread.sleep(time);
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

    @Override
    public void onResume() {
        super.onResume();
      //  parentShimmerLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
       // parentShimmerLayout.stopShimmerAnimation();
    }
}
