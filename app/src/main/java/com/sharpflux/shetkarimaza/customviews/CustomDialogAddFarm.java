package com.sharpflux.shetkarimaza.customviews;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
import com.sharpflux.shetkarimaza.fragment.SecondFragment;
import com.sharpflux.shetkarimaza.model.AddPersonModel;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;



public class CustomDialogAddFarm extends Dialog {
    TextView hideStateId, hideDistrictId,hideTalukaId;
    EditText address, city, edtdistrict,edttaluka, edtstate, companyname, license, companyregnno, gstno,name_botanical;
    ArrayList<Product> list;
    Button btn_next;
    Bundle bundle;
    String name = "", registrationTypeId = "", registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "";
    DataFetcher fetcher;
    private CustomRecyclerViewDialog customDialog;
    Product sellOptions;

    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;
    TextInputLayout TICompany;


    public Context context;

    private EditText editText_crops;
    private TextView textView_title, textView_addFarm;



    private ImageView imageView_back;

    private RadioGroup radioGroup_type, radioGroup_irrigationType;

    private Map<String, Integer> list2;



    private String id, farm_details_id;

    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;




    private AddPersonAdapter addPersonAdapter;
    private ArrayList<AddPersonModel> addPersonModelArrayList;
    private int position;

    public CustomDialogAddFarm(Context context, String id, String farm_details_id, AddPersonAdapter addPersonAdapter,
                               ArrayList<AddPersonModel> addPersonModelArrayList, int position)
    {
        super(context);
        this.context = context;
        this.id = id;
        this.farm_details_id = farm_details_id;
        this.addPersonAdapter = addPersonAdapter;
        this.addPersonModelArrayList = addPersonModelArrayList;
        this.position = position;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);





        setContentView(R.layout.custom_dialog_add_farm);



        setCanceledOnTouchOutside(true);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        imageView_back = findViewById(R.id.imageView_back);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText("Add Ware House");


        myLocale =getContext().getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getContext());

        list = new ArrayList<Product>();

        btn_next = (Button) (findViewById(R.id.secondbtnnext));

        address = findViewById(R.id.edtaddress);

        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdistrict);
        edttaluka=findViewById(R.id.edttaluka);
        city = findViewById(R.id.edtcity);
        name_botanical = findViewById(R.id.name_botanical);

        companyname = findViewById(R.id.edtcompanyname);
        license = findViewById(R.id.edtlicense);
        companyregnno = findViewById(R.id.edtcompanyregno);
        gstno = findViewById(R.id.edtgstno);

        TICompany = (TextInputLayout)findViewById(R.id.TICompany);


        hideStateId = findViewById(R.id.hideStateId);
        hideDistrictId = findViewById(R.id.hideDistrictId);
        hideTalukaId = findViewById(R.id.hideTalukaId);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getContext().getResources().getConfiguration().locale;
        language = user.getLanguage();

        Cursor cursor = mydatabase.LanguageGet(language);


        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }



        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(),name_botanical);
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

    }


    private void onClickListener()
    {




    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                if (params[0].toString() == "state")
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?StatesID=15&Language=en", "StatesID", hideStateId, "", "","State","",null,null);
                    // fetcher.loadList("StatesName", edtstate, URLs.URL_STATE, "StatesID", hideStateId, "", "");

                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText()+"," + "&Language=en", "DistrictId", hideDistrictId, "", "","District","",null,null);

                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText()+"," + "&Language=en", "TalukasId", hideTalukaId, "", "","Taluka","",null,null);


                Thread.sleep(500);

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
            progressDialog = ProgressDialog.show(getContext(),
                    "Loading...",
                    "");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }

    private class AsyncTaskLoad extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()

            try {





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

            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(context);
            customDialogLoadingProgressBar.show();

        }


        @Override
        protected void onProgressUpdate(String... text) {

        }

    }


}
