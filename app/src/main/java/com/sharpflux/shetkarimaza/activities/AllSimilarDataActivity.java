package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.RecyclerViewAdapter;
import com.sharpflux.shetkarimaza.adapter.SimilarListAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.BottomSheetDialogSorting;
import com.sharpflux.shetkarimaza.filters.BuyerFilterActivity;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.filters.PriceFragment;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.service.MyJobService;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import static com.android.volley.Request.Method.GET;
import static com.sharpflux.shetkarimaza.utils.PaginationListener.PAGE_START;

public class AllSimilarDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;
    Bundle bundle;

    String TalukaId = "", VarityId = "", AvailableMonth = "", Age = "",QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "", priceids = "", ItemName, categoryId = "";
    boolean IsVarietyAvailable;
    boolean isLoading = false;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    SimilarListAdapter myAdapter;
    private int currentPage = 1;
    private boolean isFirstLoad = false;
    private int totalPage = 10;
    int itemCount = 0;
    ProgressBar progressBar_filter;
    Locale myLocale;
    dbBuyerFilter myDatabaseBuyer;
    TextView txt_emptyView;
    LinearLayout lr_filterbtn;
    public static final int PAGE_START = 1;
    private static final int PAGE_SIZE = 50;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    dbLanguage mydatabase;
    String currentLanguage, language;

    private int messageCount = 0;
    private static Uri alarmSound;
    // Vibration pattern long array
    private final long[] pattern = {100, 300, 300, 300};
    private NotificationManager mNotificationManager;

    androidx.appcompat.widget.Toolbar toolbarData;
    TextView ToolbartvItemName, tvRecordsCount;
    List<SimilarList> mList;
    ImageView ImgBack2, ImgdownloadExcel;
    TextView tvViewing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_similar_data);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(AllSimilarDataActivity.this);

        recyclerView = findViewById(R.id.similar_rvProductList);
        productlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myDatabaseBuyer = new dbBuyerFilter(getApplicationContext());


        // DEFAULT ALARM SOUND
        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // INITIALIZE NOTIFICATION MANAGER
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //toolbarData = findViewById(R.id.toolbarData);
        ToolbartvItemName = findViewById(R.id.ToolbartvItemName);
        tvRecordsCount = findViewById(R.id.tvRecordsCount);
        ImgBack2 = findViewById(R.id.ImgBack2);
        ImgdownloadExcel = findViewById(R.id.ImgdownloadExcel);


        txt_emptyView = findViewById(R.id.txt_emptyView);
        lr_filterbtn = (LinearLayout) findViewById(R.id.lr_filterbtn);

        tvViewing=findViewById(R.id.tvViewing);

        txt_emptyView.setVisibility(View.GONE);

        progressBar_filter = findViewById(R.id.progressBar_filter);

        recyclerView = findViewById(R.id.similar_rvProductList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        ImgdownloadExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadExcel();
            }
        });

        ImgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllSimilarDataActivity.this, BuyerActivity.class);
                startActivity(intent);
                finish();
            }
        });


        myLocale = getResources().getConfiguration().locale;

        mydatabase = new dbLanguage(getApplicationContext());

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);

        if (cursor.getCount() == 0) {
            currentLanguage = "en";
        } else {
            while (cursor.moveToNext()) {
                currentLanguage = cursor.getString(0);
                if (currentLanguage == null) {
                    currentLanguage = "en";
                }

            }
        }

        AssignVariables();
        BundleAssign();

        ToolbartvItemName.setText(ItemName);
        // setTitle(ItemName);
        mList = new ArrayList<>();
        loadMore(currentPage);
        initAdapter();
        initScrollListener();

        if (mList.size() == 0) {
            lr_filterbtn.setVisibility(View.GONE);
        }


        View showModalBottomSheet = findViewById(R.id.bottom);
        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
                Intent resultIntent = new Intent(AllSimilarDataActivity.this, HomeActivity.class);


                showNotification(AllSimilarDataActivity.this,"Testing", "Body",resultIntent);*/


                BottomSheetDialogSorting bottomSheetDialogFragment = new BottomSheetDialogSorting();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.setCancelable(true);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        View tv_filter = findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleAssign();
                Intent intent = new Intent(AllSimilarDataActivity.this, BuyerFilterActivity.class);
                intent.putExtra("ItemTypeId", ItemTypeId);
                intent.putExtra("TalukaId", TalukaId);
                intent.putExtra("VarietyId", VarityId);
                intent.putExtra("QualityId", QualityId);
                intent.putExtra("StatesID", StatesID);
                intent.putExtra("DistrictId", DistrictId);
                intent.putExtra("priceids", priceids);
                intent.putExtra("IsVarietyAvailable", IsVarietyAvailable);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("ItemName", ItemName);
                startActivity(intent);
            }
        });


    }

    private void initAdapter() {
        myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, mList);
        recyclerView.setAdapter(myAdapter);
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

                int firstElementPosition=linearLayoutManager.findFirstVisibleItemPosition();
                int lastElementPosition=linearLayoutManager.findLastVisibleItemPosition();
                tvViewing.setText( getResources().getString(R.string.viewing)+  String.valueOf( firstElementPosition+1) +" - "+String.valueOf( lastElementPosition+1));
            }
        });


    }


    private void BundleAssign() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            ItemTypeId = bundle.getString("ItemTypeId");
            ItemName = bundle.getString("ItemName");
            categoryId = bundle.getString("categoryId");
            IsVarietyAvailable = bundle.getBoolean("IsVarietyAvailable");
            //TalukaId = bundle.getString("TalukaId");
            //VarityId = bundle.getString("VarietyId");
            // QualityId = bundle.getString("QualityId");
            //StatesID = bundle.getString("StatesID");
            //DistrictId = bundle.getString("DistrictId");
            //priceids=bundle.getString("priceids");

        }

    }

    private void AssignVariables() {
        ItemTypeId = "";
        TalukaId = "";
        VarityId = "";
        QualityId = "";
        StatesID = "";
        DistrictId = "";
        priceids = "";
    }


    private void loadMore(final Integer currentPage) {


        customDialogLoadingProgressBar.show();
        if (!currentPage.equals(1)) {
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


                bundle = getIntent().getExtras();

                AssignVariables();
                if (bundle != null) {

                    BundleAssign();

                    if (bundle.getString("Search") != null) {
                        if (bundle.getString("Search").contains("Filter")) {
                            Cursor VARIETYCursor = myDatabaseBuyer.FilterGetByFilterName("VARIETY");
                            Cursor QUALITYCursor = myDatabaseBuyer.FilterGetByFilterName("QUALITY");
                            Cursor STATECursor = myDatabaseBuyer.FilterGetByFilterName("STATE");
                            Cursor DISTRICTCursor = myDatabaseBuyer.FilterGetByFilterName("DISTRICT");
                            Cursor TALUKACursor = myDatabaseBuyer.FilterGetByFilterName("TALUKA");
                            Cursor AVAILABLEMONTHCursor = myDatabaseBuyer.FilterGetByFilterName("AVAILABLEMONTH");
                            Cursor AGECursor = myDatabaseBuyer.FilterGetByFilterName("AGE");

                            priceids = bundle.getString("SortBy");


                            while (AVAILABLEMONTHCursor.moveToNext()) {
                                if (AvailableMonth == null) {
                                    AvailableMonth = "";
                                }
                                AvailableMonth = AvailableMonth + AVAILABLEMONTHCursor.getString(0) + ",";
                            }


                            priceids = bundle.getString("SortBy");


                            while (VARIETYCursor.moveToNext()) {
                                if (VarityId == null) {
                                    VarityId = "";
                                }
                                VarityId = VarityId + VARIETYCursor.getString(0) + ",";
                            }
                            while (QUALITYCursor.moveToNext()) {
                                if (QualityId == null) {
                                    QualityId = "";
                                }
                                QualityId = QualityId + QUALITYCursor.getString(0) + ",";
                            }
                            while (STATECursor.moveToNext()) {
                                StatesID = StatesID + STATECursor.getString(0) + ",";
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


                            while (AGECursor.moveToNext()) {
                                if (Age == null) {
                                    Age = "";
                                }
                                Age = Age + AGECursor.getString(0) + ",";
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
                    if (!priceids.equals(null)) {
                        if (priceids.equals(""))
                            priceids = "0";
                    } else if (priceids.equals(null)) {
                        priceids = "0";
                    }
                    if (AvailableMonth != null) {
                        if (AvailableMonth.equals(""))
                            AvailableMonth = "0";
                    } else {
                        AvailableMonth = "0";
                    }

                    if (Age != null) {
                        if (Age.equals(""))
                            Age = "0";
                    } else {
                        Age = "0";
                    }

                    if (ItemTypeId == null) {
                        Toast.makeText(AllSimilarDataActivity.this, "ITEM TYPE IS NULL", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            URLs.URL_REQESTS + "?StartIndex=" + currentPage + "&PageSize=" + PAGE_SIZE +
                                    "&ItemTypeId=" + ItemTypeId + "&VarityId=" + VarityId + "&StateId=" + StatesID +
                                    "&DistrictId=" + DistrictId + "&QualityId=" + QualityId + "&TalukaId="
                                    + TalukaId + "&Language=" + currentLanguage + "&SortByRate=" + priceids + "&AvailableMonths=" + AvailableMonth+ "&Age=" + Age,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray obj = new JSONArray(response);


                                        isFirstLoad = true;
                                        for (int i = 0; i < obj.length(); i++) {
                                            JSONObject userJson = obj.getJSONObject(i);
                                            if (!userJson.getBoolean("error")) {
                                                SimilarList sellOptions;
                                                sellOptions = new SimilarList
                                                        (
                                                                userJson.getString("ImageUrl"),
                                                                userJson.getString("FullName"),
                                                                userJson.getString("MobileNo"),
                                                                userJson.getString("ItemName"),
                                                                userJson.getString("VarietyName"),
                                                                userJson.getString("QualityType"),
                                                                String.valueOf(userJson.getDouble("AvailableQuantity")),
                                                                userJson.getString("MeasurementType"),
                                                                String.valueOf(userJson.getDouble("ExpectedPrice")),
                                                                userJson.getString("AvailableMonths"),
                                                                userJson.getString("FarmAddress"),
                                                                userJson.getString("StatesName"),
                                                                userJson.getString("DistrictName"),
                                                                userJson.getString("TalukaName"),
                                                                userJson.getString("VillageName"),
                                                                userJson.getString("Hector"),
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "",
                                                                "0",
                                                                userJson.getString("CategoryName_EN"),
                                                                userJson.getString("Organic"),
                                                                userJson.getString("OrganicCertiicateNo"),
                                                                String.valueOf(userJson.getDouble("PerUnitPrice"))
                                                        );
                                                mList.add(sellOptions);
                                                //

                                            } else {
                                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                            }

                                            myAdapter.notifyDataSetChanged();

                                            //tvRecordsCount.setText( obj.getJSONObject(FIRST_ELEMENT).toString()+getResources().getString( R.string.recordsfound ));

                                        }


                                        if (obj.length() > 0) {
                                            tvRecordsCount.setText(obj.getJSONObject(0).getString("totalCount").toString() + " " + getResources().getString(R.string.recordsfound) + " | " + getResources().getString(R.string.showing) + String.valueOf(mList.size()));
                                        } else {

                                        }

                                        if (mList.size() == 0) {
                                            txt_emptyView.setVisibility(View.VISIBLE);
                                        } else {
                                            txt_emptyView.setVisibility(View.GONE);
                                        }
                                        lr_filterbtn.setVisibility(View.VISIBLE);
                                        customDialogLoadingProgressBar.dismiss();
                                        isLoading = false;

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        customDialogLoadingProgressBar.dismiss();
                                        if (myAdapter.getItemCount() == 0) {
                                            txt_emptyView.setVisibility(View.VISIBLE);
                                        } else {
                                            txt_emptyView.setVisibility(View.GONE);

                                        }
                                        lr_filterbtn.setVisibility(View.VISIBLE);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    customDialogLoadingProgressBar.dismiss();
                                    if (myAdapter.getItemCount() == 0) {
                                        txt_emptyView.setVisibility(View.VISIBLE);
                                    } else {
                                        txt_emptyView.setVisibility(View.GONE);

                                    }
                                    lr_filterbtn.setVisibility(View.VISIBLE);
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


            }
        }, 500);


    }


    public void SetDynamicDATA(String pageIndex) {

        bundle = getIntent().getExtras();

        AssignVariables();
        if (bundle != null) {

            BundleAssign();

            if (bundle.getString("Search") != null) {
                if (bundle.getString("Search").contains("Filter")) {

                    Cursor AVAILABLEMONTHCursor = myDatabaseBuyer.FilterGetByFilterName("AVAILABLEMONTH");
                    Cursor VARIETYCursor = myDatabaseBuyer.FilterGetByFilterName("VARIETY");
                    Cursor QUALITYCursor = myDatabaseBuyer.FilterGetByFilterName("QUALITY");
                    Cursor STATECursor = myDatabaseBuyer.FilterGetByFilterName("STATE");
                    Cursor DISTRICTCursor = myDatabaseBuyer.FilterGetByFilterName("DISTRICT");
                    Cursor TALUKACursor = myDatabaseBuyer.FilterGetByFilterName("TALUKA");

                    priceids = bundle.getString("SortBy");


                    while (AVAILABLEMONTHCursor.moveToNext()) {
                        if (AvailableMonth == null) {
                            AvailableMonth = "";
                        }
                        AvailableMonth = AvailableMonth + AVAILABLEMONTHCursor.getString(0) + ",";
                    }


                    while (VARIETYCursor.moveToNext()) {
                        if (VarityId == null) {
                            VarityId = "";
                        }
                        VarityId = VarityId + VARIETYCursor.getString(0) + ",";
                    }
                    while (QUALITYCursor.moveToNext()) {
                        if (QualityId == null) {
                            QualityId = "";
                        }
                        QualityId = QualityId + QUALITYCursor.getString(0) + ",";
                    }
                    while (STATECursor.moveToNext()) {
                        StatesID = StatesID + STATECursor.getString(0) + ",";
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
            if (priceids != null) {
                if (priceids.equals(""))
                    priceids = "0";
            } else {
                priceids = "0";
            }

            if (AvailableMonth != null) {
                if (AvailableMonth.equals(""))
                    AvailableMonth = "0";
            } else {
                AvailableMonth = "0";
            }


            if (ItemTypeId == null) {
                Toast.makeText(AllSimilarDataActivity.this, "ITEM TYPE IS NULL", Toast.LENGTH_SHORT).show();
                return;
            }


            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_REQESTS + "?StartIndex=" + pageIndex + "&PageSize=" + PAGE_SIZE +
                            "&ItemTypeId=" + ItemTypeId + "&VarityId=" + VarityId + "&StateId=" + StatesID +
                            "&DistrictId=" + DistrictId + "&QualityId=" + QualityId + "&TalukaId="
                            + TalukaId + "&Language=" + currentLanguage + "&SortByRate=" + priceids + "&AvailableMonths=" + AvailableMonth,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray obj = new JSONArray(response);
                                isFirstLoad = true;
                                for (int i = 0; i < obj.length(); i++) {
                                    JSONObject userJson = obj.getJSONObject(i);
                                    if (!userJson.getBoolean("error")) {
                                        SimilarList sellOptions;
                                        sellOptions = new SimilarList
                                                (
                                                        userJson.getString("ImageUrl"),
                                                        userJson.getString("FullName"),
                                                        userJson.getString("MobileNo"),
                                                        userJson.getString("ItemName"),
                                                        userJson.getString("VarietyName"),
                                                        userJson.getString("QualityType"),
                                                        String.valueOf(userJson.getDouble("AvailableQuantity")),
                                                        userJson.getString("MeasurementType"),
                                                        String.valueOf(userJson.getDouble("ExpectedPrice")),
                                                        userJson.getString("AvailableMonths"),
                                                        userJson.getString("FarmAddress"),
                                                        userJson.getString("StatesName"),
                                                        userJson.getString("DistrictName"),
                                                        userJson.getString("TalukaName"),
                                                        userJson.getString("VillageName"),
                                                        userJson.getString("Hector"),
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "0",
                                                        userJson.getString("CategoryName_EN"),
                                                        userJson.getString("Organic"),
                                                        userJson.getString("OrganicCertiicateNo"),
                                                        String.valueOf(userJson.getDouble("PerUnitPrice"))
                                                );
                                        sellOptions.setEmail(userJson.getString("EmailId"));
                                        productlist.add(sellOptions);
                                        productlist.size();

                                    } else {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                    }

                                    myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, productlist);
                                    recyclerView.setAdapter(myAdapter);
                                    customDialogLoadingProgressBar.dismiss();
                                }
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
    }

    class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                SetDynamicDATA(params[0]);
                Thread.sleep(100);

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
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(AllSimilarDataActivity.this);
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

    }


    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_download, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.download) {


            DownloadExcel();


            //  exportToExcel();
        }

      /*  if (itemId == R.id.menu_filter) {


            bundle = new Bundle();

            if (bundle != null) {
                bundle.putString("VarietyId",VarityId);
                bundle.putString("QualityId",QualityId);
                bundle.putString("TalukaId",TalukaId);
                bundle.putString("TalukaId",TalukaId);
                bundle.putString("ItemTypeId",ItemTypeId);
                bundle.putString("StatesID",StatesID);
                bundle.putString("DistrictId",DistrictId);


            }

            PriceFragment priceFragment = new PriceFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_order_list_llContainer, priceFragment);
            priceFragment.setArguments(bundle);
            fragmentTransaction.commit();

        }*/
        return super.onOptionsItemSelected(item);
    }


    public void DownloadExcel() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure you want to downoad excel?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    PersistableBundle extras = new PersistableBundle();
                    extras.putString("ItemTypeId", ItemTypeId);
                    extras.putString("VarityId", VarityId);
                    extras.putString("StatesID", StatesID);
                    extras.putString("DistrictId", DistrictId);
                    extras.putString("QualityId", QualityId);
                    extras.putString("TalukaId", TalukaId);
                    extras.putString("currentLanguage", currentLanguage);
                    extras.putString("SortByRate", priceids);
                    extras.putString("AvailableMonths", AvailableMonth);
                    extras.putString("categoryId", categoryId);
                    extras.putString("ItemName", ItemName);


                    JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(getApplicationContext(), MyJobService.class));
                    builder.setPersisted(true).setExtras(extras);
                    builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                            builder.setPeriodic(1*60*1000,60*60*10000);
                        }else {
                            builder.setPeriodic(1*60*1000);
                        }

                    // Start the job
                    JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    // start and get the result
                    int jobResult = scheduler.schedule(builder.build());

                    if (jobResult == JobScheduler.RESULT_FAILURE) {
                        Log.d("TAG", "Job failed to start");
                    } else if (jobResult == JobScheduler.RESULT_SUCCESS) {

                        Log.d("TAG", "Job Running");
                    }

                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }


    }


    private void exportToExcel() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write

            int min = 65;
            int max = 80;

            Random r = new Random();
            int randomNumber = r.nextInt(max - min + 1) + min;

            final String fileName = ItemName + "_" + String.valueOf(randomNumber) + "_" + ".xls";


            //  File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "KisanMaza");

            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }

            //file path
            File file = new File(directory, fileName);

            // File file = new File(Environment.getExternalStorageDirectory()+ "/filepath/" + filename);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");//old version
            //above 24 api new version
            Uri apkURI = FileProvider.getUriForFile(
                    AllSimilarDataActivity.this,
                    this.getApplicationContext()
                            .getPackageName() + ".fileprovider", file);
            intent.setDataAndType(apkURI, "application/vnd.ms-excel");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
                // startActivity(intent);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AllSimilarDataActivity.this);
                builder.setCancelable(false);
                builder.setTitle("File downloaded Successfully...");
                //builder.setMessage("You don't have excel Application!Please download it!");
                builder.setMessage("Go to KisanMaza folder in your phone storage!");

                builder.setIcon(R.drawable.ic_check_circle);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }


            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;

            try {

                WritableFont titleFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
                WritableCellFormat titleformat = new WritableCellFormat(titleFont);
                workbook = Workbook.createWorkbook(file, wbSettings);
                //Excel sheet name. 0 represents first sheet
                WritableSheet sheet = workbook.createSheet("FarmerList_", 0);
//
                try {

                    sheet.addCell(new Label(0, 0, "Category Name", titleformat));
                    sheet.addCell(new Label(1, 0, "Item Name", titleformat)); // column and row
                    sheet.addCell(new Label(2, 0, "Variety Name", titleformat));
                    sheet.addCell(new Label(3, 0, "Available Quality", titleformat));
                    sheet.addCell(new Label(4, 0, "Organic/Non-organic", titleformat));
                    sheet.addCell(new Label(5, 0, "Organic Certifying Agency and Certificate No.", titleformat));
                    sheet.addCell(new Label(6, 0, "Available Quantity", titleformat));
                    sheet.addCell(new Label(7, 0, "Unit", titleformat));
                    sheet.addCell(new Label(8, 0, "Price per unit", titleformat));
                    sheet.addCell(new Label(9, 0, "Available(month)", titleformat));
                    sheet.addCell(new Label(10, 0, "Farm Address", titleformat));
                    sheet.addCell(new Label(11, 0, "Survey No.", titleformat));
                    sheet.addCell(new Label(12, 0, "State", titleformat));
                    sheet.addCell(new Label(13, 0, "District", titleformat));
                    sheet.addCell(new Label(14, 0, "Taluka", titleformat));
                    sheet.addCell(new Label(15, 0, "Village", titleformat));
                    sheet.addCell(new Label(16, 0, "Area in hector", titleformat));
                    sheet.addCell(new Label(17, 0, "Full Name", titleformat));
                    sheet.addCell(new Label(18, 0, "Mobile No.", titleformat));
                    sheet.addCell(new Label(19, 0, "Email.", titleformat));
                    int j = 1;

                    for (int i = 0; i < mList.size(); i++) {
                        sheet.addCell(new Label(0, j, mList.get(i).getCategeryName()));
                        sheet.addCell(new Label(1, j, mList.get(i).getName()));
                        sheet.addCell(new Label(2, j, mList.get(i).getVarietyName()));
                        sheet.addCell(new Label(3, j, mList.get(i).getQuality()));
                        sheet.addCell(new Label(4, j, mList.get(i).getOrganic()));
                        sheet.addCell(new Label(5, j, mList.get(i).getCertificateNo()));
                        sheet.addCell(new Label(6, j, mList.get(i).getQuantity()));
                        sheet.addCell(new Label(7, j, mList.get(i).getUnit()));
                        sheet.addCell(new Label(8, j, mList.get(i).getPerUnitPrice()));
                        sheet.addCell(new Label(9, j, mList.get(i).getAvailable_month()));
                        sheet.addCell(new Label(10, j, mList.get(i).getFarm_address()));
                        sheet.addCell(new Label(11, j, mList.get(i).getSurveyNo()));
                        sheet.addCell(new Label(12, j, mList.get(i).getState()));
                        sheet.addCell(new Label(13, j, mList.get(i).getDistrict()));
                        sheet.addCell(new Label(14, j, mList.get(i).getTaluka()));
                        sheet.addCell(new Label(15, j, mList.get(i).getVillage()));
                        sheet.addCell(new Label(16, j, mList.get(i).getHector()));
                        sheet.addCell(new Label(17, j, mList.get(i).getFullName()));
                        sheet.addCell(new Label(18, j, mList.get(i).getMobileNo()));
                        sheet.addCell(new Label(18, j, mList.get(i).getEmail()));
                        j++;
                    }

                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                workbook.write();
                try {
                    workbook.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                //exportToExcel();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to downoad excel?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        PersistableBundle extras = new PersistableBundle();
                        extras.putString("selectedImages", "selectedImages");

                        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(getApplicationContext(), MyJobService.class));
                        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                        builder.setPersisted(true).setExtras(extras);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            builder.setPeriodic(1 * 60 * 1000, 60 * 60 * 10000);
                        } else {
                            builder.setPeriodic(1 * 60 * 1000);
                        }

                        // Start the job
                        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                        // start and get the result
                        int jobResult = scheduler.schedule(builder.build());

                        if (jobResult == JobScheduler.RESULT_FAILURE) {
                            Log.d("TAG", "Job failed to start");
                        } else if (jobResult == JobScheduler.RESULT_SUCCESS) {

                            Log.d("TAG", "Job Running");
                        }

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        }
    }

    public void OpenExcel(File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");//old version
        //above 24 api new version
        Uri apkURI = FileProvider.getUriForFile(
                AllSimilarDataActivity.this,
                this.getApplicationContext()
                        .getPackageName() + ".fileprovider", file);
        intent.setDataAndType(apkURI, "application/vnd.ms-excel");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 0);
            // startActivity(intent);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(AllSimilarDataActivity.this);
            builder.setCancelable(false);
            builder.setTitle("File downloaded Successfully...");
            //builder.setMessage("You don't have excel Application!Please download it!");
            builder.setMessage("Go to KisanMaza folder in your phone storage!");

            builder.setIcon(R.drawable.ic_check_circle);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }
    @Override
    public void onBackPressed() {
        myDatabaseBuyer.delete();
        super.onBackPressed();
        finish();
    }
}
