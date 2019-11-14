package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.fragment.RateDialogFragment;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.uploadimage.FileUtils;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sharpflux.shetkarimaza.activities.SelfieActivity.RequestPermissionCode;


public class ProductInfoForSaleActivity extends AppCompatActivity {


    ImageView img_banner_profile_placeholder;


    private TextInputEditText edtTotalamt, edtproductType, edtdistrict, edtstate, edtproductVariety,
            edtDays, edtavailablityInMonths, edtAQuality, edtAQuantity, edtUnit, edtExpectedPrice,
            edttaluka, edtaddres, edtvillage, edtareahector,edtcertifiedno,edtsurveyNo;
    RadioGroup rg;
    RadioButton rb1,rb2;
    LinearLayout LinearLayout1;
    ImageView imageviewcam1, imageviewcam2, imageviewcam3, imageviewcam4;
    ArrayList<Product> list;
    Product sellOptions;
    Button btn_take_selfie, btnFormSubmit, btnAdd, btnAddMore;
    TextView hideImageTvSelfie, tv_rate;
    private CustomRecyclerViewDialog customDialog;
    public static String DATEFORMATTED = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Date date1;
    DataFetcher fetcher;
    TextView hidItemTypeId, hidVarietyId, hidQualityId, hidMeasurementId, hideStateId, hideDistrictId, hideTalukaId, hideRequstId;
    Locale myLocale;
    String ProductId, productTypeId, productVarietyId, qualityId, unitId, monthId, stateId, districtId, talukaId,certificateno;
    String StateId;
    private String UserId, RequstId;
    private Integer userid;
    ProgressDialog mProgressDialog;
    public String ImageUrl;
    private static final String TAG = ProductInfoForSaleActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    AlertDialog alertDialog;
    private View parentView;
    private GridView listView;
    ImageView imageView;
    String ItemTypeId = "", VarityId = "", QualityId = "";
    // private ProgressBar mProgressBar;
    private Button btnChoose;
    int c = 0;

    private ArrayList<Uri> arrayList;
    private ArrayList<String> ImagesList;
    StringBuilder builder;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    private UserInfoDBManager userInfoDBManager = null;

    private static final String INPUT_ID = "INPUT_ID";

    private static final String INPUT_TYPE = "productType";

    private static final String INPUT_VARITY = "productVariety";

    private static final String INPUT_QUALITY = "quality";

    private static final String INPUT_QUANTTY = "quantity";

    private static final String INPUT_UNIT = "unit";

    private static final String INPUT_PRICE = "price";

    private static final String INPUT_DAYS = "days";

    private static final String INPUT_MONTH = "month";

    private static final String INPUT_ADDRESS = "address";

    private static final String INPUT_STATE = "state";

    private static final String INPUT_DISTRICT = "district";

    private static final String INPUT_TALUKA = "taluka";

    private static final String INPUT_VILLAGE = "village";

    private static final String INPUT_AREA = "area";

    private static final String INPUT_ORGNIC = "orgnic";

    private static final String INPUT_CERTIFICATENO= "certificateno";

    private static final String INPUT_SURVEYNO= "SurveyNo";

    int year;
    int month;
    int day;
    Bundle extras;
    public String ClickedImage;
    String itemId, varityId, qualityid;
    double total;
    String org,productVariety,orgnic,SurveyNo;

    dbLanguage mydatabase;
    String currentLanguage,language;

    DataAdapter dataAdapter;
        public  static  int MY_PERMISSIONS_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info_for_sale);
        builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" ?>");
        org="0";
        productVariety="";
        orgnic="";
        ClickedImage = "";
        mydatabase = new dbLanguage(getApplicationContext());
        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }

        User user = SharedPrefManager.getInstance(this).getUser();
        userid = (Integer) user.getId();
        UserId = userid.toString();

        //hidden
        hidItemTypeId = findViewById(R.id.hidItemTypeId);
        hidVarietyId = findViewById(R.id.hidVarietyId);
        hidQualityId = findViewById(R.id.hidQualityId);
        hidMeasurementId = findViewById(R.id.hidMeasurementId);
        hideStateId = findViewById(R.id.hideStateId);
        hideDistrictId = findViewById(R.id.hideDistrictId);
        hideTalukaId = findViewById(R.id.hideTalukaId);
        hideImageTvSelfie = findViewById(R.id.hideImageTvSelfie);
        hideRequstId = findViewById(R.id.hideRequstId);

        // search
        ///searchView =findViewById(R.id.searchView_customDialog);
        //edt
        edtproductType = findViewById(R.id.name_edit);
        edtproductVariety = findViewById(R.id.edtVarity);
        edtAQuality = findViewById(R.id.edtAQuality);

        edtAQuantity = findViewById(R.id.edtquant);
        edtUnit = findViewById(R.id.edtquantUnit);
        edtExpectedPrice = findViewById(R.id.edtExpectedPrice);
        edtTotalamt = findViewById(R.id.edttotalPrice);


        rg = findViewById(R.id.radioGroup1);
        rb1=findViewById(R.id.radio1);
        rb2=findViewById(R.id.radio2);
        LinearLayout1= findViewById(R.id.LinearLayout1);
        edtcertifiedno=findViewById(R.id.edtcertifiedno);
        edtsurveyNo=findViewById(R.id.edtsurveyNo);


        edtDays = findViewById(R.id.edtDays);
        edtavailablityInMonths = findViewById(R.id.edt_available_in_months);
        edtaddres = findViewById(R.id.edtAddressoffarm);
        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdistrict);
        edttaluka = findViewById(R.id.edtTal);
        edtvillage = findViewById(R.id.edtvillage);
        edtareahector = findViewById(R.id.edthector);
        btn_take_selfie = findViewById(R.id.btn_take_selfie);
        img_banner_profile_placeholder = (ImageView) findViewById(R.id.imageView);
        btnFormSubmit = findViewById(R.id.btnFormSubmit);
        mProgressDialog = new ProgressDialog(ProductInfoForSaleActivity.this);
        mProgressDialog.setIndeterminate(false);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        mProgressDialog.setTitle("Saving data");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, we are saving your data...");

        btnAdd = findViewById(R.id.btnAdd);
        btnAddMore = findViewById(R.id.btnAddMore);
        tv_rate = findViewById(R.id.tv_rate);
        //  imageView = findViewById(R.id.imageView);



        parentView = findViewById(R.id.parent_layout);
        listView = findViewById(R.id.listView);

        EnableRuntimePermission();
        btn_take_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelecteImages();
                hideImageTvSelfie.setText("selfie");
            }
        });


        arrayList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            ProductId = bundle.getString("ProductId");

        }

        //age
         if (ProductId.equals("15")||ProductId.equals("6")||ProductId.equals("8")) {
            edtDays.setVisibility(View.VISIBLE);

        }

         // organic
       if (ProductId.equals("2")||ProductId.equals("9")||ProductId.equals("3")||ProductId.equals("7")||ProductId.equals("14")||ProductId.equals("17")||ProductId.equals("42")){
            LinearLayout1.setVisibility(View.VISIBLE);
        }

       // certificate
        if (ProductId.equals("2")||ProductId.equals("9")||ProductId.equals("3")||ProductId.equals("7")||ProductId.equals("14")||ProductId.equals("17")||ProductId.equals("42")){
            edtcertifiedno.setVisibility(View.VISIBLE);
        }

       //quality
       if (ProductId.equals("6")||ProductId.equals("4")||ProductId.equals("8")) {
            edtAQuality.setVisibility(View.GONE);
        }

        list = new ArrayList<Product>();

        myLocale = getResources().getConfiguration().locale;


        fetcher = new DataFetcher(sellOptions, customDialog, list, ProductInfoForSaleActivity.this);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb1 = (RadioButton) findViewById(checkedId);
                rb2 = (RadioButton)findViewById(checkedId);

                if (rb1 != null) {

                    org = rb1.getHint().toString();

                    Toast.makeText(getApplicationContext(),
                            org,
                            Toast.LENGTH_LONG).show();
                }
                else if (rb2 != null)
                {
                    org = rb2.getHint().toString();

                    Toast.makeText(getApplicationContext(),
                            org,
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        edtTotalamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = edtAQuantity.getText().toString();
                double quant = Double.parseDouble(quantity);


                String expectedPrice = edtExpectedPrice.getText().toString();
                double priceperunit = Double.parseDouble(expectedPrice);

                total = quant * priceperunit;
                edtTotalamt.setText(total + "₹");
            }
        });


        edtavailablityInMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProductInfoForSaleActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                int day = getApplicationContext().getResources().getIdentifier("android:id/day", null, null);
               if (day != 0) {
                    View yearPicker = dialog.getDatePicker().findViewById(day);
                    if (yearPicker != null) {
                        yearPicker.setVisibility(View.GONE);
                    }
                }
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yearSelected, int monthOfYear, int dayOfMonth) {

                year = yearSelected;
                month = monthOfYear;
                day = dayOfMonth;
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                Date date = cal.getTime();
                SimpleDateFormat df_medium_us = new SimpleDateFormat("MMMM,yyyy");
                String dd = df_medium_us.format(date);
                edtavailablityInMonths.setText(dd);

            }
        };

        edtstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "state";
                runner.execute(sleepTime);

            }
        });

        edtdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "district";
                runner.execute(sleepTime);
            }

        });

        edttaluka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "taluka";
                runner.execute(sleepTime);
            }

        });

        btnFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "Update";
                runner.execute(sleepTime);*/
                submitToDb();
            }
        });
        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }


        });


        // Get input extra user account data from UserAccountListViewActivity activity.
        Intent intent = getIntent();
        final int userId = intent.getIntExtra(INPUT_TYPE, -1);
        String userName = intent.getStringExtra(INPUT_VARITY);
        String password = intent.getStringExtra(INPUT_QUALITY);
        String days = intent.getStringExtra(INPUT_PRICE);

        // Can not edit existed user name.
        if (userId != -1) {
            edtproductType.setText(userName);
            //edtproductType.setEnabled(false);

            edtproductVariety.setText(password);
            edtDays.setText(days);
        }

        // Open SQLite database connection.
        userInfoDBManager = new UserInfoDBManager(getApplicationContext());
        userInfoDBManager.open();

        extras = getIntent().getExtras();
        if (extras != null) {
            /*Bitmap bitmap;
            String ImageUrl = extras.getString("ImageUrl");
            byte [] encodeByte = Base64.decode(ImageUrl,Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            img_banner_profile_placeholder.setImageBitmap(bitmap1);
*/



           /* byte[] decodedString = Base64.decode(ImageUrl, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img_banner_profile_placeholder.setImageBitmap(decodedByte);
            */
            String ItemName = extras.getString("ItemName");
            edtproductType.setText(ItemName);
            String VarietyName = extras.getString("VarietyName");
            edtproductVariety.setText(VarietyName);
            String QualityType = extras.getString("QualityType");
            edtAQuality.setText(QualityType);
            String AvailableQuantity = extras.getString("AvailableQuantity");
            edtAQuantity.setText(AvailableQuantity);
            String MeasurementType = extras.getString("MeasurementType");
            edtUnit.setText(MeasurementType);
            String ExpectedPrice = extras.getString("ExpectedPrice");
            edtExpectedPrice.setText(ExpectedPrice);
            String AvailableMonths = extras.getString("AvailableMonths");
            edtavailablityInMonths.setText(AvailableMonths);
            String FarmAddress = extras.getString("FarmAddress");
            edtaddres.setText(FarmAddress);
            String StatesName = extras.getString("StatesName");
            edtstate.setText(StatesName);
            String DistrictName = extras.getString("DistrictName");
            edtdistrict.setText(DistrictName);
            String TalukaName = extras.getString("TalukaName");
            edttaluka.setText(TalukaName);
            String VillageName = extras.getString("VillageName");
            edtvillage.setText(VillageName);
            String Hector = extras.getString("Hector");
            edtareahector.setText(Hector);
            String ItemTypeId = extras.getString("ItemTypeId");
            hidItemTypeId.setText(ItemTypeId);
            String VarietyId = extras.getString("VarietyId");
            hidVarietyId.setText(VarietyId);
            String QualityId = extras.getString("QualityId");
            hidQualityId.setText(QualityId);
            String MeasurementId = extras.getString("MeasurementId");
            hidMeasurementId.setText(MeasurementId);
            String StateId = extras.getString("StateId");
            hideStateId.setText(StateId);
            String DistrictId = extras.getString("DistrictId");
            hideDistrictId.setText(DistrictId);
            String TalukaId = extras.getString("TalukaId");
            hideTalukaId.setText(TalukaId);
            RequstId = extras.getString("RequstId");
            hideRequstId.setText(RequstId);

            if (extras.getString("Type") != null) {
                btnFormSubmit.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.GONE);
                btnAddMore.setVisibility(View.GONE);
            }

        }




        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productType = edtproductType.getText().toString();
                String productVariety = edtproductVariety.getText().toString();
                String quality = edtAQuality.getText().toString();
                String quantity = edtAQuantity.getText().toString();
               String certificateno = edtcertifiedno.getText().toString();
                String unit = edtUnit.getText().toString();
                String days;
                String expectedPrice = edtExpectedPrice.getText().toString();
                String availablityInMonths = edtavailablityInMonths.getText().toString();
                String address = edtaddres.getText().toString();
                String state = edtstate.getText().toString();
                String district = edtdistrict.getText().toString();
                String taluka = edttaluka.getText().toString();
                String villagenam = edtvillage.getText().toString();
                String areaheactor = edtareahector.getText().toString();
                String surveyNo = edtsurveyNo.getText().toString();

                StringBuffer errorMessageBuf = new StringBuffer();


                if(edtDays.getVisibility()==View.VISIBLE)
                {
                    days = edtDays.getText().toString();
                }
                else {
                    days ="0";
                }

               /* if(LinearLayout1.getVisibility()==View.VISIBLE)
                {
                    org = edtDays.getText().toString();
                }
                else {
                    org ="0";
                }*/


                // organic
                if (ProductId.equals("2")||ProductId.equals("9")||ProductId.equals("3")||ProductId.equals("7")||ProductId.equals("14")||ProductId.equals("17")||ProductId.equals("42")){
                    LinearLayout1.setVisibility(View.VISIBLE);
                }

                // certificate
                if (ProductId.equals("2")||ProductId.equals("9")||ProductId.equals("3")||ProductId.equals("7")||ProductId.equals("14")||ProductId.equals("17")||ProductId.equals("42")){
                    edtcertifiedno.setVisibility(View.VISIBLE);
                }

                //quality
                if (ProductId.equals("6")||ProductId.equals("4")||ProductId.equals("8")) {
                    edtAQuality.setVisibility(View.GONE);
                }




                if (TextUtils.isEmpty(productType)) {
                    edtproductType.setError("Please enter your quality");
                    edtproductType.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(quality)) {
                    edtAQuality.setError("Please enter your quality");
                    edtAQuality.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(quantity)) {
                    edtAQuantity.setError("Please enter your quantity");
                    edtAQuantity.requestFocus();
                    return;
                }
              /*  if(!rb1.isChecked()||!rb2.isChecked())
                {//Grp is your radio group object
                    rb1.setError("please select organic or Non organic");
                    rb1.requestFocus();
                    rb2.setError("please select organic or Non organic");
                    rb2.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(certificateno)) {
                    edtcertifiedno.setError("Please enter your certificateno.");
                    edtcertifiedno.requestFocus();
                    return;
                }*/

                if (TextUtils.isEmpty(unit)) {
                    edtUnit.setError("Please enter your unit");
                    edtUnit.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(expectedPrice)) {
                    edtExpectedPrice.setError("Please enter your price");
                    edtExpectedPrice.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(availablityInMonths)) {
                    edtavailablityInMonths.setError("Please enter your available month");
                    edtavailablityInMonths.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    edtaddres.setError("Please enter your address");
                    edtaddres.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(state)) {
                    edtstate.setError("Please enter your state");
                    edtstate.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(district)) {
                    edtdistrict.setError("Please enter your district");
                    edtdistrict.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(taluka)) {
                    edttaluka.setError("Please enter your taluka");
                    edttaluka.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(villagenam)) {
                    edtvillage.setError("Please enter your village name");
                    edtvillage.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(surveyNo)) {
                    edtsurveyNo.setError("Please enter your Survey No.");
                    edtsurveyNo.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(productType)) {
                    errorMessageBuf.append("Username can not be empty./r/n");
                } else if (TextUtils.isEmpty(productType)) {
                    errorMessageBuf.append("Password can not be empty./r/n");
                } else if (TextUtils.isEmpty(productType)) {
                    errorMessageBuf.append("Email can not be empty./r/n");
                }

                if (errorMessageBuf.length() > 0) {
                    Toast.makeText(getApplicationContext(), errorMessageBuf.toString(), Toast.LENGTH_SHORT).show();
                } else {

                    if (userId == -1) {
                        // Insert new user account.
                        userInfoDBManager.insertAccount(productType, hidItemTypeId.getText().toString(), productVariety, hidVarietyId.getText().toString(),
                                quality, hidQualityId.getText().toString(), quantity, unit, hidMeasurementId.getText().toString(), expectedPrice,
                                days,availablityInMonths, address, state, hideStateId.getText().toString(),
                                district, hideDistrictId.getText().toString(), taluka, hideTalukaId.getText().toString(),
                                villagenam, areaheactor, ImageUrl,org,certificateno,surveyNo);


                    } else {
                        // Update exist user account.
                        userInfoDBManager.updateAccount(userId, productVariety, days, productType);
                    }

                 /*    Toast.makeText(getApplicationContext(), "User account is saved successfully.", Toast.LENGTH_SHORT).show();
                   finish();
                */
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Do you want to Add more Quality?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            edtUnit.setText("");
                            edtAQuantity.setText("");
                            edtAQuality.setText("");
                            edtExpectedPrice.setText("");
                            edtUnit.setText("");
                            edtTotalamt.setText("");
                           edtcertifiedno.setText("");


                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent startUserAccountListIntent = new Intent(getApplicationContext(), AddListActivity.class);
                            startUserAccountListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startUserAccountListIntent.putExtra("ProductId", ProductId);

                            startUserAccountListIntent.putExtra("productTypeId", hidItemTypeId.getText().toString());
                            startUserAccountListIntent.putExtra("productVarietyId", hidVarietyId.getText().toString());
                            startUserAccountListIntent.putExtra("productVariety",edtproductVariety.getText().toString());
                            startUserAccountListIntent.putExtra("qualityId", hidQualityId.getText().toString());

                            //startUserAccountListIntent.putExtra("monthId", edtavailablityInMonths.getText().toString());
                            startUserAccountListIntent.putExtra("unitId", hidMeasurementId.getText().toString());
                            startUserAccountListIntent.putExtra("stateId", hideStateId.getText().toString());
                            startUserAccountListIntent.putExtra("districtId", hideDistrictId.getText().toString());
                            startUserAccountListIntent.putExtra("talukaId", hideTalukaId.getText().toString());
                           startUserAccountListIntent.putExtra("organic", org);
                            startUserAccountListIntent.putExtra("certificateno", edtcertifiedno.getText().toString());
                            startUserAccountListIntent.putExtra("SurveyNo",edtsurveyNo.getText().toString());
                            startUserAccountListIntent.putExtra("ImageUrl",ImageUrl);
                            startActivity(startUserAccountListIntent);

                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startUserAccountListIntent = new Intent(getApplicationContext(), AddListActivity.class);
               /* startUserAccountListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startUserAccountListIntent.putExtra("ProductId", ProductId);

                startUserAccountListIntent.putExtra("productTypeId", hidItemTypeId.getText().toString());
                startUserAccountListIntent.putExtra("productVarietyId", hidVarietyId.getText().toString());
                startUserAccountListIntent.putExtra("qualityId", hidQualityId.getText().toString());
                startUserAccountListIntent.putExtra("qualityId", hidQualityId.getText().toString());

                //startUserAccountListIntent.putExtra("monthId", edtavailablityInMonths.getText().toString());
                startUserAccountListIntent.putExtra("unitId",   hidMeasurementId.getText().toString());
                startUserAccountListIntent.putExtra("stateId",    hideStateId.getText().toString());
                startUserAccountListIntent.putExtra("districtId",   hideDistrictId.getText().toString());
                startUserAccountListIntent.putExtra("talukaId",    hideTalukaId.getText().toString());*/
                startActivity(startUserAccountListIntent);


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,SellerActivity.class);
        startActivity(intent);
    }

    public void openDialog() {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle b;
        b = new Bundle();
        b.putString("ItemTypeId", hidItemTypeId.getText().toString());
        b.putString("VarityId", hidVarietyId.getText().toString());
        b.putString("QualityId", hidQualityId.getText().toString());
        b.putString("MeasurementType", edtUnit.getText().toString());
        rateDialogFragment.setArguments(b);
        rateDialogFragment.show(getSupportFragmentManager(), "rateDialogFragment");

    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userInfoDBManager != null) {
            userInfoDBManager.close();
            userInfoDBManager = null;
        }
    }

    // Start this activity from other class.
    public static void start(Context ctx, int id, String productType, String productVariety, String days, String pt) {
        Intent intent = new Intent(ctx, ProductInfoForSaleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(INPUT_ID, id);
        intent.putExtra(INPUT_TYPE, productType);
        intent.putExtra(INPUT_VARITY, productVariety);
        intent.putExtra(INPUT_DAYS, days);
        intent.putExtra("ProductId", pt);
        ctx.startActivity(intent);
    }

    public void clickHere(View view) {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "ItemName";
        runner.execute(sleepTime);
    }

    public void clickHereVariety(View view) {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "Variety";
        runner.execute(sleepTime);
    }

    public void clickHereQuality(View view) {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "Quality";
        runner.execute(sleepTime);
    }

    public void clickHereUnit(View view) {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "Unit";
        runner.execute(sleepTime);
    }



    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {

                if (params[0].toString() == "ItemName")
                    fetcher.loadList("ItemName", edtproductType, URLs.URL_NAME + "?CategoryId=" + ProductId + "&Language=" + currentLanguage, "ItemTypeId", hidItemTypeId, "CategoryId", ProductId,"Botanical Name");
                else if (params[0].toString() == "Variety")
                    fetcher.loadList("VarietyName", edtproductVariety, URLs.URL_VARIATY + hidItemTypeId.getText() + "&Language=" + currentLanguage, "VarietyId", hidVarietyId, "", "","Variety");
                else if (params[0].toString() == "Quality")
                    fetcher.loadList("QualityType", edtAQuality, URLs.URL_QUALITY + "?Language=" + currentLanguage, "QualityId", hidQualityId, "", "","Available Quality");
                else if (params[0].toString() == "Unit")
                    fetcher.loadList("MeasurementType", edtUnit, URLs.URL_UNIT + "?Language=" + currentLanguage, "MeasurementId", hidMeasurementId, "", "","Unit");
                else if (params[0].toString() == "state")
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?Language=" + currentLanguage, "StatesID", hideStateId, "", "","State");
                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText() + ",&Language=" + currentLanguage, "DistrictId", hideDistrictId, "", "","District");
                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText() + ",&Language=" + currentLanguage, "TalukasId", hideTalukaId, "", "","Taluka");

                else if (params[0].toString() == "Update") {
                    submitToDb();
                }
            /*    else if (params[0].toString() == "Rate") {
                    loadProducts();
                }*/
                Thread.sleep(1000);

                resp = "Slept for " + params[0] + " seconds";


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            // finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ProductInfoForSaleActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }


    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ProductInfoForSaleActivity.this,
                Manifest.permission.CAMERA)) {


        } else {

            ActivityCompat.requestPermissions(ProductInfoForSaleActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }

        if (ContextCompat.checkSelfPermission(ProductInfoForSaleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProductInfoForSaleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
        }
        else {
            //Permission is not granted so you have to request it
            ActivityCompat.requestPermissions(ProductInfoForSaleActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    private void SelecteImages() {

        final CharSequence[] items = {"Camera", "Gallary", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[i].equals("Gallary")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ImageUrl = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        img_banner_profile_placeholder.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        ImageUrl = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // img_banner_profile_placeholder.setImageBitmap(thumbnail);

        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
        img_banner_profile_placeholder.setImageBitmap(imageBitmap);

    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    //   Toast.makeText(ProductInfoForSaleActivity.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    // Toast.makeText(ProductInfoForSaleActivity.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void refreshAllContent(final long timetoupdate) {
        new CountDownTimer(timetoupdate, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Log.i("SCROLLS ", "UPDATE CONTENT HERE ");
                submitToDb();
            }
        }.start();
    }


    public void submitToDb() {

        builder.append("<Parent>");
        builder.append("<Assign>");
        builder.append("<RequestId>" + RequstId + "</RequestId>");
        builder.append("<UserId>" + UserId + "</UserId>");
        builder.append("<productTypeId>" + hidItemTypeId.getText() + "</productTypeId>");
        builder.append("<productVarietyId>" + hidVarietyId.getText() + "</productVarietyId>");
        builder.append("<qualityId>" + hidQualityId.getText() + "</qualityId>");
        builder.append("<quantity>" + edtAQuantity.getText() + "</quantity>");
        builder.append("<unitId>" + hidMeasurementId.getText() + "</unitId>");
        builder.append("<expectedPrice>" + edtExpectedPrice.getText() + "</expectedPrice>");
        builder.append("<days>" + "0" + "</days>");
        builder.append("<availablityInMonths>" + edtavailablityInMonths.getText() + "</availablityInMonths>");
        builder.append("<address>" + edtaddres.getText() + "</address>");
        builder.append("<stateId>" + hideStateId.getText() + "</stateId>");
        builder.append("<districtId>" + hideDistrictId.getText() + "</districtId>");
        builder.append("<talukaId>" + hideTalukaId.getText() + "</talukaId>");
        builder.append("<villagenam>" + edtvillage.getText() + "</villagenam>");
        builder.append("<areaheactor>" + edtareahector.getText() + "</areaheactor>");
        builder.append("<imagename>" + "" + "</imagename>");
        builder.append("<orgnic>"+org+"</orgnic>");
        builder.append("<certificateno>"+edtcertifiedno.getText()+"</certificateno>");
        builder.append("<SurveyNo>"+edtsurveyNo.getText()+"</SurveyNo>");
        builder.append("</Assign>");
        builder.append("</Parent>");

//edtcertifiedno.getText()+
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SAVEPRODUCTDETAILS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                        builder.setCancelable(false);
                        mProgressDialog.dismiss();
                        builder.setMessage("Data submitted successfully");
                        userInfoDBManager.deleteAll();
                        builder.setIcon(R.drawable.ic_check_circle);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ProductInfoForSaleActivity.this, HomeActivity.class);
                                startActivity(i);
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                        //refreshAllContent(30000);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ProductInfoForSaleActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("xmlData", builder.toString());
                return params;
            }
        };

        //  requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS *
                2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(ProductInfoForSaleActivity.this).addToRequestQueue(stringRequest);


    }
}
