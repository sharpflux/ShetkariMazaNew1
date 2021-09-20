package com.sharpflux.shetkarimaza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.adapter.GovtDeptAdapter;
import com.sharpflux.shetkarimaza.adapter.GovtSchemeAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GovtSchemes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContactDetail> contactlist;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "",SortBy="0";

    boolean isLoading = false;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    GovtSchemeAdapter myAdapter;
    private int currentPage = PAGE_START;
    private boolean isFirstLoad = false;
    private int totalPage = 10;
    int itemCount = 0;
    ProgressBar progressBar_filter;
    Locale myLocale;
    JSONArray obj;
    AlertDialog.Builder builder;
    TextView txt_emptyView;
    LinearLayout lr_filterbtn;
    List<ContactDetail> mList;
    private int PageSize=15;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    private RecyclerView
            recyclerView_addFarm;
    public static final int PAGE_START = 1;
    private static final int PAGE_SIZE = 10;
    private AddPersonAdapter addPersonAdapter_farm;
    private ArrayList<AddPersonModel> addPersonModelArrayList_farm;
    ImageView imageView_addFarm;
    dbFilter myDatabase;
    dbLanguage mydatabase;
    String currentLanguage,language;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_schemes);
        setTitle("Govt Schemes");


        recyclerView = findViewById(R.id.contact_Detail_rvProductList2);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(GovtSchemes.this);
        contactlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        myDatabase = new dbFilter(GovtSchemes.this);
        addPersonModelArrayList_farm = new ArrayList<>();


        recyclerView.setAdapter(myAdapter);


        mydatabase = new dbLanguage(getApplicationContext());
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
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

        progressBar_filter = findViewById(R.id.progressBar_filter);
        //txt_emptyView = findViewById(R.id.txt_emptyView);
        lr_filterbtn = (LinearLayout) findViewById(R.id.lr_filterbtn);

//        txt_emptyView.setVisibility(View.GONE);

        imageView_addFarm = findViewById(R.id.imageView_addFarm);
      /*  if(mList.size()==0)
        {
            lr_filterbtn.setVisibility(View.GONE);
        }*/

        GovtSchemes.AsyncTaskRunner runner = new GovtSchemes.AsyncTaskRunner();
        String sleepTime = "1";
        runner.execute(sleepTime);

        setTitle("Schemes");
        initAdapter();
        //  initScrollListener();
        myLocale = getResources().getConfiguration().locale;
        View showModalBottomSheet = findViewById(R.id.bottom);

        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  BottomSheetDialogSorting bottomSheetDialogFragment = new BottomSheetDialogSorting();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.setCancelable(true);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());*/
            }
        });

        View tv_filter = findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GovtSchemes.this, Filter1Activity.class);
                intent.putExtra("Activity","ContactDetailActivity");
                intent.putExtra("ProductId",ItemTypeId);
                startActivity(intent);
            }
        });




    }


    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mList.size() - 1) {
                        //bottom of list!
                        currentPage++;
                        loadMore(currentPage);
                        isLoading = true;
                    }
                }
            }
        });


    }
    private void initAdapter() {
        myAdapter = new GovtSchemeAdapter(GovtSchemes.this, mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }
    private void loadMore(final Integer currentPage) {
        customDialogLoadingProgressBar.show();
        if(!currentPage.equals(1)){
            customDialogLoadingProgressBar.dismiss();
            mList.add(null);
            myAdapter.notifyItemInserted(mList.size() - 1);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!currentPage.equals(1)) {
                    mList.remove(mList.size() - 1);
                    int scrollPosition = mList.size();
                    myAdapter.notifyItemRemoved(scrollPosition);
                    final int currentSize = scrollPosition;
                    final int nextLimit = currentSize + 10;
                }

                SetDynamicDATA(currentPage);

            }
        }, 500);
    }

    private void SetDynamicDATA(Integer currentPage) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GovtDepartmentGet +  "?Language="+currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            obj = new JSONArray(response);
                            isFirstLoad = true;
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    ContactDetail detail;
                                    detail = new ContactDetail
                                            (
                                                    "",
                                                    userJson.getString("DepartmentName"),
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    ""
                                            );

                                    mList.add(detail);
                                    mList.size();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myAdapter.notifyDataSetChanged();
                                isLoading = false;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*    if(mList.size()==0){
                                txt_emptyView.setVisibility(View.VISIBLE);
                            }
                            else {
                                txt_emptyView.setVisibility(View.GONE);

                            }
                            lr_filterbtn.setVisibility(View.VISIBLE);*/
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


                SetDynamicDATA(Integer.parseInt(params[0]));


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(GovtSchemes.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

    }
}