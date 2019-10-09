package com.sharpflux.shetkarimaza.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.MySellerAdapter;
import com.sharpflux.shetkarimaza.adapter.ProcessorAdapter;
import com.sharpflux.shetkarimaza.filters.SubCategoryFilter;
import com.sharpflux.shetkarimaza.filters.VarietyAdapter;
import com.sharpflux.shetkarimaza.filters.VarietyFragment;
import com.sharpflux.shetkarimaza.model.MyProcessor;
import com.sharpflux.shetkarimaza.model.SellOptions;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProcessorActivity extends AppCompatActivity {
Button btnProcessorSubmit;
    Locale myLocale;
    RecyclerView mRecyclerView;
    private List<MyProcessor> processorList;
    SearchView searchView;
    ProcessorAdapter myAdapter;
    private Intent iin;
    private Bundle bundle;

    private String address = "", city = "", district = "", state = "", companyname = "",
            license = "", companyregnno = "", gstno = "", names = "", registrationTypeId = "",
            registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",
            accountname = "", bankname = "", branchcode = "", accno = "", ifsc = "", check = "", adhar = "", selfie = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processor);

        myLocale = getResources().getConfiguration().locale;
        btnProcessorSubmit = findViewById(R.id.btnProcessorNext);

        mRecyclerView = findViewById(R.id.rcv_processor);
        searchView=findViewById(R.id.searchViewProcessor);


        processorList = new ArrayList<>();

        LinearLayoutManager mGridLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        iin = getIntent();
        bundle = iin.getExtras();

        if (bundle != null) {
            final String image;
            names = bundle.getString("Name");
            registrationTypeId = bundle.getString("RegistrationTypeId");

            registrationCategoryId = bundle.getString("RegistrationCategoryId");
            gender = bundle.getString("Gender");

            mobile = bundle.getString("Mobile");
            alternateMobile = bundle.getString("AlternateMobile");

            email = bundle.getString("Email");
            address = bundle.getString("address");

            city = bundle.getString("city");
            district = bundle.getString("district");

            state = bundle.getString("state");
            companyname = bundle.getString("companyname");

            license = bundle.getString("license");
            companyregnno = bundle.getString("companyregnno");

            gstno = bundle.getString("gstno");
            accountname = bundle.getString("accountname");

            bankname = bundle.getString("bankname");
            branchcode = bundle.getString("branchcode");

            accno = bundle.getString("accno");
            ifsc = bundle.getString("ifsc");

            check = bundle.getString("check");
            adhar = bundle.getString("adhar");

        }


        btnProcessorSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent =new Intent(ProcessorActivity.this,SelfieActivity.class);

                intent.putExtra("Name", names);
                intent.putExtra("RegistrationTypeId", registrationTypeId);
                intent.putExtra("RegistrationCategoryId", registrationCategoryId);
                intent.putExtra("Gender", gender);
                intent.putExtra("Mobile", mobile);
                intent.putExtra("AlternateMobile", alternateMobile);
                intent.putExtra("Email", email);
                intent.putExtra("address", address);
                intent.putExtra("city", city);
                intent.putExtra("district", district);
                intent.putExtra("state", state);
                intent.putExtra("companyname", companyname);
                intent.putExtra("license", license);
                intent.putExtra("companyregnno", companyregnno);
                intent.putExtra("gstno", gstno);
                intent.putExtra("accountname", accountname);
                intent.putExtra("bankname", bankname);
                intent.putExtra("branchcode", branchcode);
                intent.putExtra("accno", accno);
                intent.putExtra("ifsc", ifsc);
                intent.putExtra("check", check);
                intent.putExtra("adhar", adhar);
                startActivity(intent);
            }
        });


        ProcessorActivity.AsyncTaskRunner runner = new ProcessorActivity.AsyncTaskRunner();
        String sleepTime = "500";
        runner.execute(sleepTime);
    }

    private void getProcessorData() {



        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URLs.URL_PROCESSOR+myLocale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);
                                if (!userJson.getBoolean("error")) {
                                    MyProcessor proList =
                                            new MyProcessor
                                                    (userJson.getString("ItemTypeId"),
                                                            userJson.getString("ItemName"));

                                    processorList.add(proList);

                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }

                                myAdapter = new ProcessorAdapter(getApplicationContext(), processorList);
                                mRecyclerView.setAdapter(myAdapter);

                                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {

                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {

                                        myAdapter.getFilter().filter(newText);
                                        return false;

                                    }});
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

        VolleySingleton.getInstance(ProcessorActivity.this).addToRequestQueue(stringRequest);
    }

    private class  AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


                getProcessorData();
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
            progressDialog = ProgressDialog.show(ProcessorActivity.this,
                    "Loading...",
                    "");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }
}
