package com.sharpflux.shetkarimaza.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.adapter.ContactDetailAdapter;
import com.sharpflux.shetkarimaza.adapter.PostRecyclerAdapter;
import com.sharpflux.shetkarimaza.adapter.RecyclerViewAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.BottomSheetDialogSorting;
import com.sharpflux.shetkarimaza.filters.BottomSheetTransporter;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.model.PostItem;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.EndlessRecyclerViewScrollListener;
import com.sharpflux.shetkarimaza.utils.PaginationListener;
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


import static com.android.volley.Request.Method.GET;
import static com.sharpflux.shetkarimaza.utils.PaginationListener.PAGE_START;

public class TransporterViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

   private RecyclerView recyclerView;
    List<ContactDetail> contactlist;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "",priceids = "", VehicleTypeId = "",StatesID = "", DistrictId = "",SortBy="0";
    boolean isLoading = false;


    private static final String TAG = "MainActivity";


    SwipeRefreshLayout swipeRefresh;
    private PostRecyclerAdapter adapter;
    private boolean isLastPage = false;
    private double totalPage = 10;

    int itemCount = 0;
    private List<PostItem> mPostItems;
    LinearLayoutManager layoutManager;
    private int currentPage = 1;
    private int PageSize=15;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> rowsArrayList = new ArrayList<>();
    TextView txt_emptyView;
    dbFilter myDatabase;
    public  List<ContactDetail> mItemList;
    dbLanguage mydatabaseLanguage;
    Locale myLocale;
    String currentLanguage, language;
    LinearLayout lr_filterbtn;
    TextView ToolbartvItemName, tvRecordsCount;
    ImageView ImgBack2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_view);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(TransporterViewActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        bundle = getIntent().getExtras();


        if (bundle != null) {

            SortBy=bundle.getString("SortBy");

        }


        txt_emptyView = findViewById(R.id.txt_emptyView);
        lr_filterbtn = (LinearLayout) findViewById(R.id.lr_filterbtn);

        txt_emptyView.setVisibility(View.GONE);

        ToolbartvItemName = findViewById(R.id.ToolbartvItemName);
        tvRecordsCount = findViewById(R.id.tvRecordsCount);

        mydatabaseLanguage = new dbLanguage(TransporterViewActivity.this);
        myLocale = getResources().getConfiguration().locale;

        Cursor cursor = mydatabaseLanguage.LanguageGet(language);

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

        myDatabase = new dbFilter(TransporterViewActivity.this);


        mItemList=new ArrayList<>();

        loadMore(currentPage);
        initAdapter();
        initScrollListener();

        ImgBack2 = findViewById(R.id.ImgBack2);


        ImgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.delete();
                Intent intent = new Intent(TransporterViewActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });



        txt_emptyView = findViewById(R.id.txt_emptyView);

        ToolbartvItemName.setText("Transporter");

        View showModalBottomSheet = findViewById(R.id.bottom);
        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetTransporter  bottomSheetDialogFragment = new BottomSheetTransporter();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.setCancelable(true);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        View tv_filter = findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransporterViewActivity.this, Filter1Activity.class);
                intent.putExtra("Activity","TransporterViewActivity");
                intent.putExtra("ProductId",ItemTypeId);
                intent.putExtra("RegistrationSubTypeId","0");
                intent.putExtra("SortBy", SortBy);
                startActivity(intent);
                //finish();
            }
        });




    }

    private void initAdapter() {

        recyclerViewAdapter = new RecyclerViewAdapter(TransporterViewActivity.this,mItemList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    @Override
    public void onBackPressed() {
        myDatabase.delete();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mItemList.size() - 1) {
                        //bottom of list!
                        currentPage++;
                        loadMore(currentPage);
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore(final Integer currentPage) {
        customDialogLoadingProgressBar.show();
        if(!currentPage.equals(1)){
            customDialogLoadingProgressBar.dismiss();
            mItemList.add(null);
            recyclerViewAdapter.notifyItemInserted(mItemList.size() - 1);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!currentPage.equals(1)){
                    mItemList.remove(mItemList.size() - 1);
                    int scrollPosition = mItemList.size();
                    recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                    final int currentSize = scrollPosition;
                    final int nextLimit = currentSize + 10;
                }


                if(bundle.getString("Search")!=null) {
                    if (bundle.getString("Search").contains("Filter")) {
                        Cursor VEHICLETYPECursor = myDatabase.FilterGetByFilterName("VEHICLE");
                        Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                        Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                        Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");


                        priceids = bundle.getString("SortBy");


                        while (VEHICLETYPECursor.moveToNext()) {
                            if(VehicleTypeId==null)
                            {
                                VehicleTypeId="";
                            }
                            VehicleTypeId = VehicleTypeId + VEHICLETYPECursor.getString(0) + ",";
                        }

                        while (STATECursor.moveToNext()) {
                            if(StatesID==null)
                            {
                                StatesID="";
                            }
                            StatesID = StatesID + STATECursor.getString(0) + ",";
                        }

                        if(STATECursor.getCount()==0){
                            myDatabase.DeleteDependantRecord("DISTRICT");
                            myDatabase.DeleteDependantRecord("TALUKA");
                        }
                        while (DISTRICTCursor.moveToNext()) {
                            if(DistrictId==null)
                            {
                                DistrictId="";
                            }
                            DistrictId = DistrictId + DISTRICTCursor.getString(0) + ",";
                        }
                        while (TALUKACursor.moveToNext()) {
                            if(TalukaId==null)
                            {
                                TalukaId="";
                            }
                            TalukaId = TalukaId + TALUKACursor.getString(0) + ",";
                        }
                    }
                }



                if (TalukaId != null) {
                    if (TalukaId.equals(""))
                        TalukaId = "0";
                } else {
                    TalukaId = "0";
                }

                if (VarityId != null) {
                    if (VarityId.equals(""))
                        VarityId = "0";
                } else {
                    VarityId = "0";
                }
                if (QualityId != null) {
                    if (QualityId.equals(""))
                        QualityId = "0";
                } else {
                    QualityId = "0";
                }
                if (StatesID != null) {
                    if (StatesID.equals(""))
                        StatesID = "0";
                } else {
                    StatesID = "0";
                }
                if (DistrictId != null) {
                    if (DistrictId.equals(""))
                        DistrictId = "0";
                } else {
                    DistrictId = "0";
                }
                if (TalukaId != null) {
                    if (TalukaId.equals(""))
                        TalukaId = "0";
                } else {
                    TalukaId = "0";
                }

                if (VehicleTypeId != null) {
                    if (VehicleTypeId.equals(""))
                        VehicleTypeId = "0";
                } else {
                    VehicleTypeId = "0";
                }

                bundle = getIntent().getExtras();
                if (bundle != null) {
                    if(bundle.getString("SortBy")!=null)
                        SortBy = bundle.getString("SortBy");
                }

                StringRequest stringRequest = new StringRequest(GET,URLs.URL_GETTRANSPORTERS +"StartIndex="+currentPage+"&PageSize="+PageSize +"&StateId=" + StatesID + "&DistrictId=" + DistrictId + "&TalukaId=" + TalukaId + "&VehicleTypeId="+VehicleTypeId+ "&Language="+ currentLanguage+"&SortByRate="+SortBy,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray   obj = new JSONArray(response);

                                    for (int i = 0; i < obj.length(); i++) {
                                        JSONObject userJson = obj.getJSONObject(i);

                                        if (!userJson.getBoolean("error")) {
                                            ContactDetail detail;
                                            detail = new ContactDetail
                                                    (
                                                            "",
                                                            userJson.getString("VehicalType"),
                                                            userJson.getString("Address"),
                                                            userJson.getString("MobileNo"),
                                                            userJson.getString("StatesName"),
                                                            userJson.getString("DistrictName"),
                                                            userJson.getString("TalukaName"),
                                                            userJson.getString("FullName"),
                                                            userJson.getString("VehicalNo"),
                                                            userJson.getString("Rates"),
                                                            "27"
                                                    );

                                            mItemList.add(detail);


                                        }

                                        recyclerViewAdapter.notifyDataSetChanged();
                                        isLoading = false;
                                        if (obj.length() > 0) {
                                            tvRecordsCount.setText(obj.getJSONObject(0).getString("totalCount").toString() + " " + getResources().getString(R.string.recordsfound) + " | " + getResources().getString(R.string.showing) + String.valueOf(mItemList.size()));
                                        } else {

                                        }
                                    }

                                } catch (JSONException e) {
                                    customDialogLoadingProgressBar.dismiss();
                                    e.printStackTrace();
                                }
                                if(mItemList.size()==0){
                                    txt_emptyView.setVisibility(View.VISIBLE);
                                }
                                else {
                                    txt_emptyView.setVisibility(View.GONE);
                                }
                                lr_filterbtn.setVisibility(View.VISIBLE);
                                customDialogLoadingProgressBar.dismiss();
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

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            }
        }, 500);


    }



    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
       // doApiCall();
       // SetDynamicDATA(currentPage);
    }




}