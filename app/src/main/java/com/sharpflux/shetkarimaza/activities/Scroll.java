package com.sharpflux.shetkarimaza.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.ContactAdapter;
import com.sharpflux.shetkarimaza.adapter.PostRecyclerAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.PostItem;
import com.sharpflux.shetkarimaza.model.UserModal2;
import com.sharpflux.shetkarimaza.model.contacts;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.utils.PaginationListener;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sharpflux.shetkarimaza.Interface.PaginationListener.PAGE_START;

public class Scroll extends AppCompatActivity   implements SwipeRefreshLayout.OnRefreshListener {

    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    private static final String TAG = "MainActivity";
    Locale myLocale;
    RecyclerView mRecyclerView;

    SwipeRefreshLayout swipeRefresh;
    private ContactAdapter adapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 50;
    private boolean isLoading = false;
    int itemCount = 0;
    TextView ToolbartvItemName, tvRecordsCount,tvError;
    dbFilter myDatabase;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "",SortBy="0",RegistrationSubTypeId="0",CategoryName="0",Latitude="0",Longitude="0";
    boolean UseLatLong=false;
    Boolean IsSubCategory;
    TextView tvViewing;
    ImageView ImgBack2;
    LinearLayout error_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        mRecyclerView=findViewById(R.id.recyclerView);
        swipeRefresh=findViewById(R.id.swipeRefresh);
        myDatabase = new dbFilter(Scroll.this);
        ImgBack2 = findViewById(R.id.ImgBack2);

        ToolbartvItemName = findViewById(R.id.ToolbartvItemName);
        tvRecordsCount = findViewById(R.id.tvRecordsCount);
        tvError=findViewById(R.id.tvError);
        error_layout=findViewById(R.id.error_layout);




        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(Scroll.this,new ArrayList<>());
        mRecyclerView.setAdapter(adapter);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(Scroll.this);
        customDialogLoadingProgressBar.show();
        bundle = getIntent().getExtras();
        tvViewing=findViewById(R.id.tvViewing);
        tvRecordsCount=findViewById(R.id.tvRecordsCount);
        //  bundle.getString("ProductId");
        if (bundle != null) {
            ItemTypeId = bundle.getString("ProductId");
            TalukaId = bundle.getString("TalukaId");
            VarityId = bundle.getString("VarietyId");
            QualityId = bundle.getString("QualityId");
            StatesID = bundle.getString("StatesID");
            DistrictId = bundle.getString("DistrictId");
            RegistrationSubTypeId= bundle.getString("RegistrationSubTypeId");
            IsSubCategory=bundle.getBoolean("IsSubCategory");
            RegistrationSubTypeId= bundle.getString("RegistrationSubTypeId");
            CategoryName= bundle.getString("Name");

            ToolbartvItemName.setText(bundle.getString("Heading"));
            setTitle(bundle.getString("Heading"));
        }



        SetDynamicDATA(1);
        ImgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.delete();
                Intent intent = new Intent(Scroll.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {


            @Override
            protected void Scrolling() {
                int firstElementPosition=layoutManager.findFirstVisibleItemPosition();
                int lastElementPosition=layoutManager.findLastVisibleItemPosition();
                tvViewing.setText( getResources().getString(R.string.viewing)+  String.valueOf( firstElementPosition+1) +" - "+String.valueOf(  lastElementPosition+1));
            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();

            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }


        });

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
                Intent intent = new Intent(Scroll.this, Filter1Activity.class);
                intent.putExtra("Activity","ContactDetailActivity");
                intent.putExtra("ProductId",ItemTypeId);
                intent.putExtra("RegistrationSubTypeId",RegistrationSubTypeId);
                startActivity(intent);
                //   finish();
            }
        });
    }
    private void doApiCall() {
        final ArrayList<PostItem> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SetDynamicDATA(currentPage);
            }
        }, 500);
    }

    private void SetDynamicDATA(Integer currentPage) {
        if (bundle != null) {
            if (bundle.getString("Search") != null) {
                if (bundle.getString("Search").contains("Filter")) {
                    Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                    Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                    Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");

                    UseLatLong = bundle.getBoolean("UseLatLong");
                    RegistrationSubTypeId = bundle.getString("RegistrationSubTypeId");

                    while (STATECursor.moveToNext()) {
                        if (StatesID == null) {
                            StatesID = "";
                        }
                        StatesID = StatesID + STATECursor.getString(0) + ",";
                    }

                    if (STATECursor.getCount() == 0) {
                        myDatabase.DeleteDependantRecord("DISTRICT");
                        myDatabase.DeleteDependantRecord("TALUKA");
                    }
                    while (DISTRICTCursor.moveToNext()) {
                        if (DistrictId == null) {
                            DistrictId = "";
                        }
                        DistrictId = DistrictId + DISTRICTCursor.getString(0) + ",";
                    }
                    while (TALUKACursor.moveToNext()) {
                        if (TalukaId == null) {
                            TalukaId = "";
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
            if (UseLatLong) {
                if (Latitude != null) {
                    if (Latitude.equals(""))
                        Latitude = "0";
                } else {
                    Latitude = "0";
                }

                if (Longitude != null) {
                    if (Longitude.equals(""))
                        Longitude = "0";
                } else {
                    Longitude = "0";
                }
            } else {
                Latitude = "0";
                Longitude = "0";
            }

            if (RegistrationSubTypeId != null) {
                if (RegistrationSubTypeId.equals(""))
                    RegistrationSubTypeId = "0";
            } else {
                RegistrationSubTypeId = "0";
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_CONTACTDET + "StartIndex="+currentPage+"&PageSize="+totalPage + "&RegistrationTypeId=" + ItemTypeId  + "&RegistrationSubTypeId=" + RegistrationSubTypeId+ "&StateId=" + StatesID + "&DistrictId=" + DistrictId + "&TalukaId=" + TalukaId + "&Language=en"+"&Lat=" + Latitude+"&Long=" + Longitude,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject obj = new JSONObject(response);
                                JSONArray array = obj.getJSONArray("results");


                                final ArrayList<contacts> items = new ArrayList<>();


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject userJson = array.getJSONObject(i);

                                    itemCount++;
                                    contacts postItem = new contacts();
                                    postItem.setImage(userJson.getString("UserId"));
                                    postItem.setFullName(userJson.getString("FullName"));
                                    postItem.setAddress(userJson.getString("Address"));
                                    postItem.setMobileNo(userJson.getString("MobileNo"));
                                    postItem.setState(userJson.getString("StatesName"));
                                    postItem.setDistrict(userJson.getString("DistrictName"));
                                    postItem.setTaluka(userJson.getString("TalukaName"));
                                    items.add(postItem);


                                }


                                tvRecordsCount.setText(obj.getString("total_results")+" Rocord found");
                                if (currentPage != PAGE_START) adapter.removeLoading();
                                adapter.addItems(items);
                                swipeRefresh.setRefreshing(false);
                                // check weather is last page or not
                                if (currentPage < totalPage) {
                                    adapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;

                                if(obj.getString("total_results").equals("0")){
                                    adapter.removeLoading();
                                    error_layout.setVisibility(View.VISIBLE);
                                    tvError.setText(getResources().getString (R.string.error_msg_no_data));
                                }
                                else {
                                    error_layout.setVisibility(View.GONE);
                                    tvError.setText(getResources().getString (R.string.error_msg_no_data));
                                }



                                customDialogLoadingProgressBar.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Scroll.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("UserId", "");
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(Scroll.this).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        doApiCall();
    }
}