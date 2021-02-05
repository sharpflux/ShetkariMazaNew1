package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

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
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.adapter.ContactDetailAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogAddFarm;
import com.sharpflux.shetkarimaza.customviews.CustomDialogAddVehicle;
import com.sharpflux.shetkarimaza.filters.BottomSheetDialogSorting;
import com.sharpflux.shetkarimaza.filters.Filter1Activity;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.ContactDetail;
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

public class ContactDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContactDetail> contactlist;
    Bundle bundle;
    String TalukaId = "", VarityId = "", QualityId = "", ItemTypeId = "", StatesID = "", DistrictId = "";
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

    private RecyclerView
            recyclerView_addFarm;
    public static final int PAGE_START = 1;
    private static final int PAGE_SIZE = 10;
    private AddPersonAdapter  addPersonAdapter_farm;
    private ArrayList<AddPersonModel>  addPersonModelArrayList_farm;
    ImageView imageView_addFarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        /*Toolbar toolbar = findViewById(R.id.toolbarCustom);
        setSupportActionBar(toolbar);
*/

        recyclerView = findViewById(R.id.contact_Detail_rvProductList);

        contactlist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<ContactDetail> mList = new ArrayList<>();


        addPersonModelArrayList_farm = new ArrayList<>();


        recyclerView.setAdapter(myAdapter);

        progressBar_filter = findViewById(R.id.progressBar_filter);
        txt_emptyView = findViewById(R.id.txt_emptyView);
        lr_filterbtn = (LinearLayout) findViewById(R.id.lr_filterbtn);

        imageView_addFarm = findViewById(R.id.imageView_addFarm);
        if(mList.size()==0)
        {
            lr_filterbtn.setVisibility(View.GONE);


        }


        recyclerView_addFarm = findViewById(R.id.recyclerView_addFarm);
        addPersonAdapter_farm = new AddPersonAdapter(ContactDetailActivity.this, addPersonModelArrayList_farm,
                "Farm");
        recyclerView_addFarm.setAdapter(addPersonAdapter_farm);
        recyclerView_addFarm.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_farm = new LinearLayoutManager(ContactDetailActivity.this);
        recyclerView_addFarm.setLayoutManager(linearLayoutManager_farm);


        bundle = getIntent().getExtras();
        bundle.getString("ProductId");
        if (bundle != null) {
            ItemTypeId = bundle.getString("ProductId");
            TalukaId = bundle.getString("TalukaId");
            VarityId = bundle.getString("VarietyId");
            QualityId = bundle.getString("QualityId");
            StatesID = bundle.getString("StatesID");
            DistrictId = bundle.getString("DistrictId");
        }
        imageView_addFarm.setOnClickListener(new View.OnClickListener() {
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
        });

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
        } else if (ItemTypeId.equals("27")) {
            setTitle("Transporter");
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



        myAdapter = new ContactDetailAdapter(ContactDetailActivity.this, mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);


        myLocale = getResources().getConfiguration().locale;

        ContactDetailActivity.AsyncTaskRunner runner = new ContactDetailActivity.AsyncTaskRunner();
        String sleepTime = "1";
        runner.execute(sleepTime);


       /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


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

                scrollOutItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


             if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == contactlist.size() - 1) {
                        //bottom of list!
                        currentPage++;
                        ContactDetailActivity.AsyncTaskRunner runner = new ContactDetailActivity.AsyncTaskRunner();
                        String sleepTime = String.valueOf(currentPage);
                        runner.execute(sleepTime);
                        isLoading = true;
                        myAdapter.notifyDataSetChanged();
                        isLoading = false;
                        progressBar_filter.setVisibility(View.GONE);

                    }

                }


     if (isLoading && (currentItems + scrollOutItems == totalItems)) {
                    currentPage++;
                    AllSimilarDataActivity.AsyncTaskRunner runner = new AllSimilarDataActivity.AsyncTaskRunner();
                    String sleepTime =String.valueOf( currentPage);
                    runner.execute(sleepTime);

                    recyclerView.scrollToPosition(myAdapter.getItemCount()-1);
                }

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
                Intent intent = new Intent(ContactDetailActivity.this, Filter1Activity.class);
                startActivity(intent);
            }
        });

    }



    private void SetDynamicDATA() {


        if (bundle != null) {
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


            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_CONTACTDET + "&RegistrationTypeId=" + ItemTypeId + "&StateId=" + StatesID + "&DistrictId=" + DistrictId + "&TalukaId=" + TalukaId + "&Language=en",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.d("RESPONSE","\n\n\n-------------------------------\n"+response+"\n\n\n-------------------------------\n");
                                obj = new JSONArray(response);
                                isFirstLoad = true;
                                String oldAPI = "http://apimaza.supergo.in";
                                for (int i = 0; i < obj.length(); i++) {
                                    JSONObject userJson = obj.getJSONObject(i);

                                   /* if(userJson.length()==0){
                                       txt_err.setVisibility(View.VISIBLE);
                                    }
*/


                                    if (!userJson.getBoolean("error")) {
                                        String imageUrl = userJson.getString("ImageUrl")
                                                .substring(oldAPI.length(), userJson.getString("ImageUrl").length());


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

                                        contactlist.add(detail);
                                        contactlist.size();

                                    }  /*if(obj.length()==0){
                                        Toast.makeText(getApplicationContext(),"Data base is empty",Toast.LENGTH_SHORT).show();
                                    }*/
                                    else {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    }

                                    myAdapter = new ContactDetailAdapter(ContactDetailActivity.this, contactlist);
                                    recyclerView.setAdapter(myAdapter);

                                   if(myAdapter.getItemCount()==0);{
                                       txt_emptyView.setVisibility(View.VISIBLE);
                                        //lr_filterbtn.setVisibility(View.GONE);
                                    }
                                        txt_emptyView.setVisibility(View.GONE);
                                        lr_filterbtn.setVisibility(View.VISIBLE);






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

               /* SetDynamicDATA();
                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";*/

                int time = Integer.parseInt(params[0]) * 1000;
                SetDynamicDATA();
                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";

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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_contact_detail, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_download) {
            exportToExcel();
        }


        return super.onOptionsItemSelected(item);
    }
*/


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
                    ContactDetailActivity.this,
                    this.getApplicationContext()
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
      Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
      startActivity(intent);
    }
}
