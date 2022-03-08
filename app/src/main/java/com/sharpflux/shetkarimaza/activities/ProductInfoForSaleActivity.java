package com.sharpflux.shetkarimaza.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.CouponAdapter;
import com.sharpflux.shetkarimaza.adapter.DataAdapter;
import com.sharpflux.shetkarimaza.adapter.RateAdapter;
import com.sharpflux.shetkarimaza.adapter.SimilarListAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.customviews.MonthPickerDialog;
import com.sharpflux.shetkarimaza.fragment.RateDialogFragment;
import com.sharpflux.shetkarimaza.model.CouponModel;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.RateData;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.UserInfoDBManager;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.uploadimage.FileUtils;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.utils.PictureFacer;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleyMultipartRequest;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sharpflux.shetkarimaza.activities.SelfieActivity.RequestPermissionCode;


public class ProductInfoForSaleActivity extends AppCompatActivity  implements OnMapReadyCallback {


    ImageView img_banner_profile_placeholder;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;

    private EditText edtTotalamt, edtproductType, edtdistrict, edtstate, edtproductVariety,
            edtDays, edtavailablityInMonths, edtAQuality, edtAQuantity, edtUnit, edtExpectedPrice,
            edttaluka, edtaddres, edtvillage, edtareahector, edtcertifiedno, edtsurveyNo, name_botanical, sub_category_name;
    RadioGroup rg;
    RadioButton rb1, rb2;
    LinearLayout LinearLayout1;
    ImageView imageviewcam1, imageviewcam2, imageviewcam3, imageviewcam4;
    ArrayList<Product> list;
    Product sellOptions;
    Button btn_take_selfie, btnAdd;
    LinearLayout btnFormSubmit, btnAddMore;
    TextView  tv_rate, hideAgeId;
    CustomRecyclerViewDialog customDialog;
    public static String DATEFORMATTED = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Date date1;
    DataFetcher fetcher;
    EditText hidItemTypeId, hidVarietyId, hidQualityId, hidMeasurementId, hideStateId, hideDistrictId, hideTalukaId, hideRequstId, hideSubCatId,hideImageTvSelfie;
    Locale myLocale;
    String ProductId, productTypeId, productVarietyId, qualityId, unitId, monthId, stateId, districtId, talukaId, certificateno;
    String StateId;
    private String UserId, RequstId, AgeGroupId;
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
    String ItemTypeId = "", VarityId = "", QualityId = "",Latitude="",Longitude="",GPSState="",GPSDistrict="",GPSTaluka="";
    // private ProgressBar mProgressBar;
    private Button btnChoose;
    int c = 0;
    private Bitmap bitmap;
    private ArrayList<Uri> arrayList;
    private ArrayList<String> ImagesList;
    StringBuilder builder;
    TextInputLayout lt_txtAge, lr_subCategory, lr_quality, lr_botanicalName, lr_variety, lr_areaInHector, textInputAvailableMonth;
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

    private static final String INPUT_CERTIFICATENO = "certificateno";

    private static final String INPUT_SURVEYNO = "SurveyNo";

    int year;
    int month;
    int day;
    Bundle extras;
    public String ClickedImage;
    String itemId, varityId, qualityid;
    double total = 0.00;
    String org, productVariety, orgnic, SurveyNo;

    dbLanguage mydatabase;
    String currentLanguage, language;
    String ImageUrlupload = "", ImageUrlUpdate = "", imageString = "";

    double priceperunit = 0.00;
    double quant = 0.00;
    DataAdapter dataAdapter;
    public static int MY_PERMISSIONS_REQUEST = 1;
    ScrollView scrollableContents;
    LinearLayout lr_rate;
    Boolean IsEdit;

    private RateAdapter rateAdapter;
    private ArrayList<RateData> rateModels;
    private RecyclerView recyclerView;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private static final int LOCATION_REQUEST_CODE = 101;

    TextView tvCurrentAddress;

    LinearLayout linearAddress;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info_for_sale);

        if (savedInstanceState != null) {

            edtproductType.setText(savedInstanceState.getString("productType"));
            hidItemTypeId.setText(savedInstanceState.getString("productTypeId"));
        }



        builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" ?>");
        org = "0";
        productVariety = "";
        orgnic = "";
        ClickedImage = "";
        mydatabase = new dbLanguage(getApplicationContext());
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
                changeLang(cursor.getString(0));
            }
        }
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(ProductInfoForSaleActivity.this);
        User user = SharedPrefManager.getInstance(this).getUser();
        userid = (Integer) user.getId();
        UserId = userid.toString();

        lr_rate=findViewById(R.id.lr_rate);

        rateModels = new ArrayList<>();

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
        hideAgeId = findViewById(R.id.hideAgeId);
        hideSubCatId = findViewById(R.id.hideSubCatId);


        // search
        ///searchView =findViewById(R.id.searchView_customDialog);
        //edt
        edtproductType = findViewById(R.id.name_edit);
        edtproductVariety = findViewById(R.id.edtVarity);
        edtAQuality = findViewById(R.id.edtAQuality);
        name_botanical = findViewById(R.id.name_botanical);

        edtAQuantity = findViewById(R.id.edtquant);
        edtUnit = findViewById(R.id.edtquantUnit);
        edtExpectedPrice = findViewById(R.id.edtExpectedPrice);
        edtTotalamt = findViewById(R.id.edttotalPrice);
        sub_category_name = findViewById(R.id.sub_category_name);
        lr_subCategory = findViewById(R.id.lr_subCategory);
        lr_botanicalName = findViewById(R.id.lr_botanicalName);
        lr_variety = findViewById(R.id.lr_variety);
        lr_areaInHector = findViewById(R.id.lr_areaInHector);


        rg = findViewById(R.id.radioGroup1);
        rb1 = findViewById(R.id.radio1);
        rb2 = findViewById(R.id.radio2);
        LinearLayout1 = findViewById(R.id.LinearLayout1);
        edtcertifiedno = findViewById(R.id.edtcertifiedno);
        edtsurveyNo = findViewById(R.id.edtsurveyNo);
        scrollableContents = findViewById(R.id.scrollableContents);



        textInputAvailableMonth = findViewById(R.id.textInputAvailableMonth);


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
        //btnFormSubmit = findViewById(R.id.footer);
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
        lt_txtAge = findViewById(R.id.lt_txtAge);
        lr_quality = findViewById(R.id.lr_quality);
        tvCurrentAddress=findViewById(R.id.tvCurrentAddress);
        //  imageView = findViewById(R.id.imageView);


        parentView = findViewById(R.id.parent_layout);
        listView = findViewById(R.id.listView);

        linearAddress=findViewById(R.id.linearAddress);



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
            IsEdit=bundle.getBoolean("IsEdit");
            RequstId="0";
        }




        linearAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductInfoForSaleActivity.this, LocActivity.class);
                locationActivityLauncher.launch(intent);
          /*      intent.putExtra("ProductId",ProductId);
                startActivity(intent);*/
            }
        });



        edtaddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductInfoForSaleActivity.this, LocActivity.class);
                locationActivityLauncher.launch(intent);
                /*intent.putExtra("ProductId",ProductId);
                startActivity(intent);*/
            }
        });

        tvCurrentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductInfoForSaleActivity.this, LocActivity.class);
                locationActivityLauncher.launch(intent);
                /*intent.putExtra("ProductId",ProductId);
                startActivity(intent);*/
            }
        });

        //age
        if (ProductId.equals("15") || ProductId.equals("6") || ProductId.equals("8") || ProductId.equals("35")) {
            lt_txtAge.setVisibility(View.VISIBLE);

        }

        //Area in hector
        if (!(ProductId.equals("4") || ProductId.equals("6") || ProductId.equals("10") || ProductId.equals("35"))) {
            lr_areaInHector.setVisibility(View.VISIBLE);


        }

        if (lr_areaInHector.getVisibility() == View.INVISIBLE) {
            edtareahector.setText("0");
        }

        // clear CategoryName
        if (ProductId.equals("15") || ProductId.equals("7") || ProductId.equals("43")) {
            edtproductType.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    sub_category_name.setText("");


                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }


        //  SubCategory Name
        if (ProductId.equals("15") || ProductId.equals("43")) {
            lr_subCategory.setVisibility(View.VISIBLE);
            lr_quality.setVisibility(View.GONE);
            LinearLayout1.setVisibility(View.GONE);
            hidItemTypeId.setText(hideSubCatId.getText());
            lr_areaInHector.setVisibility(View.GONE);
        }

        //Botanical Name
        if (ProductId.equals("4") || ProductId.equals("6") || ProductId.equals("10") || ProductId.equals("35")|| ProductId.equals("14") || ProductId.equals("42") || ProductId.equals("43") || ProductId.equals("7")) {
            lr_botanicalName.setVisibility(View.GONE);


        }

        //Variety
        if (ProductId.equals("4") || ProductId.equals("6") || ProductId.equals("10")) {
            lr_variety.setVisibility(View.GONE);


        }
        //AGE
        if (ProductId.equals("1") || ProductId.equals("2") || ProductId.equals("3") || ProductId.equals("4") || ProductId.equals("6") || ProductId.equals("17")) {
            lt_txtAge.setVisibility(View.GONE);
        }


        // organic no display
        if (ProductId.equals("10")) {
            LinearLayout1.setVisibility(View.GONE);
            textInputAvailableMonth.setVisibility(View.GONE);
            lr_areaInHector.setVisibility(View.GONE);
        }

        if (ProductId.equals("35")) {
            LinearLayout1.setVisibility(View.GONE);

        }

        // area no display
        if (ProductId.equals("14")) {
            lr_areaInHector.setVisibility(View.GONE);

        }

        if (ProductId.equals("42")) {
            lt_txtAge.setVisibility(View.GONE);
            lr_areaInHector.setVisibility(View.GONE);
        }

        // organic
        if (ProductId.equals("2") || ProductId.equals("9") || ProductId.equals("3") || ProductId.equals("7") || ProductId.equals("14") || ProductId.equals("17") || ProductId.equals("42")) {
            LinearLayout1.setVisibility(View.VISIBLE);
        }

        // certificate
        if (ProductId.equals("2") || ProductId.equals("9") || ProductId.equals("3") || ProductId.equals("7") || ProductId.equals("14") || ProductId.equals("17") || ProductId.equals("42")) {
            edtcertifiedno.setVisibility(View.VISIBLE);
        }

        //quality
        if (ProductId.equals("1") || ProductId.equals("2") || ProductId.equals("14") || ProductId.equals("17") || ProductId.equals("42") || ProductId.equals("7")) {
            lr_quality.setVisibility(View.VISIBLE);
        }

        list = new ArrayList<Product>();

        myLocale = getResources().getConfiguration().locale;


        fetcher = new DataFetcher(sellOptions, customDialog, list, ProductInfoForSaleActivity.this, name_botanical);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb1 = (RadioButton) findViewById(checkedId);
                rb2 = (RadioButton) findViewById(checkedId);

                if (rb1 != null) {

                    org = "Organic";//rb1.getHint().toString();


                } else if (rb2 != null) {
                    org = "Inorganic";//rb2.getHint().toString();


                }

            }
        });


        edtAQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    quant = new Double(edtAQuantity.getText().toString());
                } catch (NumberFormatException e) {
                    quant = 0; // your default value
                }

                try {
                    priceperunit = new Double(edtExpectedPrice.getText().toString());
                } catch (NumberFormatException e) {
                    priceperunit = 0; // your default value
                }

                String   total2 = String.format("%f",quant * priceperunit) ;//String.format("%.2f",  quant * priceperunit);
              //  edtTotalamt.setText(String.format("%.2f",quant * priceperunit));

                NumberFormat formatter = new DecimalFormat("#.#####");


                edtTotalamt.setText(formatter.format(quant * priceperunit));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edtExpectedPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    quant = new Double(edtAQuantity.getText().toString());
                } catch (NumberFormatException e) {
                    quant = 0; // your default value
                }
                //quant = Double.parseDouble(edtAQuantity.getText().toString());


                //String expectedPrice = edtExpectedPrice.getText().toString();

                try {
                    priceperunit = new Double(edtExpectedPrice.getText().toString());
                } catch (NumberFormatException e) {
                    priceperunit = 0; // your default value
                }
                //   priceperunit = Double.parseDouble(edtExpectedPrice.getText().toString());

                total = quant * priceperunit;
                edtTotalamt.setText(Double.toString(total));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtTotalamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  String quantity = edtAQuantity.getText().toString();


                try {
                    quant = new Double(edtAQuantity.getText().toString());
                } catch (NumberFormatException e) {
                    quant = 0; // your default value
                }
                //quant = Double.parseDouble(edtAQuantity.getText().toString());


                //String expectedPrice = edtExpectedPrice.getText().toString();

                try {
                    priceperunit = new Double(edtExpectedPrice.getText().toString());
                } catch (NumberFormatException e) {
                    priceperunit = 0; // your default value
                }
                //   priceperunit = Double.parseDouble(edtExpectedPrice.getText().toString());

                total = quant * priceperunit;
                edtTotalamt.setText(Double.toString(total));
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


        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(edtproductType.getText().toString().equals(""))
                {
                    AlertDialog.Builder popupSuccess = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(ProductInfoForSaleActivity.this).inflate(R.layout.custom_dialog_failed, viewGroup, false);

                    TextView textViewMsg=(TextView) dialogView.findViewById(R.id.tvSuccessMesg);


                    textViewMsg.setText(getResources().getString(R.string.MessageItemSelect));
                    popupSuccess.setView(dialogView);
                    AlertDialog alertDialog = popupSuccess.create();
                    alertDialog.show();

                    dialogView.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });


                    dialogView.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    return;
                }


                //openDialog();
                final Dialog slideDialog;
                LinearLayout cancel,linearAddress;
                final Context context = view.getContext();
                LinearLayout btn;
                final EditText edt_FamilyMemberName,edt_Age;
                final RadioGroup rg_gender;
                final RadioButton rbMale,rbFemale,rbOther;
                final  TextView tvHeadingDialog;
                final String[] GenderSelected = new String[1];
                slideDialog = new Dialog(context, R.style.CustomDialogAnimation);
                slideDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Setting dialogview
                Window window = slideDialog.getWindow();
                //  window.setGravity(Gravity.BOTTOM);

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                slideDialog.setContentView(R.layout.activity_rate_guide);


                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                slideDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
                layoutParams.copyFrom(slideDialog.getWindow().getAttributes());

                //int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = (int) ( context.getResources().getDisplayMetrics().heightPixels * 0.730);

                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = height;
                layoutParams.gravity = Gravity.BOTTOM;

                slideDialog.getWindow().setAttributes(layoutParams);
                slideDialog.setCancelable(true);
                slideDialog.setCanceledOnTouchOutside(true);

                String[] txt_CouponCode = {"GROCY50", "GROCY10", "GROCY60", "GROCY30"};
                String[] txt_CouponDiscount = {"Get Rs. 50 Off", "Get Rs. 10% Off", "Get Rs. 10% Off", "Get Rs. 50 Off"};
                String[] txt_CouponTitle = {"Get Rs 200 off on purchase of Rs 300 and above", "Get Rs 300 off on purchase of Rs 600 and above", "Get Rs 200 off on your first order", "Get Rs 200 off on purchase of Rs 300 and above"};



                recyclerView =  slideDialog.findViewById(R.id.recyclerCoupon);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProductInfoForSaleActivity.this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());





                RateGuide(1);
                initAdapter(rateModels);




                tvHeadingDialog=slideDialog.findViewById(R.id.tvHeadingDialog);

                tvHeadingDialog.setText(edtproductType.getText().toString() +" - "+getResources().getString(R.string.rateguide));

                cancel = slideDialog.findViewById(R.id.cancel);



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slideDialog.dismiss();
                    }
                });



                slideDialog.show();
            }


        });


        if (ProductId.equals("1")) {
            edtproductType.setHint("Fruit Name");
        } else if (ProductId.equals("2")) {
            edtproductType.setHint("Vegetable Name");
        } else if (ProductId.equals("3")) {
            edtproductType.setHint("Flowers Name");
        } else if (ProductId.equals("4")) {
            edtproductType.setHint("Milk & Milk Products Name");
        } else if (ProductId.equals("6")) {
            edtproductType.setHint("Fish name");
        } else if (ProductId.equals("7")) {
            edtproductType.setHint("Sugarcane & Sugarcane Products");
        } else if (ProductId.equals("10")) {
            edtproductType.setHint("Tools");
        } else if (ProductId.equals("14")) {
            edtproductType.setHint("Honey");
        } else if (ProductId.equals("15")) {
            edtproductType.setHint("Nursery");
        } else if (ProductId.equals("17")) {
            edtproductType.setHint("Pulses");
        } else if (ProductId.equals("35")) {
            edtproductType.setHint("Animals");
        } else if (ProductId.equals("42")) {
            edtproductType.setHint("Jaggery");
        } else if (ProductId.equals("43")) {
            edtproductType.setHint("Poultry");

        }

        edtproductType.setHintTextColor(Color.GRAY);


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

            if(IsEdit) {
                String ItemName = extras.getString("ItemName");
                edtproductType.setText(ItemName);

                String BotanicalName = extras.getString("BotanicalName");
                name_botanical.setText(BotanicalName);

                String Organic = extras.getString("Organic");
                if (Organic.equals("Organic"))
                    rb1.setChecked(true);
                else rb2.setChecked(true);

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
                AgeGroupId = extras.getString("AgeGroupId");
                hideAgeId.setText(AgeGroupId);
                String SurveyNo = extras.getString("SurveyNo");
                edtsurveyNo.setText(SurveyNo);


                Picasso.get().load(extras.getString("ImageUrl")).into(img_banner_profile_placeholder);
                ImageUrlupload = extras.getString("ImageUrl");

                if (ImageUrlupload != null)
                    ImageUrl = convertUrlToBase64(ImageUrlupload);


          /*  BitmapDrawable drawable = (BitmapDrawable) img_banner_profile_placeholder.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/

                if (extras.getString("Type") != null) {
//                btnFormSubmit.setVisibility(View.VISIBLE);
                    //     btnAdd.setVisibility(View.GONE);
                    // btnAddMore.setVisibility(View.GONE);
                }

            }
        }

        if (edtAQuality.equals("")) {
            edtAQuality.setText(null);
        }

        if (edtAQuality.equals("")) {
            edtDays.setText(null);
        }

        setNormalPicker();
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
                String AgeGroupId = hideAgeId.getText().toString();
                String total = edtTotalamt.getText().toString();

                StringBuffer errorMessageBuf = new StringBuffer();

                if (lt_txtAge.getVisibility() == View.VISIBLE) {
                    days = hideAgeId.getText().toString();
                } else {
                    days = "0";
                }

                if (edtcertifiedno.getVisibility() == View.VISIBLE) {
                    certificateno = edtcertifiedno.getText().toString();
                } else {
                    certificateno = "0";
                }


                if (lr_subCategory.getVisibility() == View.VISIBLE) {
                    String catId = hideSubCatId.getText().toString();
                    hidItemTypeId.setText(catId);
                }


                // organic
                if (ProductId.equals("2") || ProductId.equals("9") || ProductId.equals("3") || ProductId.equals("7") || ProductId.equals("14") || ProductId.equals("17") || ProductId.equals("42")) {
                    LinearLayout1.setVisibility(View.VISIBLE);
                }

                // certificate
                if (ProductId.equals("2") || ProductId.equals("9") || ProductId.equals("3") || ProductId.equals("7") || ProductId.equals("14") || ProductId.equals("17") || ProductId.equals("42")) {
                    edtcertifiedno.setVisibility(View.VISIBLE);
                }

                //quality
                if (ProductId.equals("6") || ProductId.equals("4") || ProductId.equals("8") || ProductId.equals("15") || ProductId.equals("3") || ProductId.equals("10") || ProductId.equals("35") || ProductId.equals("43 ")) {
                    // edtAQuality.setVisibility(View.GONE);
                }
//


                if (TextUtils.isEmpty(productType)) {
                    edtproductType.setError("Please enter your quality");
                    edtproductType.requestFocus();
                    return;
                }


                if (TextUtils.isEmpty(quantity)) {
                    edtAQuantity.setError("Please enter your quantity");
                    edtAQuantity.requestFocus();
                    return;
                }

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

                if (!ProductId.equals("10")) {
                    if (TextUtils.isEmpty(availablityInMonths)) {
                        edtavailablityInMonths.setError("Please enter your available month");
                        edtavailablityInMonths.requestFocus();
                        return;
                    }
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

                /*if (TextUtils.isEmpty(villagenam)) {
                    edtvillage.setError("Please enter your village name");
                    edtvillage.requestFocus();
                    return;
                }*/

                if (TextUtils.isEmpty(surveyNo)) {
                    edtsurveyNo.setError("Please enter your Survey No.");
                    edtsurveyNo.requestFocus();
                    return;
                }


                if (hidVarietyId.equals("") || productVariety.equals("")) {
                    hidVarietyId.setText("0");
                    productVariety = "0";

                }

                if (quality.equals("") || hidQualityId.equals("")) {
                    quality = "0";
                    hidQualityId.setText("0");
                }

                if (edtcertifiedno.getText().toString().equals("")) {
                    certificateno = "0";

                }

                if (edtareahector.getText().toString().equals("")) {
                    areaheactor = "0";

                }

                if (AgeGroupId.equals("")) {
                    hideAgeId.setText("0");
                }


                if (TextUtils.isEmpty(hidItemTypeId.getText().toString())) {
                    edtproductType.setError("Please select Product Type");
                    edtproductType.requestFocus();
                    return;
                }

              else if (hidItemTypeId.getText().toString().equals("0")) {
                    edtproductType.setError("Please select Product Type");
                    edtproductType.requestFocus();
                    return;
                }

                builder.append("<Parent>");
                builder.append("<Assign>");
                builder.append("<RequestId>" + RequstId + "</RequestId>");
                builder.append("<Id>" + 1 + "</Id>");
                builder.append("<UserId>" + UserId + "</UserId>");
                builder.append("<productTypeId>" + hidItemTypeId.getText().toString() + "</productTypeId>");
                builder.append("<productVarietyId>" + hidVarietyId.getText().toString() + "</productVarietyId>");
                builder.append("<qualityId>" + hidQualityId.getText().toString() + "</qualityId>");
                builder.append("<quantity>" + quantity + "</quantity>");
                builder.append("<unitId>" + hidMeasurementId.getText().toString() + "</unitId>");
                builder.append("<expectedPrice>" + total + "</expectedPrice>");
                builder.append("<days>" + days + "</days>");
                builder.append("<availablityInMonths>" + availablityInMonths + "</availablityInMonths>");
                builder.append("<address>" + address + "</address>");
                builder.append("<stateId>" + hideStateId.getText().toString() + "</stateId>");
                builder.append("<districtId>" + hideDistrictId.getText().toString() + "</districtId>");
                builder.append("<talukaId>" + hideTalukaId.getText().toString() + "</talukaId>");
                builder.append("<villagenam>" + villagenam + "</villagenam>");
                builder.append("<areaheactor>" + areaheactor + "</areaheactor>");
                builder.append("<imagename>" + "0" + "</imagename>"); //ImageUrl
                builder.append("<organic>" + org + "</organic>");
                builder.append("<certificateno>" + certificateno + "</certificateno>");
                builder.append("<SurveyNo>" + surveyNo + "</SurveyNo>");
                builder.append("<AgeGroupId>" + hideAgeId.getText().toString() + "</AgeGroupId>");
                builder.append("<Lat>" + Latitude + "</Lat>");
                builder.append("<Long>" + Longitude + "</Long>");
                builder.append("</Assign>");
                builder.append("</Parent>");
                AsyncTaskRunnerNewEntry runner = new AsyncTaskRunnerNewEntry();
                runner.execute("0");
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ProductInfoForSaleActivity.this);
        if (ActivityCompat.checkSelfPermission(ProductInfoForSaleActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProductInfoForSaleActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProductInfoForSaleActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }
        fetchLastLocation();

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("productTypeId", hidItemTypeId.getText().toString());
        savedInstanceState.putString("productType", edtproductType.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }


    private void initAdapter(List<RateData> rateDatalst) {
        rateAdapter = new RateAdapter(ProductInfoForSaleActivity.this, rateDatalst);
        recyclerView.setAdapter(rateAdapter);
    }
    public String convertUrlToBase64(String url) {
        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //
    public void openDialog() {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        Bundle b;
        b = new Bundle();

        if (hidVarietyId.equals("") && hidQualityId.equals("")) {
            hidQualityId.setText("0");
            hidVarietyId.setText("0");

        }
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


    /*public void clickHereBotanical(View view) {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "ItemName";
        runner.execute(sleepTime);
    }*/


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

    public void clickHereAge(View view) {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "Age";
        runner.execute(sleepTime);
    }

    public void clickHereCategory(View view) {

        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "CategoryName";
        runner.execute(sleepTime);
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                String TittleName = "";
                if (ProductId.equals("1")) {
                    TittleName = "Fruit Name";
                } else if (ProductId.equals("2")) {
                    TittleName = "Vegetable Name";
                } else if (ProductId.equals("3")) {
                    TittleName = "Flowers Name";
                } else if (ProductId.equals("4")) {
                    TittleName = "Milk & Milk Products Name";
                } else if (ProductId.equals("6")) {
                    TittleName = "Fish Name";
                } else if (ProductId.equals("7")) {
                    TittleName = "Sugarcane & Sugarcane Products";
                } else if (ProductId.equals("10")) {
                    TittleName = "Tools";
                } else if (ProductId.equals("14")) {
                    TittleName = "Honey";
                } else if (ProductId.equals("15")) {
                    TittleName = "Nursery";
                } else if (ProductId.equals("17")) {
                    TittleName = "Pulses";
                } else if (ProductId.equals("35")) {
                    TittleName = "Animals";
                } else if (ProductId.equals("42")) {
                    TittleName = "Jaggery";
                } else if (ProductId.equals("43")) {
                    TittleName = "Poultry";

                }


                if (params[0].toString() == "ItemName")
                    fetcher.loadList("ItemName", edtproductType, URLs.URL_NAME + "1&PageSize=700&CategoryId=" + ProductId + "&Language=" + currentLanguage, "ItemTypeId", hidItemTypeId, "CategoryId", ProductId, TittleName, "BotanicalName", name_botanical, lr_variety, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Variety")
                    fetcher.loadList("VarietyName", edtproductVariety, URLs.URL_VARIATY + hidItemTypeId.getText() + "&Language=" + currentLanguage, "VarietyId", hidVarietyId, "", "", "Variety", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Quality")
                    fetcher.loadList("QualityType", edtAQuality, URLs.URL_QUALITY + "?Language=" + currentLanguage + "&CategoryId=" + ProductId, "QualityId", hidQualityId, "", "", "Available Quality", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Unit")
                    fetcher.loadList("MeasurementType", edtUnit, URLs.URL_UNIT + "?CategoryId=" + ProductId + "&Language=" + currentLanguage, "MeasurementId", hidMeasurementId, "", "", "Unit", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "state")
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?Language=" + currentLanguage, "StatesID", hideStateId, "", "", "State", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText() + ",&Language=" + currentLanguage, "DistrictId", hideDistrictId, "", "", "District", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText() + ",&Language=" + currentLanguage, "TalukasId", hideTalukaId, "", "", "Taluka", "", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Age")
                    fetcher.loadList("AgeGroupName", edtDays, URLs.URL_AGE + "Language=" + currentLanguage+ "&CategoryId=" + ProductId+ "&itemTypeId=" + hidItemTypeId.getText() , "AgeGroupId", hideAgeId, "", "", "Age", "", null, null, customDialogLoadingProgressBar);
                if (params[0].toString() == "CategoryName")
                    fetcher.loadList("ItemName", sub_category_name, URLs.URL_NAME + "1&PageSize=500&CategoryId=" + hidItemTypeId.getText() + "&Language=" + currentLanguage, "ItemTypeId", hideSubCatId, "CategoryId", ProductId, "Category Name", "BotanicalName", null, null, customDialogLoadingProgressBar);
                else if (params[0].toString() == "Update") {
                    submitToDb();
                }

            /*    else if (params[0].toString() == "Rate") {
                    loadProducts();
                }*/
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
            // execution of result of Long time consuming operation
            //    progressDialog.dismiss();
            // finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(ProductInfoForSaleActivity.this);
            customDialogLoadingProgressBar.show();
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
        } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }



    }


    ActivityResultLauncher<Intent> locationActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getData()!= null) {
                        if (result.getData().getExtras().getString("place") != null) {
                            tvCurrentAddress.setText(result.getData().getExtras().getString("place"));
                            Latitude = result.getData().getExtras().getString("lat");
                            Longitude = result.getData().getExtras().getString("long");
                            edtaddres.setText(result.getData().getExtras().getString("place"));
                        }
                    }
                }
            });


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmaplocal = null;
        if (data != null) {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmaplocal = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bitmaplocal.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                bitmap = bitmaplocal;
                ImageUrl = getStringImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        img_banner_profile_placeholder.setImageBitmap(bitmap);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        bitmap = thumbnail;
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


    private void submitToDb() {


        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_SAVEPRODUCTDETAILS, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    customDialogLoadingProgressBar.dismiss();
                    JSONObject obj = new JSONObject(new String(response.data));

                    if (obj.getBoolean("error")) {
                        Toast.makeText(ProductInfoForSaleActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage(getResources().getString(R.string.data_submitted));
                        builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                edtUnit.setText("");
                                edtAQuantity.setText("");
                                edtAQuality.setText("");
                                edtExpectedPrice.setText("");
                                edtUnit.setText("");
                                edtTotalamt.setText("");
                            }
                        });
                        builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent startUserAccountListIntent = new Intent(ProductInfoForSaleActivity.this, HomeActivity.class);
                                startUserAccountListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(startUserAccountListIntent);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customDialogLoadingProgressBar.dismiss();
                        Toast.makeText(ProductInfoForSaleActivity.this, "Network Error !" + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("xmlData", builder.toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                try {
                    //  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(new File(item.getPicturePath())) );
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    params.put("image_file", new DataPart("image" + "i" + ".jpg", getFileDataFromDrawable(bitmap)));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(volleyMultipartRequest);

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void submitToDbOld() {

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
        builder.append("<days>" + edtDays.getText() + "</days>");
        builder.append("<availablityInMonths>" + edtavailablityInMonths.getText() + "</availablityInMonths>");
        builder.append("<address>" + edtaddres.getText() + "</address>");
        builder.append("<stateId>" + hideStateId.getText() + "</stateId>");
        builder.append("<districtId>" + hideDistrictId.getText() + "</districtId>");
        builder.append("<talukaId>" + hideTalukaId.getText() + "</talukaId>");
        builder.append("<villagenam>" + edtvillage.getText() + "</villagenam>");
        builder.append("<areaheactor>" + edtareahector.getText() + "</areaheactor>");
        builder.append("<imagename>" + ImageUrl + "</imagename>");
        builder.append("<orgnic>" + org + "</orgnic>");
        builder.append("<certificateno>" + edtcertifiedno.getText() + "</certificateno>");
        builder.append("<SurveyNo>" + edtsurveyNo.getText() + "</SurveyNo>");
        builder.append("<AgeGroupId>" + hideAgeId.getText() + "</AgeGroupId>");
        builder.append("</Assign>");
        builder.append("</Parent>");

//edtcertifiedno.getText()+
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SAVEPRODUCTDETAILS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
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
                        alert.show();*/

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage("Data submitted successfully, Do you want to Add more Quality?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                edtUnit.setText("");
                                edtAQuantity.setText("");
                                edtAQuality.setText("");
                                edtExpectedPrice.setText("");
                                edtUnit.setText("");
                                edtTotalamt.setText("");
                                // edtcertifiedno.setText("");


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
                                startUserAccountListIntent.putExtra("productVariety", edtproductVariety.getText().toString());
                                startUserAccountListIntent.putExtra("qualityId", hidQualityId.getText().toString());

                                //startUserAccountListIntent.putExtra("monthId", edtavailablityInMonths.getText().toString());
                                startUserAccountListIntent.putExtra("unitId", hidMeasurementId.getText().toString());
                                startUserAccountListIntent.putExtra("stateId", hideStateId.getText().toString());
                                startUserAccountListIntent.putExtra("districtId", hideDistrictId.getText().toString());
                                startUserAccountListIntent.putExtra("talukaId", hideTalukaId.getText().toString());
                                startUserAccountListIntent.putExtra("organic", org);
                                startUserAccountListIntent.putExtra("certificateno", edtcertifiedno.getText().toString());
                                startUserAccountListIntent.putExtra("SurveyNo", edtsurveyNo.getText().toString());
                                startUserAccountListIntent.putExtra("ImageUrl", ImageUrl);
                                startUserAccountListIntent.putExtra("AgeGroupId", hideAgeId.getText().toString());
                                startUserAccountListIntent.putExtra("TotalAmt", edtTotalamt.getText().toString());
                                startActivity(startUserAccountListIntent);

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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(ProductInfoForSaleActivity.this).addToRequestQueue(stringRequest);
    }


    private void submitNewEntry() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SAVEPRODUCTDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        customDialogLoadingProgressBar.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductInfoForSaleActivity.this);
                        builder.setCancelable(false);
                        mProgressDialog.dismiss();
                        builder.setMessage("Data submitted Successfully ! Do you want to add more Quality ?");
                        userInfoDBManager.deleteAll();
                        builder.setIcon(R.drawable.ic_check_circle);
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent startUserAccountListIntent = new Intent(ProductInfoForSaleActivity.this, HomeActivity.class);
                                startUserAccountListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(startUserAccountListIntent);

                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if user pressed "yes", then he is allowed to exit from applicationdialog.cancel();
                                // Intent i = new Intent(ProductInfoForSaleActivity.this, HomeActivity.class);
                                //startActivity(i);
                                dialog.dismiss();

                                edtUnit.setText("");
                                edtAQuantity.setText("");
                                edtExpectedPrice.setText("");
                                edtUnit.setText("");
                                edtTotalamt.setText("");
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error------------", error.getMessage());
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(ProductInfoForSaleActivity.this).addToRequestQueue(stringRequest);


    }

    public void changeLang(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            myLocale = new Locale(lang);
            Locale.setDefault(myLocale);
            Configuration conf = new Configuration(config);
            conf.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    private void RateGuide(final Integer currentPage) {


        customDialogLoadingProgressBar.show();
        if (!currentPage.equals(1)) {
            customDialogLoadingProgressBar.dismiss();
            rateModels.add(null);
            rateAdapter.notifyItemInserted(rateModels.size() - 1);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!currentPage.equals(1)) {
                                        rateModels.remove(rateModels.size() - 1);
                                        int scrollPosition = rateModels.size();
                                        rateAdapter.notifyItemRemoved(scrollPosition);
                                        final int currentSize = scrollPosition;
                                        final int nextLimit = currentSize + 10;
                                    }

                                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                            URLs.URL_RATE + "ItemTypeId="+hidItemTypeId.getText()+"&VarietyId=0&QualityId=0&MonthYear="+edtavailablityInMonths.getText()+"&Language=en",
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        JSONArray obj = new JSONArray(response);
                                                        rateModels.clear();
                                                        for (int i = 0; i < obj.length(); i++) {
                                                            JSONObject userJson = obj.getJSONObject(i);
                                                            if (!userJson.getBoolean("error")) {
                                                                RateData sellOptions;
                                                                sellOptions = new RateData
                                                                        (
                                                                                userJson.getString("FromDates"),
                                                                                userJson.getString("ToDates"),
                                                                                userJson.getString("Rates"),
                                                                                userJson.getString("MeasurementType")
                                                                        );
                                                                rateModels.add(sellOptions);
                                                                //

                                                            } else {
                                                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                                            }

                                                            rateAdapter.notifyDataSetChanged();
                                                            customDialogLoadingProgressBar.dismiss();
                                                        }




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
                                    };
                                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                                }


                            }
                , 500);

        customDialogLoadingProgressBar.dismiss();
    }


    private class AsyncTaskRunnerNewEntry extends AsyncTask<String, String, String> {

        private String resp;


        @Override
        protected String doInBackground(String... params) {

            try {

                //  submitNewEntry();
                submitToDb();

            } catch (Exception e) {
                e.printStackTrace();
                //   Toast.makeText(AddListActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            // mProgressDialog.dismiss();

            // finalResult.setText(result);
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(ProductInfoForSaleActivity.this);
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {
            //mProgressDialog.setProgress(92);

        }


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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    Toast.makeText(ProductInfoForSaleActivity.this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(ProductInfoForSaleActivity.this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(ProductInfoForSaleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProductInfoForSaleActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    List<Address> addresses = getAddress(ProductInfoForSaleActivity.this, currentLocation.getLatitude(), currentLocation.getLongitude());


                    if (addresses != null) {

                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            tvCurrentAddress.setText(addresses.get(0).getAddressLine(0));



                            Latitude=String.valueOf(address.getLatitude());
                            Longitude=String.valueOf(address.getLongitude());

                            GPSState=address.getAdminArea();
                            GPSDistrict=address.getSubAdminArea();
                            GPSTaluka=address.getSubAdminArea();
                        }



                    }




                } else {
                    Toast.makeText(ProductInfoForSaleActivity.this, "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private void applyLocale() {
        Locale locale = new Locale("hi");
        Locale.setDefault(locale);

        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void setNormalPicker() {
        final Calendar today = Calendar.getInstance();
        edtavailablityInMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ProductInfoForSaleActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        //Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
                       // Toast.makeText(ProductInfoForSaleActivity.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();

                        String MonthName="";
                        switch(selectedMonth) {
                            case 0:
                                MonthName="January";
                                break;
                            case 1:
                                MonthName="February";
                                break;
                            case 2:
                                MonthName="March";
                                break;
                            case 3:
                                MonthName="April";
                                break;
                            case 4:
                                MonthName="May";
                                break;
                            case 5:
                                MonthName="June";
                                break;
                            case 6:
                                MonthName="July";
                                break;
                            case 7:
                                MonthName="August";
                                break;
                            case 8:
                                MonthName="September";
                                break;
                            case 9:
                                MonthName="October";
                                break;
                            case 10:
                                MonthName="November";
                                break;
                            case 11:
                                MonthName="December";
                                break;

                        }
                        edtavailablityInMonths.setText(MonthName+","+selectedYear);
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(2022)
                        .setActivatedYear(2022)
                        .setMaxYear(2025)
                        .setMinMonth(Calendar.FEBRUARY)
                        .setTitle("Select trading month")
                        .setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                Log.d(TAG, "Selected month : " + selectedMonth);
                                // Toast.makeText(ProductInfoForSaleActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {
                                Log.d(TAG, "Selected year : " + selectedYear);
                                // Toast.makeText(ProductInfoForSaleActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();

            }
        });


    }



    int choosenYear = 2017;


}