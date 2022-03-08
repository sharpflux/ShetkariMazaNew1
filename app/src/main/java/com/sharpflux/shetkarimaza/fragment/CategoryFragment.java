package com.sharpflux.shetkarimaza.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.activities.Membership;
import com.sharpflux.shetkarimaza.activities.SubscriptionPlanActivity;
import com.sharpflux.shetkarimaza.adapter.BannerAdapter;
import com.sharpflux.shetkarimaza.adapter.HomeSliderAdapter;
import com.sharpflux.shetkarimaza.adapter.MyCategoryTypeAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.ListModel;
import com.sharpflux.shetkarimaza.model.MyCategoryType;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.EndlessRecyclerViewScrollListenerOld;
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
    int currentPage = 1;
    User user;
    RecyclerView recyclerView2;
    ArrayList<ListModel> listModels1;
    public CategoryFragment() {
        // Required empty public constructor
    }
    BannerAdapter bannerAdapter;
    String[] txt_vegType;
    ViewPager viewPager2;
    LinearLayout li_line1;
    LinearLayout li_line2;
    LinearLayout li_line3;
    int CURRENTPAGE = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_categorytype);
        textView_category = view.findViewById(R.id.textView_category);
        mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        this.txt_vegType = new String[]{"VEGETABLE", "VEGETABLE & Fruits", "VEGETABLE", "VEGETABLE,Fruits"};
        this.listModels1 = new ArrayList<>();
        this.recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerBanner);
        this.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.recyclerView2.setItemAnimator(new DefaultItemAnimator());
        this.listModels1 = new ArrayList<>();
        for (String listModel2 : this.txt_vegType) {
            this.listModels1.add(new ListModel(listModel2));
        }
        BannerAdapter bannerAdapter2 = new BannerAdapter(getContext(), this.listModels1);
        this.bannerAdapter = bannerAdapter2;
        this.recyclerView2.setAdapter(bannerAdapter2);


        mydatabase = new dbLanguage(getContext());
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
        categoryList = new ArrayList<>();



        this.li_line1 = (LinearLayout) view.findViewById(R.id.li_line1);
        this.li_line2 = (LinearLayout) view.findViewById(R.id.li_line2);
        this.li_line3 = (LinearLayout) view.findViewById(R.id.li_line3);

        ViewPager viewPager2 = (ViewPager) view.findViewById(R.id.viewPager);
        this.viewPager2 = viewPager2;
        viewPager2.setPageMargin(15);
        this.viewPager2.setPadding(16, 0, 16, 0);
        int i5 = this.CURRENTPAGE;
        if (i5 < 2) {
            int i6 = i5 + 1;
            this.CURRENTPAGE = i6;
            this.viewPager2.setCurrentItem(i6);
            setcompletedStates(this.CURRENTPAGE);
            Log.e("CURRENTPAGE", this.CURRENTPAGE + "");
        }


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

        myLocale = getResources().getConfiguration().locale;


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

        textView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Membership.class);
                getContext().startActivity(intent);
            }
        });
        loadMore(currentPage);

       // AsyncTaskRunner runner = new AsyncTaskRunner();
        //runner.execute("1");


        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListenerOld(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == categoryList.size() - 1) {
                        //bottom of list!
                        currentPage++;
                        loadMore(currentPage);
                        isLoading = true;
                    }
                }



            }
        });


        return view;
    }
    public void setcompletedStates(int CURRENTPAGE2) {
        if (CURRENTPAGE2 == 0) {
            this.li_line1.setBackgroundResource(R.drawable.green_line);
            this.li_line2.setBackgroundResource(R.drawable.circle_white);
            this.li_line3.setBackgroundResource(R.drawable.circle_white);
        }
        if (CURRENTPAGE2 == 1) {
            this.li_line1.setBackgroundResource(R.drawable.circle_white);
            this.li_line2.setBackgroundResource(R.drawable.green_line);
            this.li_line3.setBackgroundResource(R.drawable.circle_white);
        }
        if (CURRENTPAGE2 == 2) {
            this.li_line1.setBackgroundResource(R.drawable.circle_white);
            this.li_line2.setBackgroundResource(R.drawable.circle_white);
            this.li_line3.setBackgroundResource(R.drawable.green_line);
        }
    }

    public class FadeOutTransformation implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position*page.getWidth());
            page.setAlpha(1-Math.abs(position));

        }
    }


    private void loadMore(final Integer currentPage) {


        customDialogLoadingProgressBar.show();
        if (!currentPage.equals(1)) {
            customDialogLoadingProgressBar.dismiss();
            categoryList.add(null);
            myCategoryTypeAdapter.notifyItemInserted(categoryList.size() - 1);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!currentPage.equals(1)) {
                    categoryList.remove(categoryList.size() - 1);
                    int scrollPosition = categoryList.size();
                    myCategoryTypeAdapter.notifyItemRemoved(scrollPosition);
                    final int currentSize = scrollPosition;
                    final int nextLimit = currentSize + 10;
                }


                isLoading = true;
                //SUPPLIED PageSize=99 for detect show the categories whose are usefull for Home Page (IsHomePageCategory)
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        URLs.URL_RType+currentPage+"&PageSize=99&Language="+currentLanguage+"&UserId="+user.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray obj = new JSONArray(response);



                                    for (int i = 0; i < obj.length(); i++) {
                                        JSONObject userJson = obj.getJSONObject(i);

                                        if (!userJson.getBoolean("error")) {


                                            String RegistrationType;

                                            if (userJson.getString("RegistrationTypeId").equals("2")) {
                                                RegistrationType = getResources().getString(R.string.sell);
                                            } else if (userJson.getString("RegistrationTypeId").equals("3")) {
                                                RegistrationType = getResources().getString(R.string.Buy);
                                            } else {
                                                RegistrationType = userJson.getString("RegistrationType");
                                            }

                                            myCategoryType = new MyCategoryType
                                                    (userJson.getString("ImageUrl"),
                                                            RegistrationType,
                                                            userJson.getString("RegistrationTypeId"),
                                                            userJson.getString("UserRegistrationTypeId")
                                                    );
                                            categoryList.add(myCategoryType);
                                        } else {
                                            // Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                        }
                                        myCategoryTypeAdapter = new MyCategoryTypeAdapter(getContext(), categoryList);
                                        mRecyclerView.setAdapter(myCategoryTypeAdapter);
                                    }

                                    myCategoryTypeAdapter.notifyDataSetChanged();
                                    isLoading = false;
                                    customDialogLoadingProgressBar.dismiss();


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
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


            }
        }, 10);


    }






    private void setDynamicFragmentToTabLayout(Integer PageSize) {


    }
    public boolean isAttachedToActivity() {
        boolean attached = isVisible() && getActivity() != null;
        return attached;
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
