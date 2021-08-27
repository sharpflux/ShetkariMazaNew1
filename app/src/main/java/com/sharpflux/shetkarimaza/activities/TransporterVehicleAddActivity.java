package com.sharpflux.shetkarimaza.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransporterVehicleAddActivity extends AppCompatActivity {

    DataFetcher fetcher;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    EditText edtVehicleType;
    TextView hidVehicleTypeId, hideStateId, hideDistrictId, hideTalukaId;
    private EditText   edtdistrict, edtstate,   edttaluka, edtaddres, edtvillage,edtVehcileNo,edtRateperKm,edtsurveyNo;
    dbLanguage mydatabase;
    String currentLanguage, language;
    ArrayList<Product> list;
    Product sellOptions;
    CustomRecyclerViewDialog customDialog;
    ProgressDialog mProgressDialog;
    User user;
    LinearLayout btnFormSubmit,btnAddMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_vehicle_add);
        edtVehicleType=findViewById(R.id.edtVehicleType);
        hidVehicleTypeId=findViewById(R.id.hidVehicleTypeId);
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(TransporterVehicleAddActivity.this);
        mydatabase = new dbLanguage(getApplicationContext());

        list = new ArrayList<Product>();
        btnAddMore = findViewById(R.id.btnAddMore);
        mProgressDialog = new ProgressDialog(TransporterVehicleAddActivity.this);
        mProgressDialog.setIndeterminate(false);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        mProgressDialog.setTitle("Saving data");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, we are saving your data...");


        user = SharedPrefManager.getInstance(this).getUser();

        btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String VehicleTypeId = hidVehicleTypeId.getText().toString();
                String VehicleType = edtVehicleType.getText().toString();
                String VehicleNo = edtVehcileNo.getText().toString();
                String Rate = edtRateperKm.getText().toString();
                final String address = edtaddres.getText().toString();
                final String state = edtstate.getText().toString();
                final String district = edtdistrict.getText().toString();
                final String taluka = edttaluka.getText().toString();
                final String villagenam = edtvillage.getText().toString();
                final String surveyNo = edtsurveyNo.getText().toString();


                if (TextUtils.isEmpty(VehicleType)) {
                    edtVehicleType.setError("Please select vehicle type");
                    edtVehicleType.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }



                if (TextUtils.isEmpty(VehicleNo)) {
                    edtVehcileNo.setError("Please enter vehicle No");
                    edtVehcileNo.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }
                if (TextUtils.isEmpty(Rate)) {
                    edtRateperKm.setError("Please enter Rate per Km");
                    edtRateperKm.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    edtaddres.setError("Please enter your address");
                    edtaddres.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }

              /*  if (TextUtils.isEmpty(surveyNo)) {
                    edtsurveyNo.setError("Please enter Survey No");
                    edtsurveyNo.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }*/
                if (TextUtils.isEmpty(state)) {
                    edtstate.setError("Please enter your state");
                    edtstate.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(district)) {
                    edtdistrict.setError("Please enter your district");
                    edtdistrict.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(taluka)) {
                    edttaluka.setError("Please enter your taluka");
                    edttaluka.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }

            /*    if (TextUtils.isEmpty(villagenam)) {
                    edtvillage.setError("Please enter your village name");
                    edtvillage.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }*/

              /*  if (TextUtils.isEmpty(surveyNo)) {
                    edtsurveyNo.setError("Please enter your Survey No.");
                    edtsurveyNo.requestFocus();
                    customDialogLoadingProgressBar.dismiss();
                    return;
                }*/


                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "submit";
                runner.execute(sleepTime);
            }
        });



        Cursor cursor = mydatabase.LanguageGet(language);

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }
        fetcher = new DataFetcher(sellOptions, customDialog, list, TransporterVehicleAddActivity.this, null);
        hideStateId = findViewById(R.id.hideStateId);
        hideDistrictId = findViewById(R.id.hideDistrictId);
        hideTalukaId = findViewById(R.id.hideTalukaId);

        edtVehcileNo=findViewById(R.id.edtVehcileNo);
        edtRateperKm= findViewById(R.id.edtRateperKm);
        edtsurveyNo=findViewById(R.id.edtsurveyNo) ;

        edtaddres = findViewById(R.id.edtAddressoffarm);
        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdistrict);
        edttaluka = findViewById(R.id.edtTal);
        edtvillage = findViewById(R.id.edtvillage);

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

    public void clickHere(View view) {
        AsyncTaskRunner runner = new AsyncTaskRunner();
        String sleepTime = "ItemName";
        runner.execute(sleepTime);
    }

    private void submitNewEntry() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_POSTVEHICLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        customDialogLoadingProgressBar.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(TransporterVehicleAddActivity.this);
                        builder.setCancelable(false);
                        mProgressDialog.dismiss();
                        builder.setMessage("Data submitted Successfully ! Do you want to add more Vehicle ?");
                        builder.setIcon(R.drawable.ic_check_circle);
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent startUserAccountListIntent = new Intent(TransporterVehicleAddActivity.this, HomeActivity.class);
                                startUserAccountListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(startUserAccountListIntent);

                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TransportId", "0");
                params.put("UserId",String.valueOf(user.getId()));
                params.put("VehicalId", hidVehicleTypeId.getText().toString());
                params.put("VehicalNo", edtVehcileNo.getText().toString());
                params.put("Rate", edtRateperKm.getText().toString());
                params.put("TransportAddress", edtaddres.getText().toString());
                params.put("SurveyNo","0");
                params.put("StateId",  hideStateId.getText().toString() );
                params.put("DistrictId", hideDistrictId.getText().toString());
                params.put("TahasilId", hideTalukaId.getText().toString());
                params.put("VillageName", "0");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(TransporterVehicleAddActivity.this).addToRequestQueue(stringRequest);


    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                  if (params[0].toString() == "ItemName")
                      fetcher.loadList("VehicalType", edtVehicleType, URLs.URL_GETVEHICLE + "Language=" + currentLanguage, "VehicalId", hidVehicleTypeId, "", "", "Vehicle", "", null, null, customDialogLoadingProgressBar);
                  else if (params[0].toString() == "state")
                      fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?Language=" + currentLanguage, "StatesID", hideStateId, "", "", "State", "", null, null, customDialogLoadingProgressBar);
                  else if (params[0].toString() == "district")
                      fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText() + ",&Language=" + currentLanguage, "DistrictId", hideDistrictId, "", "", "District", "", null, null, customDialogLoadingProgressBar);
                  else if (params[0].toString() == "taluka")
                      fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText() + ",&Language=" + currentLanguage, "TalukasId", hideTalukaId, "", "", "Taluka", "", null, null, customDialogLoadingProgressBar);

                  else if (params[0].toString() == "submit")
                      submitNewEntry();

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
            //    progressDialog.dismiss();
            // finalResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(TransporterVehicleAddActivity.this);
            customDialogLoadingProgressBar.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }
}