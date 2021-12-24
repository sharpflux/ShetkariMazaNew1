package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.adapter.ContactDetailAdapter;
import com.sharpflux.shetkarimaza.adapter.RecyclerViewAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogAddFarm;
import com.sharpflux.shetkarimaza.customviews.CustomDialogAddVehicle;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.filters.BottomSheetDialogSorting;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.ContactDetail;
import com.sharpflux.shetkarimaza.sqlite.dbFilter;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class ContactDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContactDetail> contactlist;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "",SortBy="0",RegistrationSubTypeId="0",CategoryName="0",Latitude="0",Longitude="0";

    boolean isLoading = false;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    ContactDetailAdapter myAdapter;
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
    private int PageSize=50;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    private RecyclerView
            recyclerView_addFarm;
    public static final int PAGE_START = 1;
    private static final int PAGE_SIZE = 10;
    private AddPersonAdapter  addPersonAdapter_farm;
    private ArrayList<AddPersonModel>  addPersonModelArrayList_farm;
    ImageView imageView_addFarm;
    dbFilter myDatabase;
    TextView ToolbartvItemName, tvRecordsCount;
    ImageView ImgBack2;
    Boolean IsSubCategory;
    TextView tvViewing;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private static final int LOCATION_REQUEST_CODE = 101;


    //https://newbedev.com/how-to-show-an-empty-view-with-a-recyclerview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ContactDetailActivity.this);
        if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContactDetailActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContactDetailActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }
        fetchLastLocation();


        recyclerView = findViewById(R.id.contact_Detail_rvProductList);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(ContactDetailActivity.this);
        contactlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        myDatabase = new dbFilter(ContactDetailActivity.this);
        addPersonModelArrayList_farm = new ArrayList<>();

        recyclerView.setAdapter(myAdapter);

        progressBar_filter = findViewById(R.id.progressBar_filter);
        txt_emptyView = findViewById(R.id.txt_emptyView);
        lr_filterbtn = (LinearLayout) findViewById(R.id.lr_filterbtn);

        ImgBack2 = findViewById(R.id.ImgBack2);

        tvViewing=findViewById(R.id.tvViewing);
        ImgBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.delete();
                Intent intent = new Intent(ContactDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        txt_emptyView.setVisibility(View.GONE);

        imageView_addFarm = findViewById(R.id.imageView_addFarm);
        if(mList.size()==0)
        {
            lr_filterbtn.setVisibility(View.GONE);
        }

        ToolbartvItemName = findViewById(R.id.ToolbartvItemName);
        tvRecordsCount = findViewById(R.id.tvRecordsCount);

     /*   recyclerView_addFarm = findViewById(R.id.recyclerView_addFarm);
        addPersonAdapter_farm = new AddPersonAdapter(ContactDetailActivity.this, addPersonModelArrayList_farm,
                "Farm");
        recyclerView_addFarm.setAdapter(addPersonAdapter_farm);
        recyclerView_addFarm.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_farm = new LinearLayoutManager(ContactDetailActivity.this);
        recyclerView_addFarm.setLayoutManager(linearLayoutManager_farm);*/

        ItemTypeId="0";
        bundle = getIntent().getExtras();

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
        }


    /*    imageView_addFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ItemTypeId.equals("1")) {
                    setTitle("Processor");
                } else if (ItemTypeId.equals("4")) {
                    setTitle("Producer");
                } else if (ItemTypeId.equals("5")) {
                    setTitle("Commission Agent");
                } else if (ItemTypeId.equals("6")) {
                    setTitle("Service Provider");
                } else if (ItemTypeId.equals("16")) {
                    setTitle("Individual Farmers");
                } else if (ItemTypeId.equals("17")) {
                    setTitle("Farmer Group");
                } else if (ItemTypeId.equals("18")) {
                    setTitle("Trader");
                } else if (ItemTypeId.equals("20")) {
                    setTitle("FPO/FPC");
                } else if (ItemTypeId.equals("21")) {
                    setTitle("Govt. Agency");
                } else if (ItemTypeId.equals("22")) {
                    setTitle("Individual Customer");
                } else if (ItemTypeId.equals("23")) {
                    setTitle("Exporter");
                } else if (ItemTypeId.equals("24")) {
                    setTitle("Retailer");
                } else if (ItemTypeId.equals("26")) {
                    setTitle("Warehouse");
                    CustomDialogAddFarm customDialogAddFarm = new CustomDialogAddFarm(ContactDetailActivity.this, "0", "0", addPersonAdapter_farm, addPersonModelArrayList_farm, 0);
                    customDialogAddFarm.show();

                } else if (ItemTypeId.equals("27")) {
                    setTitle("Transporter");
                    CustomDialogAddVehicle customDialogAddVehicle = new CustomDialogAddVehicle(ContactDetailActivity.this, "0", "0", addPersonAdapter_farm, addPersonModelArrayList_farm, 0);
                    customDialogAddVehicle.show();
                } else if (ItemTypeId.equals("28")) {
                    setTitle("Packer");
                } else if (ItemTypeId.equals("29")) {
                    setTitle("Franchise");
                }else if (ItemTypeId.equals("30")) {
                    setTitle("Bank Loan Consultants");
                }else if (ItemTypeId.equals("31"))
                {
                    setTitle("Project Consultant");
                } else if (ItemTypeId.equals("32")) {
                    setTitle("Nursery Owner");
                }
            }
        });*/

        if (ItemTypeId.equals("1")) {
            ToolbartvItemName.setText("Processor");
            setTitle("Processor");
        } else if (ItemTypeId.equals("4")) {
            ToolbartvItemName.setText("Producer");
            setTitle("Producer");
        } else if (ItemTypeId.equals("5")) {
            ToolbartvItemName.setText("Commission Agent");
            setTitle("Commission Agent");
        } else if (ItemTypeId.equals("6")) {
            ToolbartvItemName.setText("Service Provider");
            setTitle("Service Provider");
        } else if (ItemTypeId.equals("16")) {
            ToolbartvItemName.setText("Individual Farmers");
            setTitle("Individual Farmers");
        } else if (ItemTypeId.equals("17")) {
            ToolbartvItemName.setText("Farmer Group");
            setTitle("Farmer Group");
        } else if (ItemTypeId.equals("18")) {
            ToolbartvItemName.setText("Trader");
            setTitle("Trader");
        } else if (ItemTypeId.equals("20")) {
            ToolbartvItemName.setText("FPO/FPC");
            setTitle("FPO/FPC");
        } else if (ItemTypeId.equals("21")) {
            ToolbartvItemName.setText("Govt. Agency");
            setTitle("Govt. Agency");
        } else if (ItemTypeId.equals("22")) {
            ToolbartvItemName.setText("Individual Customer");
            setTitle("Individual Customer");
        } else if (ItemTypeId.equals("23")) {
            ToolbartvItemName.setText("Exporter");
            setTitle("Exporter");
        } else if (ItemTypeId.equals("24")) {
            ToolbartvItemName.setText("Processor");
            setTitle("Retailer");
        } else if (ItemTypeId.equals("26")) {
            ToolbartvItemName.setText("Processor");
            setTitle("Warehouse");
        } else if (ItemTypeId.equals("27")) {
            ToolbartvItemName.setText("Transporter");
            setTitle("Transporter");
        } else if (ItemTypeId.equals("28")) {
            ToolbartvItemName.setText("Packer");
            setTitle("Packer");
        } else if (ItemTypeId.equals("29")) {
            ToolbartvItemName.setText("Franchise");
            setTitle("Franchise");
        }else if (ItemTypeId.equals("30")) {
            ToolbartvItemName.setText("Bank Loan Consultants");
            setTitle("Bank Loan Consultants");
        }else if (ItemTypeId.equals("31"))
        {
            ToolbartvItemName.setText("Project Consultant");
            setTitle("Project Consultant");
        } else if (ItemTypeId.equals("32")) {
            ToolbartvItemName.setText("Nursery Owner");
            setTitle("Nursery Owner");
        }

        else if (ItemTypeId.equals("35")) {
            ToolbartvItemName.setText("Krushi Seva Kendra");
            setTitle("Krushi Seva Kendra");
        }

        else if (ItemTypeId.equals("34")) {
            ToolbartvItemName.setText("Insurance Agency");
            setTitle("Insurance Agency");
        }
        else if (ItemTypeId.equals("36")) {
            ToolbartvItemName.setText("Tracktors");
            setTitle("Tracktors");
        }

        else if (ItemTypeId.equals("37")) {
            ToolbartvItemName.setText("Krushi Kamgaar");
            setTitle("Krushi Kamgaar");
        }

        else {
            ToolbartvItemName.setText(CategoryName);
            setTitle(CategoryName);
        }

  /*      ContactDetailActivity.AsyncTaskRunner runner = new ContactDetailActivity.AsyncTaskRunner();
        String sleepTime = "1";
        runner.execute(sleepTime);
*/
        initAdapter();
        initScrollListener();
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
                Intent intent = new Intent(ContactDetailActivity.this, Filter1Activity.class);
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

                int firstElementPosition=linearLayoutManager.findFirstVisibleItemPosition();
                int lastElementPosition=linearLayoutManager.findLastVisibleItemPosition();
                tvViewing.setText( getResources().getString(R.string.viewing)+  String.valueOf( firstElementPosition+1) +" - "+String.valueOf(  lastElementPosition+1));
            }
        });


    }
    private void initAdapter() {
        myAdapter = new ContactDetailAdapter(ContactDetailActivity.this, mList);
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


        if (bundle != null) {


            if(bundle.getString("Search")!=null) {
                if (bundle.getString("Search").contains("Filter")) {
                    Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                    Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                    Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");

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
            if (RegistrationSubTypeId != null) {
                if (RegistrationSubTypeId.equals(""))
                    RegistrationSubTypeId = "0";
            } else {
                RegistrationSubTypeId = "0";
            }

            StringRequest stringRequest = new StringRequest(Request.Method.GET,URLs.URL_CONTACTDET + "StartIndex="+currentPage+"&PageSize="+PageSize + "&RegistrationTypeId=" + ItemTypeId  + "&RegistrationSubTypeId=" + RegistrationSubTypeId+ "&StateId=" + StatesID + "&DistrictId=" + DistrictId + "&TalukaId=" + TalukaId + "&Language=en"+"&Lat=" + Latitude+"&Long=" + Longitude,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                obj = new JSONArray(response);
                                isFirstLoad = true;
                                String oldAPI = "http://apimaza.supergo.in";
                                for (int i = 0; i < obj.length(); i++) {
                                    JSONObject userJson = obj.getJSONObject(i);

                                    if (!userJson.getBoolean("error")) {
                                        String imageUrl = userJson.getString("ImageUrl").substring(oldAPI.length(),userJson.getString("ImageUrl").length());
                                        ContactDetail detail;
                                        detail = new ContactDetail
                                                (
                                                        imageUrl,
                                                        userJson.getString("FullName"),
                                                        userJson.getString("Address"),
                                                        userJson.getString("MobileNo"),
                                                        userJson.getString("StatesName"),
                                                        userJson.getString("DistrictName"),
                                                        userJson.getString("TalukaName")
                                                );

                                        mList.add(detail);
                                        mList.size();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    }
                                    if (obj.length() > 0) {
                                        tvRecordsCount.setText(obj.getJSONObject(0).getString("totalCount").toString() + " " + getResources().getString(R.string.recordsfound) + " | " + getResources().getString(R.string.showing) + String.valueOf(mList.size()));
                                    } else {

                                    }
                                    myAdapter.notifyDataSetChanged();
                                    isLoading = false;




                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(mList.size()==0){
                                txt_emptyView.setVisibility(View.VISIBLE);
                            }
                            else {
                                txt_emptyView.setVisibility(View.GONE);

                            }
                            lr_filterbtn.setVisibility(View.VISIBLE);
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
            progressDialog = ProgressDialog.show(ContactDetailActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

    }

    private void exportToExcel() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write

            final String fileName = "MyOrderList.xls";

            //Saving file in external storage
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/shetkrimaza");

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
                    ContactDetailActivity.this,
                    getApplicationContext()
                            .getPackageName() + ".fileprovider", file);
            intent.setDataAndType(apkURI, "application/vnd.ms-excel");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
                // startActivity(intent);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailActivity.this);
                builder.setCancelable(false);
                builder.setTitle("File downloaded Successfully...");
                //builder.setMessage("You don't have excel Application!Please download it!");
                builder.setMessage("Go to diapertohome folder to your phone storage!");

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
                WritableSheet sheet = workbook.createSheet("MyOrder List", 0);

                try {


                    sheet.addCell(new Label(0, 0, "Full Name", titleformat));
                    sheet.addCell(new Label(1, 0, "Mobile No.", titleformat));
                    sheet.addCell(new Label(2, 0, "Address", titleformat)); // column and row

                    int j = 1;

                    for (int i = 0; i < contactlist.size(); i++) {


                        sheet.addCell(new Label(0, j, contactlist.get(i).getFullName()));
                        sheet.addCell(new Label(1, j, contactlist.get(i).getMobileNo()));
                        sheet.addCell(new Label(2, j, contactlist.get(i).getAddress()));

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
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_order_list_llContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
       myDatabase.delete();
      Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
      startActivity(intent);
      finish();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    public List<Address> getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

                // if you want only city or pin code use following code //
                   /* String Location = address.getLocality();
                    String zip = address.getPostalCode();
                    String Country = address.getCountryName(); */
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return addresses;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        + grantResults[4]
                                        + grantResults[5]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ) {
                    // Permissions are granted
                    Toast.makeText(ContactDetailActivity.this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(ContactDetailActivity.this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    List<Address> addresses = getAddress(ContactDetailActivity.this, currentLocation.getLatitude(), currentLocation.getLongitude());


                    if (addresses != null) {

                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            Latitude=String.valueOf(address.getLatitude());
                            Longitude=String.valueOf(address.getLongitude());

                            ContactDetailActivity.AsyncTaskRunner runner = new ContactDetailActivity.AsyncTaskRunner();
                            String sleepTime = "1";
                            runner.execute(sleepTime);
                        }
                    }

                } else {
                    Toast.makeText(ContactDetailActivity.this, "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
