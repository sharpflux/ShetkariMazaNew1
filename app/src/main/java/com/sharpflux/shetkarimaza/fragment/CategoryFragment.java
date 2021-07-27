package com.sharpflux.shetkarimaza.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.PaymentActivity;
import com.sharpflux.shetkarimaza.activities.SubscriptionPlanActivity;
import com.sharpflux.shetkarimaza.adapter.HomeSliderAdapter;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
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

import me.relex.circleindicator.CircleIndicator;

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
    private TextView textView_category;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    private ViewPager viewPager;
    Handler handler;
   // ShimmerFrameLayout parentShimmerLayout;
   Runnable runnable_viewPager;
    int currentPage = 0;
    User user;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_categorytype);
        textView_category = view.findViewById(R.id.textView_category);
        mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mydatabase = new dbLanguage(getContext());
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
        categoryList = new ArrayList<>();
       // parentShimmerLayout =view.findViewById(R.id.shimmer_view_container);

     /*   myLocale = getResources().getConfiguration().locale;
        Context context = getContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "shetkariMaza", Context.MODE_PRIVATE);
        String language = sharedPref.getString("KEY_LANGUAGE", null);*/

        viewPager = view.findViewById(R.id.viewpager);
        CircleIndicator indicator = view.findViewById(R.id.indicator);
        HomeSliderAdapter viewPagerAdapter = new HomeSliderAdapter(getContext());
        viewPager.setPageTransformer(false, new FadeOutTransformation());
        viewPager.setAdapter(viewPagerAdapter);
      //  scrollView.scrollTo(0, viewPager.getTop());

        handler = new Handler();
        runnable_viewPager = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                else
                {
                    currentPage++;
                }
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this,3*1000);
            }
        };


         user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();
        progressBar = view.findViewById(R.id.progressBar);
        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        textView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SubscriptionPlanActivity.class);
                getContext().startActivity(intent);
            }
        });

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


    public class FadeOutTransformation implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position*page.getWidth());
            page.setAlpha(1-Math.abs(position));

        }
    }

    private void setDynamicFragmentToTabLayout(Integer PageSize) {

       StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_RType+PageSize+"&PageSize=15&Language="+currentLanguage+"&UserId="+user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);



                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                   // String imageUrl = userJson.getString("ImageUrl")
                                    //       .substring(oldAPI.length(), userJson.getString("ImageUrl").length());

                                        myCategoryType = new MyCategoryType
                                            (      userJson.getString("ImageUrl"),
                                                    userJson.getString("RegistrationType"),
                                                    userJson.getString("RegistrationTypeId"),
                                                    userJson.getString("UserRegistrationTypeId")
                                            );
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
                            customDialogLoadingProgressBar.dismiss();


                          /*  parentShimmerLayout.stopShimmerAnimation();
                            parentShimmerLayout.setVisibility(View.GONE);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                            customDialogLoadingProgressBar.dismiss();
                        }
                    }
                },
//
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customDialogLoadingProgressBar.dismiss();
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
                setDynamicFragmentToTabLayout(Integer.valueOf( params[0]));
                Thread.sleep(5);

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
          //  progressDialog.dismiss();
            // finalResult.setText(result);
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
