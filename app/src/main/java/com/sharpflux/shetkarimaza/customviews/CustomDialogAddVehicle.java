package com.sharpflux.shetkarimaza.customviews;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.AddPersonAdapter;
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


public class CustomDialogAddVehicle extends Dialog {
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

    public CustomDialogAddVehicle(Context context, String id, String farm_details_id, AddPersonAdapter addPersonAdapter,
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
        setContentView(R.layout.custom_dialog_add_vehicle);
        setCanceledOnTouchOutside(true);
        list = new ArrayList<Product>();
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imageView_back = findViewById(R.id.imageView_back);
        textView_title = findViewById(R.id.textView_title);
        hideStateId = findViewById(R.id.hideStateId);
        textView_title.setText("Add Vehicle");
        myLocale =getContext().getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getContext());
        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(),name_botanical);
        edtstate=findViewById(R.id.edtstate);
        edtstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "Vehicle";
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
                if (params[0].toString() == "Vehicle")
                    fetcher.loadList("VehicalType", edtstate, URLs.URL_VEHICLETYPE, "VehicalId", hideStateId, "", "","VehicalType","",null,null);
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
