package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.SimilarListAdapter;
import com.sharpflux.shetkarimaza.filters.BottomSheetDialogSorting;
import com.sharpflux.shetkarimaza.filters.BuyerFilterActivity;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.filters.PriceFragment;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
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

public class AllSimilarDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<SimilarList> productlist;
    Bundle bundle;

    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "",priceids="",ItemName;
    boolean isLoading = false;
    int currentItems;
    int totalItems;
    int scrollOutItems;
    SimilarListAdapter myAdapter;
    private int currentPage = PAGE_START;
    private boolean isFirstLoad = false;
    private int totalPage = 10;
    int itemCount = 0;
    ProgressBar progressBar_filter;
    Locale myLocale;
    dbBuyerFilter myDatabase;

    public static final int PAGE_START = 1;
    private static final int PAGE_SIZE = 10;

    dbLanguage mydatabase;
    String currentLanguage,language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_similar_data);

        recyclerView = findViewById(R.id.similar_rvProductList);
        productlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<SimilarList> mList = new ArrayList<>();
        myDatabase = new dbBuyerFilter(getApplicationContext());

        recyclerView.setAdapter(myAdapter);
        progressBar_filter = findViewById(R.id.progressBar_filter);

        myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        myLocale = getResources().getConfiguration().locale;

        mydatabase = new dbLanguage(getApplicationContext());

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        AssignVariables();
        BundleAssign();
        setTitle(ItemName);

       /* AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
        String sleepTime = String.valueOf(currentPage);
        runner.execute(sleepTime);
*/
        AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
        String sleepTime = "1";
        runner.execute(sleepTime);




       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // isLoading = true;
                    progressBar_filter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();

               *//* scrollOutItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productlist.size() - 1) {
                        //bottom of list!
                        currentPage++;
                        AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
                        String sleepTime = String.valueOf(currentPage);
                        runner.execute(sleepTime);
                        isLoading = true;
                        myAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar_filter.setVisibility(View.GONE);

                    }

                }*//*



            *//*    if (isLoading && (currentItems + scrollOutItems == totalItems)) {
                    currentPage++;
                    AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
                    String sleepTime =String.valueOf( currentPage);
                    runner.execute(sleepTime);

                    recyclerView.scrollToPosition(myAdapter.getItemCount()-1);
                }*//*

            }

        });*/





        View showModalBottomSheet = findViewById(R.id.bottom);
        showModalBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                intent.putExtra("ItemTypeId",ItemTypeId);
                intent.putExtra("TalukaId",TalukaId);
                intent.putExtra("VarietyId",VarityId);
                intent.putExtra("QualityId",QualityId);
                intent.putExtra("StatesID",StatesID);
                intent.putExtra("DistrictId",DistrictId);
                intent.putExtra("priceids",priceids);

                startActivity(intent);
            }
        });


    }

    private void BundleAssign()
    {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            ItemTypeId = bundle.getString("ItemTypeId");
            ItemName = bundle.getString("ItemName");
            //TalukaId = bundle.getString("TalukaId");
           //VarityId = bundle.getString("VarietyId");
          // QualityId = bundle.getString("QualityId");
           //StatesID = bundle.getString("StatesID");
           //DistrictId = bundle.getString("DistrictId");
           //priceids=bundle.getString("priceids");

        }

    }

    private void AssignVariables()
    {
        ItemTypeId = "";
        TalukaId = "";
        VarityId = "";
        QualityId ="";
        StatesID ="";
        DistrictId ="";
        priceids="";
    }

    private void SetDynamicDATA(String pageIndex) {

        bundle = getIntent().getExtras();

        AssignVariables();
        if (bundle != null) {

            BundleAssign();

            if(bundle.getString("Search")!=null) {
                if (bundle.getString("Search").contains("Filter")) {
                    Cursor VARIETYCursor = myDatabase.FilterGetByFilterName("VARIETY");
                    Cursor QUALITYCursor = myDatabase.FilterGetByFilterName("QUALITY");
                    Cursor STATECursor = myDatabase.FilterGetByFilterName("STATE");
                    Cursor DISTRICTCursor = myDatabase.FilterGetByFilterName("DISTRICT");
                    Cursor TALUKACursor = myDatabase.FilterGetByFilterName("TALUKA");

                    while (VARIETYCursor.moveToNext()) {
                        if(VarityId==null)
                        {
                            VarityId="";
                        }
                        VarityId = VarityId + VARIETYCursor.getString(0) + ",";
                    }
                    while (QUALITYCursor.moveToNext()) {
                        if(QualityId==null)
                        {
                            QualityId="";
                        }
                        QualityId = QualityId + QUALITYCursor.getString(0) + ",";
                    }
                    while (STATECursor.moveToNext()) {
                        StatesID = StatesID + STATECursor.getString(0) + ",";
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
            if (priceids != null) {
                if (priceids.equals(""))
                    priceids = "0";
            } else {
                priceids = "0";
            }

            if(ItemTypeId==null)
            {
                Toast.makeText(getApplicationContext(), "ITEM TYPE IS NULL", Toast.LENGTH_SHORT).show();
                return;
            }



            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_REQESTS + "?StartIndex=" + pageIndex + "&PageSize=" + PAGE_SIZE +
                            "&ItemTypeId=" + ItemTypeId + "&VarityId=" + VarityId + "&StateId=" + StatesID +
                            "&DistrictId=" + DistrictId + "&QualityId=" + QualityId + "&TalukaId="
                            + TalukaId+"&Language="+currentLanguage+"&SortByRate="+priceids,
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
                                                        userJson.getString("OrganicCertiicateNo")

                                                );

                                        productlist.add(sellOptions);
                                        productlist.size();

                                    } else {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    }

                                    myAdapter = new SimilarListAdapter(AllSimilarDataActivity.this, productlist);
                                    recyclerView.setAdapter(myAdapter);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        protected void onPostExecute(String result)
        {
          progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllSimilarDataActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }

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
            exportToExcel();
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

    private void exportToExcel() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                builder.setMessage("Go to ShetkariMaza folder in your phone storage!");

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

                    int j = 1;

                    for (int i = 0; i < productlist.size(); i++) {

                        sheet.addCell(new Label(0, j, productlist.get(i).getCategeryName()));
                        sheet.addCell(new Label(1, j, productlist.get(i).getName()));
                        sheet.addCell(new Label(2, j, productlist.get(i).getVarietyName()));
                        sheet.addCell(new Label(3, j, productlist.get(i).getQuality()));
                        sheet.addCell(new Label(4, j, productlist.get(i).getOrganic()));
                        sheet.addCell(new Label(5, j, productlist.get(i).getCertificateNo()));
                        sheet.addCell(new Label(6, j, productlist.get(i).getQuantity()));
                        sheet.addCell(new Label(7, j, productlist.get(i).getUnit()));
                        sheet.addCell(new Label(8, j, productlist.get(i).getPrice()));
                        sheet.addCell(new Label(9, j, productlist.get(i).getAvailable_month()));
                        sheet.addCell(new Label(10, j, productlist.get(i).getFarm_address()));
                        sheet.addCell(new Label(11, j, productlist.get(i).getSurveyNo()));
                        sheet.addCell(new Label(12, j, productlist.get(i).getState()));
                        sheet.addCell(new Label(13, j, productlist.get(i).getDistrict()));
                        sheet.addCell(new Label(14, j, productlist.get(i).getTaluka()));
                        sheet.addCell(new Label(15, j, productlist.get(i).getVillage()));
                        sheet.addCell(new Label(16, j, productlist.get(i).getHector()));
                        sheet.addCell(new Label(17, j, productlist.get(i).getFullName()));
                        sheet.addCell(new Label(18, j, productlist.get(i).getMobileNo()));

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
